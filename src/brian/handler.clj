;; # The App
;;
;; The application can be split into the following verticals:
;;
;; -  Public Website (`home-routes`)
;; -  Public Data (`api-routes`)
;; -  Private Website (`tbd`)
;;
(ns brian.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [brian.routes.api :refer [api-routes]]
            [brian.routes.home :refer [home-routes]]))

(defn init []
  (println "brian is starting"))

(defn destroy []
  (println "brian is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes api-routes app-routes)
      (handler/site)
      (wrap-base-url)))
