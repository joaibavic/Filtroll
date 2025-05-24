FROM openjdk:17-jdk-slim
WORKDIR /app

# Añade variable de entorno activa
ENV SPRING_PROFILES_ACTIVE=prod

# Copia el JAR compilado
COPY target/filtroll-0.0.1-SNAPSHOT.jar app.jar

# Copia los recursos estáticos para que estén disponibles en tiempo de ejecución
COPY src/main/resources/static /app/static

# Exponer el puerto por el que Spring Boot servirá la app
EXPOSE 8080

# Inicia la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
