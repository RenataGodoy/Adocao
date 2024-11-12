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

# Verificar permissões do mvnw
RUN chmod +x mvnw && ls -l ./mvnw

# Testar conexão de rede
RUN curl -I https://repo.maven.apache.org

# Tentar baixar as dependências Maven
RUN ./mvnw dependency:resolve -B

# Copiar o código-fonte
COPY src src

# Construir a aplicação
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/ApplicationAdoption-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]
