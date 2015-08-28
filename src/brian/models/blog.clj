(ns brian.models.blog
  (:require [clojure.java.io :as io]
            [markdown.core :refer [md-to-html-string]]))

(def blog-posts {})

(defn parse-post [post]
  (md-to-html-string post))

(defn load-posts [path]
  (let [file-names (.list (io/file path))
        file-contents (map #(slurp (str path %)) file-names)
        posts (map parse-post file-contents)]
    (map println posts)))

#_(load-posts "resources/blog/drafts/")
