(ns brian.models.util
  (:require [clojure.set :refer [rename-keys]]
            [clojure.string :refer [blank?]]))

(defn filter-and-swap
  "Filter map by keys (`old-ks`) then swap the old keys (`old-ks`)
   for the new keys (`new-ks`)."
  [data attr-map]
  (rename-keys (select-keys data (keys attr-map)) attr-map))

(defn bad? [x]
  "We want to filter out entries with invalid or insufficient (aka 'bad')
   attribuve values."
  (cond (string? x) (blank? x)
        (number? x) (nil? x)
        :else       true))

(defn parse-entries
  "TODO iterate through the entries once
   TODO use a map instead of the two vectors"
  [relation data attr-map]
  (let [massaged (mapv #(filter-and-swap % attr-map) data)
        filtered (filter #(not-any? bad? (vals %)) massaged)]
    (mapv #(flatten (cons relation %)) filtered)))

(defn mk-parser
  "Takes a k-v pair
   where k == an entity type
     and v == a map pairing old attribute keys with new attribute keys."
  [[kind attr-map]]
  (fn [data] (parse-entries kind data attr-map)))

