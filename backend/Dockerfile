# Use the official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the Maven files and compile the dependencies
COPY pom.xml .
COPY src ./src

# Copy docker.properties to application.properties
RUN cp ./src/main/resources/docker.properties ./src/main/resources/application.properties
RUN mvn clean package -DskipTests

# Use a minimal JDK image to run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
RUN mkdir /app/logs
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8081
EXPOSE 8081

# Command to start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]