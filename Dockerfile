# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="renata godoy"
LABEL description="This is the Dockerfile for the Adoption service"
# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw

RUN mvn dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/ApplicationAdoption-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8090

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]