# Build stage
FROM gradle:latest AS build
WORKDIR /usr/app/
COPY . .
RUN gradle build

# Package stage
FROM openjdk:latest
ENV JAR_NAME=app.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/$JAR_NAME .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]