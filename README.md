# Spring-Rocket

This is a Spring-Boot 2 bootstrap Project with some default features. Just clone and start build your app.

## Requirements ##
* Java 11
* Gradle 7.0

## Already implemented Features ##

* Auditing
* Caching
* JSON Web Token Authentication
* Open API 
* Swagger UI
* Multi profiles for dev, prod
* SQL File Data Migration
* Micrometer
* JPA
* Actuator
* Logback Logging
* i18n
* Dockerfile

## Clone & Try! ##

```gradlew clean assemble```

```gradlew bootRun```

### Swagger ###


#### Try it ####
    * Developer Port: 8081
    * Production Port: 8080


    Swagger URL: http://localhost:8081/api/swagger-ui.html
You need to use ``/users/signup`` endpoint to create a user.
As a Response you will get somethink like this.

``{
"jsonWebToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2MTg2ODU1NjYsImV4cCI6MTYxODY4OTE2Nn0.m4lGuZjkjKIOiSg43QunGWs2Scf3e9Yu40__OMPsZ6I"
}``

Copy your jsonWebToken, click on Authorize and paste "Bearer YOUR_TOKEN". Now you are authenticated to use all endpoints.

### Actuator

    ``http://localhost:8081/actuator/health``

    ``http://localhost:8081/actuator/info``

### Docker
Every modern project should have Docker support. Spring-Rocket already configures to start with Docker.

Build your image with

    sudo docker build -t spring-rocket:latest .

Run your docker image with

    sudo docker run spring-rocket
