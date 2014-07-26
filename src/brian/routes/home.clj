(ns brian.routes.home
  (:require [compojure.core :refer :all]
            [brian.views.layout :as layout]
            [hiccup.core :refer :all]))


(defn icon [url img]
  (let [x [:a {:href url}
           [:img.icon {:src img}]]]
    x))

(def twitter-icon  (icon "//twitter.com/brianru" "/img/twitter.png"))
(def github-icon   (icon "//github.com/brianru" "/img/github.png"))
(def linkedin-icon (icon "https://www.linkedin.com/in/brianjrubinton"
                         "/img/linkedin.png"))

(defn home []
  (layout/common
    [:div.top-banner
     [:div [:ul
            [:li [:span.my-name "Brian James Rubinton"]]
            [:li github-icon twitter-icon linkedin-icon]]]]
    ;[:h2 "This is my website."]
    ;[:div [:span "links to stuff"]]
    ;[:div [:span "github stats go here"]]
    ;[:div [:span "other stats"]]
    ))

; top: big, full screen-height image with intro in the middle (height: 100vh)
;      and a button to contact me. maybe it opens a form right there,
;      or maybe it scrolls you to the bottom

; middle: what i want to do, what i'm doing, and what i've done
;         in that order

; bottom: contact information

(defroutes home-routes
  (GET "/" [] (home)))
