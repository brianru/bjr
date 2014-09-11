(defproject brian "0.1.3"
  :description "a web app for data heavy personal websites"
  :url "http://www.brianjamesrubinton.com"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [ring/ring-json "0.3.1"]
                 [org.clojure/data.json "0.2.5"]
                 [clj-http "1.0.0"]
                 [clj-oauth "1.5.1"]
                 [fogus/bacwn "0.4.0"]
                 [clj-time "0.8.0"]
                 [clojure-csv/clojure-csv "2.0.1"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-marginalia "0.7.1"]]
  :java-agents [[com.newrelic.agent.java/newrelic-agent "2.19.0"]]
  :min-lein-version "2.0.0"
  :ring {:handler brian.handler/app
         :init brian.handler/init
         :destroy brian.handler/destroy}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [ring/ring-devel "1.2.1"]]}})
