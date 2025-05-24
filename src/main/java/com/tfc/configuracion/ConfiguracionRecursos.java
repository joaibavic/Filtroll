package com.tfc.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRecursos implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Quita esta línea porque no se usa en producción:
        // registry.addResourceHandler("/imagenes/**").addResourceLocations("file:imagenes/");

        // Esta línea hace que se sirvan las imágenes desde dentro del JAR (classpath)
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("classpath:/static/imagenes/");
    }
}
