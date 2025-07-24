# Stage 1: Build the application
FROM openjdk:20-jdk AS build
WORKDIR /app

# Copy Maven wrapper and project files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src ./src

# Grant execution rights and build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:20-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
