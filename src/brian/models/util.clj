(ns brian.models.util
  (:require [clojure.set :refer [rename-keys]]
            [clojure.string :refer [blank?]]))

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

