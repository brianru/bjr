;; If no .db file is found, construct db.
(ns brian.models.init
  (:require [brian.models.shelfari :refer [parse-shelfari]] 
            [brian.models.schema :refer [db-base]]
            [fogus.datalog.bacwn.impl.database :refer [add-tuples]]
            ))

(def db-path "resources/brian.db")

(defn save-db [db]
  (spit db-path db))

(defn load-db []
  (read-string (slurp db-path)))

(def db
  (if (.exists (clojure.java.io/as-file db-path))
    (load-db)
    (let [init (parse-shelfari "resources/shelfari_data.tsv")
          res  (apply (partial add-tuples db-base) init)]
      (do (save-db res)
          res))))
