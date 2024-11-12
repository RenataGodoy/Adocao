# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="renata godoy"
LABEL description="This is the Dockerfile for the Adoption service"
# Set the working directory
WORKDIR /app

# Set environment variables to ensure UTF-8 encoding
ENV LANG C.UTF-8
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

# Copy pom.xml and download dependencies
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./


RUN chmod +x mvnw

RUN mvnw clean dependency:purge-local-repository -B

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

# Expose port 8088
EXPOSE 8088

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
