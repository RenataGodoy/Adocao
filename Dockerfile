# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="renata godoy"
LABEL description="This is the Dockerfile for the Adoption service"

# Etapa 1: Build com Maven e JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e as dependências do projeto para o contêiner
COPY pom.xml ./pom.xml
COPY src ./src

# Baixar todas as dependências do Maven
RUN mvn dependency:go-offline -B

# Fazer o build do projeto, compilando e empacotando o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Rodar o aplicativo com JDK 21
FROM eclipse-temurin:21-jre AS runtime

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=build /app/target/Application-0.0.1-SNAPSHOT.jar /app/application.jar

# Expor a porta onde o Spring Boot irá rodar
EXPOSE 8080

# Comando para rodar o JAR gerado
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
