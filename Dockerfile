FROM openjdk:17-jdk-slim
WORKDIR /app

# Añade variable de entorno activa
ENV SPRING_PROFILES_ACTIVE=prod

COPY target/filtroll-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]