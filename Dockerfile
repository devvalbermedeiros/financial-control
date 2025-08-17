# Estágio de Build
FROM gradle:8.8.0-jdk21 AS build
WORKDIR /home/gradle/project

# Copia os arquivos de definição de build e o wrapper do Gradle
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

# Baixa as dependências para aproveitar o cache
RUN ./gradlew dependencies --no-daemon

# Copia o restante do código-fonte da aplicação
COPY src ./src

# Gera o artefato final
RUN ./gradlew bootJar --no-daemon

# Estágio de Execução
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]