LABEL authors="Mohamed Soliman"
FROM maven:3.8.3-openjdk-17 AS build
COPY .. .
RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=build ./target/vodafoneTask.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]
