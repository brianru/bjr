;; # The Public Website
(ns brian.routes.home
  (:require [compojure.core :refer :all]
            [brian.views.layout :as layout]
            [brian.models.blog :refer [blog-posts]]
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
    ;;[:div.dashboard.container-fluid
    ;; [:div.row ;; d3 populates these with svgs
    ;;  [:div.col-md-4.text-center.github]
    ;;  [:div.col-md-4.text-center.reading] ;; http://www.githubarchive.org/
    ;;  [:div.col-md-4.text-center]]] ;; fitbit
    ))

(defn mk-blog-headline [{:keys [id title date] :as post}]
  [:div.headline
   [:a {:href (str "/blog/" id)}
    [:span title]]])

(defn blog
  "Display list of blog post titles."
  []
  (layout/common
    [:div.top-banner
     [:div (map mk-blog-headline blog-posts)]]
    ))

(defn mk-blog-post [{:keys [body date] :as post}]
  [:div.body [:span body]])

(defn blog-post
  "Display blog post."
  [id]
  (let [post (get blog-posts id)]
    (layout/common
      [:div.top-banner
       [:div
        (mk-blog-headline post)]
       [:div
        (mk-blog-post post)]])))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/blog" [] (blog))
  (GET "/blog/:id" [id] (blog-post id)))
