# Etapa 1: Construir a aplicação usando Maven 4.0.0 (caso tenha uma imagem específica)
FROM maven:3.9.4-eclipse-temurin-21 AS build
LABEL authors="Renata Godoy"
LABEL description = "Api com spingboot para adocao de animal "
# Definir o diretório de trabalho
WORKDIR /app

# Copiar o pom.xml e baixar as dependências (para aproveitar o cache do Docker)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN mvn dependency:go-offline -B

# Copiar o código fonte do projeto
COPY src src

# Construir o aplicativo
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem para rodar a aplicação com Java
FROM eclipse-temurin:21-jre

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da etapa de construção
COPY --from=build /app/target/API-Cadeia-0.0.1-SNAPSHOT.jar /app/API-Cadeia.jar

# Expor a porta que o Spring Boot vai rodar (por padrão, 8080)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/API-Cadeia.jar"]