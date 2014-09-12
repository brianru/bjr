;; # Fetch and format github data.
(ns brian.models.github
  (:require [clj-http.client :as http]
            [clojure.walk :refer [keywordize-keys]]
            [clojure.data.json :as json]
            [brian.models.util :as util]))

(def source "https://osrc.dfm.io/brianru.json")

(def github-db-map
  {:repos (sorted-map :name :name
                      :count :commits)
   :languages (sorted-map :language :name
                          :count :commits)})

(defn parse-github [url]
  (let [raw (http/get url)
        body (keywordize-keys (json/read-str (:body raw)))
        repos ((util/mk-parser (:repos github-db-map)) (:repositories body))
        langs ((util/mk-parser (:languages github-db-map)) (-> body
                                                               (:usage)
                                                               (:languages)))]
    repos))

(defn refresh
  "TODO Fetch new github data if a day has elapsed."
  []
  (parse-github source))
