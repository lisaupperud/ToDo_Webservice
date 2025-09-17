# ---------- Stage 1: Build the JAR ----------
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy Gradle config files first (for better caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN ./gradlew --version

# Copy the full project
COPY . .

# Build the application (skip tests for faster CI/CD builds)
RUN ./gradlew clean build -x test --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21
# Set the working directory in the container
WORKDIR /app
# Copy the JAR file into the container named /app and renames it to ToDo_Webservice
COPY build/libs/ws_todo-0.0.1-SNAPSHOT.jar /app/ToDo_Webservice.jar
# Expose the port that the application will run on (Must reflect Spring Boot's PORT)
EXPOSE 8080
# Command to run the app
ENTRYPOINT ["java", "-jar", "/app/ToDo_Webservice.jar"]