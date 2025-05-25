@echo off
echo ==========================================
echo 🚀 Iniciando despliegue de Filtroll en Render...
echo ==========================================

REM Paso 1: Construir la imagen Docker
echo 🛠 Construyendo imagen Docker...
docker build -t filtroll .

IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al construir la imagen Docker.
    pause
    exit /b %ERRORLEVEL%
)

REM Paso 2: Etiquetar la imagen con el nombre de tu servicio en Render
echo 🏷 Etiquetando imagen...
docker tag filtroll registry.render.com/filtrollweb/filtroll:latest

REM Paso 3: Subir imagen al registro de Render
echo ⬆️ Subiendo imagen a Render...
docker push registry.render.com/filtrollweb/filtroll:latest

IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al hacer push al registro de Render.
    pause
    exit /b %ERRORLEVEL%
)

echo ✅ Despliegue completado correctamente.
pause
