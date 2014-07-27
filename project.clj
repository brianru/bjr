(defproject brian "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "0.5.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [org.clojure/java.jdbc "0.3.4"]
                 [postgresql/postgresql "9.1-901.jdbc4"]
                 [korma "0.3.2"]
                 [clojure-csv/clojure-csv "2.0.1"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-environ "0.5.0"]]
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
