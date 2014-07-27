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
            ; use initial when viewed from mobile phone
            [:li [:span.my-name "Brian James Rubinton"]]
            [:li github-icon twitter-icon linkedin-icon]]]]
    ))

; top: big, full screen-height image with intro in the middle

; middle: what i want to do, what i'm doing, and what i've done
;         in that order

; bottom: contact information

(defroutes home-routes
  (GET "/" [] (home)))
