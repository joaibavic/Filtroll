# Imagen base con Java 17 (ligera)
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar compilado al contenedor
COPY target/filtroll-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 (Spring Boot)
EXPOSE 8080

# Comando de inicio de la app
ENTRYPOINT ["java", "-jar", "app.jar"]
