# maas
Material assets management system.

## Technology stack
- Java 12
- Spring Boot
- Spring Data
- Spring Security
- Gradle
- Log4j
- Lombok
- Swagger

## Clone project

`git clone git@github.com:rednavis/maas-api.git`

## Build project

`./gradlew clean build`

`./gradlew bootRun`

## Create dependency report

`./gradlew clean htmlDependencyReport`

## Update dependency

`./gradlew dependencyUpdates`

## Create docker image

`./gradlew clean bootJar jibDockerBuild`

## Swagger

http://localhost:8081/swagger-ui.html