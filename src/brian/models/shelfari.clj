(ns brian.models.shelfari
  (:require [clojure-csv.core :as csv]
            [clojure.string :refer [blank?]]
            [clojure.set :refer [rename-keys]]
            [clojure.walk :refer [keywordize-keys]]
            [clj-http.client :as http]
            [clojure.data.json :as json]))

(def shelfari-db-map
  {:book (sorted-map "ISBN" :isbn
                     "Author" :author
                     "Title" :title)
   :start (sorted-map "ISBN" :isbn
                      "DateAdded" :when)
   :finish (sorted-map "ISBN" :isbn
                       "DateRead" :when)})

(defn query-library-api
  "Get data about a given isbn from the Open Library API."
  [isbn]
  (let [endpoint "http://openlibrary.org/api/books?bibkeys=ISBN:"
        opts     "&format=json&jscmd=data"
        resp     (http/get (str endpoint isbn opts))
        body     (-> resp (:body)
                     (json/read-str)
                     (first) (next) (first)
                     (keywordize-keys))]
    body))

(defn filter-and-swap
  "Filter map by keys (`old-ks`) then swap the old keys (`old-ks`)
   for the new keys (`new-ks`)."
  [data attr-map]
  (rename-keys (select-keys data (keys attr-map)) attr-map))

(defn parse-entries
  "TODO iterate through the entries once
   TODO use a map instead of the two vectors"
  [relation data attr-map]
  (let [massaged (mapv #(filter-and-swap % attr-map) data)
        filtered (filter #(not-any? blank? (vals %)) massaged)]
    (mapv #(flatten (cons relation %)) filtered)))

(defn mk-parser [[kind attr-map]]
  (fn [data] (parse-entries kind data attr-map)))

(defn get-tabular-data
  "Given spreadsheet data, construct a vector of maps
   using the first row as keys."
  [path delim]
  (let [tsv  (csv/parse-csv (slurp path) :delimiter delim)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

(defn parse-shelfari [path delim translator]
  (let [raw     (get-tabular-data path delim)
        parsers (map mk-parser translator)
        parsed  (mapcat #(% raw) parsers)]
    parsed))
