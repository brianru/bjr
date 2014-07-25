(ns brian.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [& body]
  (html5
    [:head
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     [:title "BJR"]
     (include-css "/css/screen.css"
                  "//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css")
     (include-js "//code.jquery.com/jquery-1.11.0.min.js"
                 "//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js")]
    [:body body]))
