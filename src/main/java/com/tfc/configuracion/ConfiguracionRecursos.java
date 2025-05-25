package com.tfc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRecursos implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sirve las imágenes generadas en tiempo de ejecución desde la carpeta externa
        registry.addResourceHandler("/imagenes/resultados/**")
                .addResourceLocations("file:imagenes/resultados/")
                .setCachePeriod(0); // Cache desactivada para mostrar la última versión
    }
}
