FROM openjdk:17-jdk
MAINTAINER Samuel Abramov
COPY build/libs/spring-rocket-1.0.1.jar spring-rocket-1.0.1.jar
EXPOSE 8022
ENTRYPOINT ["java","-jar","/spring-rocket-1.0.1.jar"]

