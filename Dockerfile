FROM adoptopenjdk:11-jre-hotspot
MAINTAINER abramov-samuel.de
COPY build/libs/spring-rocket-1.0.jar  spring-rocket-1.0.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/spring-rocket-0.0.1.jar"]
