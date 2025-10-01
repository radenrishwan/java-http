# Multi-stage build for Java application with Gradle

# Build stage
FROM gradle:8.4-jdk17 AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy source code
COPY src/ src/

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build --no-daemon

# Runtime stage
FROM openjdk:26-slim-bookworm

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port (default 8080, can be overridden with PORT env var)
EXPOSE 8080

# Set default environment variables
ENV PORT=8080

# Run the application
CMD ["java", "-jar", "app.jar"]
