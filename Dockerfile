# Etapa 1: Construir a aplicação usando Maven
FROM maven:gi-eclipse-temurin-21 AS build
LABEL authors="Renata Godoy"
LABEL description="Api com springboot para adocao de animal"

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o pom.xml
COPY pom.xml ./

# Baixar as dependências para aproveitar o cache
RUN mvn dependency:go-offline -B

# Copiar o código fonte
COPY src ./src

# Construir a aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem para rodar a aplicação com Java
FROM eclipse-temurin:21-jre

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da etapa de construção
COPY --from=build /app/target/Application-0.0.1-SNAPSHOT.jar /app/Application.jar

# Expor a porta que o Spring Boot vai rodar (por padrão, 8080)
EXPOSE 8000

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/Application.jar"]