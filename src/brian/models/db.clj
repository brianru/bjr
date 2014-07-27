(ns brian.models.db
  (:require [clojure.java.jdbc :as sql]
            [clojure-csv.core :as csv]
            [environ.core :refer [env]]
            [korma.db :refer [defdb transaction]]
            [korma.core :refer :all]))

(def db {:subprotocol "postgresql"
         :subname (env :db-url)
         :user (env :db-user)
         :password (env :db-pass)})

; (parse-tabular-data (env :raw-data) \tab)
(defn parse-tabular-data [path delim]
  (let [tsv  (csv/parse-csv (slurp path) :delimiter delim)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

