;; # The API
(ns brian.routes.api
  (:require [compojure.core :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [brian.models.init :refer [db]]
            [brian.models.db :refer [books-read]]
            [fogus.datalog.bacwn :refer [q]]
            [fogus.datalog.bacwn.macros :refer [?-]]))

(defn parse-year
  ""
  ([x]
   (parse-year "MM/dd/yyyy" x))
  ([fmt x]
   (t/year (f/parse (f/formatter fmt) x))))

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

(defn commits-by-day [] nil)

(defn languages [] nil)

(defn repos [] nil)

(defroutes api-routes
  (GET "/api/books-read" [] (books-read))
  (GET "/api/books-by-year" [] (books-read-by-year))
  (GET "/api/commits-by-day" [] (commits-by-day))
  (GET "/api/languages" [] (languages))
  (GET "/api/repos" [] (repos)))
