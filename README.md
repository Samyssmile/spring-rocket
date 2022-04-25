# Spring-Rocket

Spring-Rocket is a preconfigured Spring-Boot 2 project with some very important and often used features. This project was built to save a lot of time on the standard features.

Just clone and start building your app.

## Minimal Requirements ##
* Java 17
* Gradle 7.4.2
* Spring-Boot 2.6.7


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

### Docker
Every modern project should have Docker support. Spring-Rocket already configured to start inside a Docker container.

#### Image Packer
Build your docker image with (easy but slow, no dockerfile required)

    ./gradlew bootBuildImage

For some fine tuning of your image you need to modify the gradle task in build.gradle.

    tasks.named("bootBuildImage")
#### Common way
Instead of a packer,  use this command instead

    docker build -t spring-rocket:latest .


Run your docker image with run "${project.name}:${project.version}" e.g.

    docker run spring-rocket:latest


