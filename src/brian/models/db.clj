(ns brian.models.db
  (:require [clojure-csv.core :as csv])
  (:use [fogus.datalog.bacwn :only (build-work-plan run-work-plan)]
        [fogus.datalog.bacwn.macros :only (<- ?- make-database)]
        [fogus.datalog.bacwn.impl.rules :only (rules-set)]
        [fogus.datalog.bacwn.impl.database :only (add-tuples)]))


(def db-base
  (make-database
    (relation :book [:isbn :author :title :pagecount])
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

; TODO how do I only iterate through the entries once?
; TODO how do I use a map instead of the two vectors?
; FIXME this doesn't work.
(defn parse-entries [relation d old-ks new-ks]
  (let [filtered (mapv #(select-keys % old-ks) d)]
    (mapv #(cons relation (zipmap new-ks (vals %))) filtered)))

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
  (let [d (get-tabular-data "resources/shelfari_data.tsv" \tab)]
    (apply (partial add-tuples db-base)
           (concat (parse-books d)
                   (parse-starts d)
                   (parse-finishes d)))))
