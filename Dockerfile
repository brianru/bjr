FROM dockerfile/java
MAINTAINER Brian James Rubinton <brianrubinton@gmail.com>

RUN sudo apt-get update

ADD target/brian-for-docker-standalone.jar /srv/brian.jar

EXPOSE 8080

CMD ["java", "-jar", "/srv/brian.jar"]
