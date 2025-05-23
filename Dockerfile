# Imagen base oficial de Java 20 (JDK)
FROM eclipse-temurin:20-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado en tu máquina al contenedor
COPY target/filtroll-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usa Spring Boot
EXPOSE 8080

# Comando que se ejecuta al iniciar el contenedor, con perfil `prod`
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
