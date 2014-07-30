(ns brian.models.db
  (:require [clojure-csv.core :as csv])
  (:use [clojure.string :only (blank?)]
        [clojure.set :only (rename-keys)]
        [fogus.datalog.bacwn :only (build-work-plan run-work-plan)]
        [fogus.datalog.bacwn.macros :only (<- ?- make-database)]
        [fogus.datalog.bacwn.impl.rules :only (rules-set)]
        [fogus.datalog.bacwn.impl.database :only (add-tuples)]))


(def db-base
  (make-database
    (relation :book [:isbn :author :title])
    (index :book :isbn)

    (relation :start [:isbn :when])
    (index :start :isbn)

    (relation :finish [:isbn :when])
    (index :finish :isbn)))

; (parse-tabular-data (env :raw-data) \tab)
(defn get-tabular-data [path delim]
  (let [tsv  (csv/parse-csv (slurp path) :delimiter delim)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

(defn filter-and-swap [d old-ks new-ks]
  (rename-keys (select-keys d old-ks)
               (zipmap old-ks new-ks)))

; TODO how do I only iterate through the entries once?
; TODO how do I use a map instead of the two vectors?
(defn parse-entries [relation d old-ks new-ks]
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
        parsed (concat (parse-books raw) (parse-starts raw) (parse-finishes raw))]
    (apply (partial add-tuples db-base) parsed)))
