;; # The Public Website
(ns brian.routes.home
  (:require [compojure.core :refer :all]
            [brian.views.layout :as layout]
            [hiccup.core :refer :all]))


(defn icon
  "Construct markup for icon links.
   TODO: make data uris for images"
  [url img]
  (let [x [:a {:href url}
           [:img.icon {:src img}]]]
    x))

(def twitter-icon  (icon "//twitter.com/brianru" "/img/twitter.png"))
(def github-icon   (icon "//github.com/brianru" "/img/github.png"))
(def linkedin-icon (icon "https://www.linkedin.com/in/brianjrubinton"
                         "/img/linkedin.png"))

(defn home
  "# Homepage


   ## Top - first impression
   
   Full (vertical) page with an opaque background image (that I took).
   Name in the middle. Most important links directly beneath.
  

   ## Middle - depth - data

   -  reading habits (shelfari)
   -  fitness (fitbit)
   -  programming (github)
  

   ## Bottom - tbd
  
   -  contact information?"
  []
  (layout/common
    [:div.top-banner
     [:div [:ul
            ; use initial when viewed from mobile phone
            [:li [:span.my-name "Brian James Rubinton"]]
            [:li github-icon twitter-icon linkedin-icon]]]]
    [:div.dashboard.container-fluid
     [:div.row ;; d3 populates these with svgs
      [:div.col-md-4]
      [:div.col-md-4 "dude"] ;; http://www.githubarchive.org/
      [:div.col-md-4 "apple"]]] ;; fitbit
    ))

(defroutes home-routes
  (GET "/" [] (home)))
