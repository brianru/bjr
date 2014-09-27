(ns brian.models.shelfari
  (:require [clojure-csv.core :as csv]
            [clojure.walk :refer [keywordize-keys]]
            [clj-http.client :as http]
            [clojure.data.json :as json]
            [brian.models.util :as util]))

;; TODO get missing attrs from open library and do a 'merge' using isbn
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


(defn get-tabular-data
  "Given spreadsheet data, construct a vector of maps
   using the first row as keys."
  [path delim]
  (let [tsv  (csv/parse-csv (slurp path) :delimiter delim)
        hdrs (first tsv)
        rows (rest tsv)]
    (mapv #(zipmap hdrs %) rows)))

(defn parse-shelfari [path]
  (let [delim   \tab
        raw     (get-tabular-data path delim)
        parsers (map util/mk-parser shelfari-db-map)
        parsed  (mapcat #(% raw) parsers)]
    parsed))
