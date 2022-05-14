FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM adoptopenjdk/openjdk11:latest
COPY target/transactions-0.0.1-SNAPSHOT.war transactions.war
ENTRYPOINT ["java","-jar","/transactions.war"]