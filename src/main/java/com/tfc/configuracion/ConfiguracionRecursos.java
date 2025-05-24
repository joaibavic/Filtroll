// src/main/java/com/tfc/configuracion/ConfiguracionRecursos.java
package com.tfc.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRecursos implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expone la carpeta "imagenes" como recurso accesible vía HTTP
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:imagenes/");
    }
}
