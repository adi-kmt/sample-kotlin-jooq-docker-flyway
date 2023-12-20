#!/bin/bash
source ~/.bash_profile
#running postgres
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=pass123 postgres:16.1-alpine
#cleaning the build folder
rm -r build
#migrate scripts to db using flyway plugin
./gradlew flywayMigrate
#generating jooq files and creating jar
./gradlew clean build
 #kill all docker process
docker kill $(docker ps -q)
#build multi-container application
docker-compose up --build