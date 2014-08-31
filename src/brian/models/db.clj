;; # Data Interface Layer
;;
(ns brian.models.db
  (:require [brian.models.init :refer [db]]
            [brian.models.schema :refer [rules]]
            [fogus.datalog.bacwn :refer [q]]
            [fogus.datalog.bacwn.macros :refer [?-]]))

(defn books-read
  "Query the database for books that have been read."
  []
  (q (?- :success :author ?a :title ?t :when ?w)
     db rules {}))

(defn unfinished-books
  []
  (q (?- :failure :author ?a :title ?t :when ?w)
     db rules {})) 

(defn start-books
  "Take any number of book-start tuples"
  [])

(defn finish-books
  "Take any number of book-finish tuples"
  [])
