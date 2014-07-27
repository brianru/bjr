(ns brian.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure-csv.core :as csv]
            [environ.core :as env]))

(def db {:subprotocol "postgresql"
         :subname "//localhost/brian"
         :user "brian"
         :password "brian"})

(defn parse-initial-data []
  (let [tsv  (csv/parse-csv (slurp (env :raw-data)) :delimiter \tab)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

