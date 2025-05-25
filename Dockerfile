FROM openjdk:17-jdk-slim

WORKDIR /app

# Activa el perfil de producción
ENV SPRING_PROFILES_ACTIVE=prod

# Copia el JAR generado por Maven
COPY target/filtroll-0.0.1-SNAPSHOT.jar app.jar

# Crea carpeta para imágenes generadas
RUN mkdir -p imagenes/resultados

# Expone el puerto del servidor
EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
