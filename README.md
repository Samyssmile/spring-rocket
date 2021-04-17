# spring-rocket

This is an example Spring-Boot Project with some integrated features, so you dont need to implement standard features again and again. - DRY

## Requirements ##
* Java 11
* Gradle 6.5

## Implemented Features ##

* Auditing
* Caching
* JSON Web Token for REST API protection
* Open API 3.0
* Swagger UI
* Multi Profiles for dev, prod
* SQL File Data Migration
* Micrometer
* JPA
* Actuator
* Logback Logging
* i18n

## Clone & Try! ##

```gradlew clean assemble```

```gradlew bootRun```

### Swagger ###
Clone & Build

## Try it Developer Mode ##
Swagger URL: http://localhost:8081/api/swagger-ui.html
You need to use ``/users/signup`` endpoint to create a user.
As a Response you will get sometink like this.
``{
"jsonWebToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2MTg2ODU1NjYsImV4cCI6MTYxODY4OTE2Nn0.m4lGuZjkjKIOiSg43QunGWs2Scf3e9Yu40__OMPsZ6I"
}``

## Try it Production Mode ##

Swagger URL: http://localhost:8080/api/swagger-ui.html
You need to use ``/users/signup`` endpoint to create a user.