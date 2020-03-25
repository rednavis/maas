[![Build Status](https://travis-ci.com/rednavis/maas.svg?branch=master)](https://travis-ci.com/rednavis/maas)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d888b4131a264e8fa14b26b528a1f2fc)](https://www.codacy.com/gh/rednavis/maas?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rednavis/maas&amp;utm_campaign=Badge_Grade)

# maas
Material assets management system.

## Technology stack
- Java 12
- Mongo
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