# Etapa de build: Usar uma imagem com OpenJDK 21 e instalar o Maven
FROM openjdk:21-slim AS build

# Instalar Maven (se não estiver presente na imagem)
RUN apt-get update && apt-get install -y maven

# Define o diretório de trabalho para o projeto
WORKDIR /app

# Copia o pom.xml e baixa as dependências do projeto
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copia o código-fonte e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa final: usar uma imagem mais leve com OpenJDK 21 para rodar o app
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
