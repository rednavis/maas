[![Build Status](https://travis-ci.com/rednavis/maas.svg?branch=master)](https://travis-ci.com/rednavis/maas)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d888b4131a264e8fa14b26b528a1f2fc)](https://www.codacy.com/gh/rednavis/maas?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rednavis/maas&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/rednavis/maas/branch/master/graph/badge.svg)](https://codecov.io/gh/rednavis/maas)

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
```
git clone git@github.com:rednavis/maas-api.git
```

## Build project
```
./gradlew clean build
./gradlew bootRun
```

## Create dependency report
```
./gradlew clean htmlDependencyReport
```

## Update dependency
```
./gradlew dependencyUpdates
```

## Create docker image
```
./gradlew clean bootJar jibDockerBuild
```

## Swagger
http://localhost:8081/swagger-ui.html

## Camunda
http://localhost:8081/app/welcome/index.html

## H2-Console
http://localhost:8081/h2-console

## Maas-Vaadin
1. If you do not have node.js installed locally, please run `./gradlew vaadinPrepareNode` once.
2. Run the following command in this repo, to create necessary Vaadin config files `./gradlew clean vaadinPrepareFrontend`

## Run maas
`./gradlew bootRun`
1. Run maas-config
2. Run maas-sd
3. Run maas-api
4. Run maas-auth
5. Run maas-bpm
6. Run maas-data
7. Run maas-more - generate mock data
8. Run maas-vaadin