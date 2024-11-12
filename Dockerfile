# Etapa de build usando Maven com JDK 21
FROM maven:3.8.5-openjdk-21 AS build

# Define o diretório de trabalho para o projeto
WORKDIR /app

# Copia o pom.xml e baixa as dependências do projeto
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copia o código-fonte e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa final: usa uma imagem mais leve com OpenJDK 21 para rodar o app
FROM openjdk:21-slim

# Define o diretório de trabalho para o contêiner final
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/Application-0.0.1-SNAPSHOT.jar app.jar

# Define a variável de ambiente da porta
ENV PORT 8088

# Expõe a porta 8088 para Railway
EXPOSE 8088

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
