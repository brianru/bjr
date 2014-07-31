(ns brian.routes.api
  (:require [compojure.core :refer :all]
            [brian.models.db :refer :all])
  (:use [fogus.datalog.bacwn :only (q)]))

; query db and return json of results
(defn books []
  "Query the database for a list of books."
   (str db))

(defroutes api-routes
  (GET "/api/books" [] (books)))
