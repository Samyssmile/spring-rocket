# Spring-Rocket

Spring-Rocket is a preconfigured Spring-Boot 2 project with some very important and often used features. This project was built to save a lot of time on the standard features.

Just clone and start building your app.

## Minimal Requirements ##
* Java 11
* Gradle 7.2
* Spring-Boot 2.5.6


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
* Apache log4j2 Logging
* i18n
* Docker support.

## Clone & Try! ##

```gradlew clean assemble```

```gradlew bootRun```

```gradlew test```

### Swagger ###


#### Try it ####
    * Developer Port: 8081
    * Production Port: 8080


    Swagger URL: http://localhost:8081/api/swagger-ui.html
You need to use ``/users/signup`` endpoint to create a user.
As a response you will get something like this.

``{
"jsonWebToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2MTg2ODU1NjYsImV4cCI6MTYxODY4OTE2Nn0.m4lGuZjkjKIOiSg43QunGWs2Scf3e9Yu40__OMPsZ6I"
}``

Copy your jsonWebToken, click on authorize and paste "YOUR_TOKEN" without "Bearer" as prefix. Now you are authenticated and can use all endpoints.

### Actuator

    ``http://localhost:8081/actuator/health``

    ``http://localhost:8081/actuator/info``

    ``http://localhost:8081/actuator/prometheus``

#### Grafana
You can run Grafana as a docker container. The default username, password for Grafana is admin,admin.
You can use the "startGrafana" Script to run a Grafana Container, you will find that script in your commands folder.

    ``http://localhost:3000/``
    ``Login: admin - admin

### Docker
Every modern project should have Docker support. Spring-Rocket already configured to start inside a Docker container.

Build your image with

    docker build -t spring-rocket:latest .

Run your docker image with

    docker run spring-rocket
