;; # Fetch and format github data.
(ns brian.models.github
  (:require [clj-http.client :as http]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.data.json :as json]
            [brian.models.util :as util]))

(def source "https://osrc.dfm.io/brianru.json")

(def github-db-map
  {:repos {:repo :name
           :count :commits}
   :languages {:language :name
               :count :commits}})

(defn parse-github [url]
  (let [raw (http/get url)
        body (keywordize-keys (json/read-str (:body raw)))
        repos ((util/mk-parser [:repos (:repos github-db-map)])
               (:repositories body))
        langs ((util/mk-parser [:languages (:languages github-db-map)])
               (-> body (:usage) (:languages)))]
    repos))

(let [raw (http/get source)
      body (keywordize-keys (json/read-str (:body raw)))
      repos ((util/mk-parser [:repos (:repos github-db-map)])
             (:repositories body))]
  repos)

(defn refresh
  "TODO Fetch new github data if a day has elapsed."
  []
  (parse-github source))

(refresh)
