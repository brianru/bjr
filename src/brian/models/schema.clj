(ns brian.models.schema
  (:require [brian.models.db :refer :all]
            [clojure.java.jdbc :as sql]))

(comment
(defn create-books-table []
  (sql/with-connection db
    (sql/create-table
      :books
      []
      [])))
  )
