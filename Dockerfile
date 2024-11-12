# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="Renata Godoy"
LABEL description="This is the Dockerfile for the Projetorest service"


# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw
RUN mvn dependency:go-offline -B

# Copiar o código-fonte
COPY src ./src

# Construir o projeto (package) sem rodar os testes
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem para rodar a aplicação com Java
FROM eclipse-temurin:21-jre

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR gerado da etapa anterior
COPY --from=build /app/target/Application-0.0.1-SNAPSHOT.jar /app/Application.jar

# Expor a porta 8080 (padrão para aplicações Spring Boot)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/Application.jar"]