# Etapa 1: build
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiar pom.xml y dependencias para caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del proyecto
COPY src ./src

# Compilar y empaquetar
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Carpeta para logs (opcional)
RUN mkdir -p logs

# Copiar el jar desde la etapa build
COPY --from=build /app/target/*.jar app.jar

# Puerto de la app
ARG PORT=8080
ENV PORT=$PORT
EXPOSE $PORT

# Ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]