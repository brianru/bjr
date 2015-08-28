(ns brian.main
  (:require [brian.repl :refer :all])
  (:gen-class))

(defn -main [& args]
  (println "starting server")
  (start-server))
