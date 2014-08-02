;; # The API
(ns brian.routes.api
  (:require [compojure.core :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [brian.models.db :refer [db rules]]
            [fogus.datalog.bacwn :refer [q]]
            [fogus.datalog.bacwn.macros :refer [?-]]))

(defn parse-year
  ""
  ([x]
   (parse-year "MM/dd/yyyy" x))
  ([fmt x]
   (t/year (f/parse (f/formatter fmt) x))))

(defn books-read []
  (q (?- :success :author ?a :title ?t :when ?w) db rules {}))

(defn books-read-by-year
  "Partition books by year read."
  []
  (let [res (books-read)
        parts (group-by (comp parse-year :when) res)]
    (map #(hash-map (first %) (count (second %))) parts)))


(comment
(defn books-in-last
  "TODO: datetime parsing"
  [t]
  (filter #(> ((comp parse-year :when) %) t) (books-read)))
  )

(comment
(defn recent-books
  "TODO: datetime parsing"
  [n]
  (take n (sort-by :when (complement compare) (books-read))))
  )

(defroutes api-routes
  (GET "/api/books-read" [] (books-read))
  (GET "/api/books-by-year" [] (books-read-by-year)))
