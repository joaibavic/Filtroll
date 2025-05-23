# Filtroll 📷🌟

Aplicador de filtros de imagen desarrollado en Java con Spring Boot. Permite subir imágenes, aplicar filtros visuales (como sepia, clarendon, alto contraste, etc.), guardar un historial de ediciones y visualizarlas en una galería.

---

## ✨ Características principales
- Subida de imágenes personalizadas
- Selección de imágenes de prueba
- Aplicación de filtros visuales desde el backend puro
- Vista previa actualizada sin uso de JavaScript
- Historial de filtros aplicados
- Opcion para eliminar una imagen o todo el historial
- Interfaz visual moderna y responsive

---

## 📊 Tecnologías usadas
- Java 17
- Spring Boot 3.2.x
- Maven
- Thymeleaf
- Hibernate (JPA)
- MySQL (local)
- HTML + CSS personalizados (sin JS)

---

## 🚀 Despliegue en Railway

### 1. Subir el proyecto a GitHub
```bash
git init
git add .
git commit -m "Proyecto Filtroll listo para producción"
git remote add origin https://github.com/TU_USUARIO/Filtroll
git push -u origin master
```

### 2. Crear proyecto en Railway
- Entra en [https://railway.app](https://railway.app)
- Selecciona **New Project > Deploy from GitHub**
- Selecciona este repositorio

### 3. Configura puerto (si no está hecho):
En `application.properties`, asegúrate de tener:
```properties
server.port=${PORT:8080}
```

### 4. Configura comando de inicio:
Railway ejecutará:
```bash
java -jar target/filtroll.jar
```
(Asegúrate de compilar el `.jar` antes o configura la build con Maven o Gradle en Railway)

### 5. Despliegue listo
Tu app estará disponible en algo como:
```
https://filtroll.up.railway.app
```

---

## 📊 Estructura del proyecto
```
TFC_JIV/
├── src/
│   ├── main/java/com/tfc/controladores
│   ├── main/resources/static/styles
│   ├── main/resources/static/imagenes
│   ├── main/resources/templates
├── target/ (no se sube)
├── pom.xml
├── .gitignore
├── README.md
```

---

## 📖 Licencia
Este proyecto ha sido desarrollado como parte del Trabajo de Fin de Ciclo del autor en FP DAM. Uso libre con fines educativos y demostrativos.

---

Desarrollado con ❤ por [@joaibavic](https://github.com/joaibavic)
