# ---------- Stage 1: Build the JAR ----------
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy Gradle config first for caching
COPY build.gradle settings.gradle.kts gradlew ./
COPY gradle gradle
RUN ./gradlew --version

# Copy all source code
COPY . .

# Build the Spring Boot JAR (skip tests to speed up CI/CD)
RUN ./gradlew clean bootJar -x test --no-daemon

# ---------- Stage 2: Run the app ----------
FROM amazoncorretto:21
WORKDIR /app

# Copy the JAR built in the previous stage
COPY --from=build /app/build/libs/ws_todo-0.0.1-SNAPSHOT.jar /app/ToDo_Webservice.jar

# Expose the Spring Boot port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "/app/ToDo_Webservice.jar"]
