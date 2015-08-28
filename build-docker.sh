lein clean

lein with-profile production uberjar

mv target/*standalone.jar target/brian-for-docker-standalone.jar

docker build -t brian .

