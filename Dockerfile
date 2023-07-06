#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /home/app
COPY .  .
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true
 
#
# Package stage
#
FROM openjdk:18-ea-11-jdk-alpine3.15
WORKDIR /usr/local/lib/
COPY --from=build /home/app/application/target/*.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/usr/local/lib/app.jar"]


