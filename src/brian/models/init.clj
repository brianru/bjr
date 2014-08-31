;; If no .db file is found, construct db.
(ns brian.models.init
  (:require [clojure-csv.core :as csv]
            [clojure.string :refer [blank?]]
            [clojure.set :refer [rename-keys]]
            [brian.models.schema :refer [db-base]]
            [fogus.datalog.bacwn.impl.database :refer [add-tuples]]
            ))

(def db-path "resources/brian.db")

(defn get-tabular-data
  "Given spreadsheet data, construct a vector of maps
   using the first row as keys."
  [path delim]
  (let [tsv  (csv/parse-csv (slurp path) :delimiter delim)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

(defn filter-and-swap
  "Filter map by keys (`old-ks`) then swap the old keys (`old-ks`)
   for the new keys (`new-ks`)."
  [d old-ks new-ks]
  (rename-keys (select-keys d old-ks)
               (zipmap old-ks new-ks)))

(defn parse-entries [relation d old-ks new-ks]
  "TODO iterate through the entries once
   TODO use a map instead of the two vectors"
  (let [massaged (mapv #(filter-and-swap % old-ks new-ks) d)
        filtered (filter #(not-any? blank? (vals %)) massaged)]
    (mapv #(flatten (cons relation %)) filtered)))

(defn parse-books [d]
  (parse-entries :book d
                 ["Title" "Author" "ISBN"]
                 [:title :author :isbn]))
(defn parse-starts [d]
  (parse-entries :start d
                 ["ISBN" "DateAdded"]
                 [:isbn :when]))
(defn parse-finishes [d]
  (parse-entries :finish d
                 ["ISBN" "DateRead"]
                 [:isbn :when]))

(def db
  (let [raw    (get-tabular-data "resources/shelfari_data.tsv" \tab)
        parsed (concat (parse-books raw)
                       (parse-starts raw)
                       (parse-finishes raw))]
    (apply (partial add-tuples db-base) parsed)))

(defn save-db [db]
  (spit db-path db))

(defn load-db []
  (read-string (slurp db-path)))
