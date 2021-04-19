FROM adoptopenjdk:11-jre-hotspot
MAINTAINER abramov-samuel.de
COPY build/libs/spring-rocket-0.0.1.jar  spring-rocket-0.0.1.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/spring-rocket-0.0.1.jar"]
