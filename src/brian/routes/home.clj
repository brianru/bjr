(ns brian.routes.home
  (:require [compojure.core :refer :all]
            [brian.views.layout :as layout]
            [hiccup.core :refer :all]))

(defn home []
  (layout/common [:h1 "Hello World!"]
                 [:h2 "My name is Brian James Rubinton."]
                 [:br]
                 [:h2 "This is my website."]
                 [:div [:span "links to stuff"]]
                 [:div [:span "github stats go here"]]
                 [:div [:span "other stats"]]
                 ))

; top: big, full screen-height image with intro in the middle
;      and a button to contact me. maybe it opens a form right there,
;      or maybe it scrolls you to the bottom

; middle: what i want to do, what i'm doing, and what i've done
;         in that order

; bottom: contact information

(defroutes home-routes
  (GET "/" [] (home)))
