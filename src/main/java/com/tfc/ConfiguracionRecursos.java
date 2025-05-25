package com.tfc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRecursos implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Recursos generados dinámicamente desde el sistema de archivos
        registry.addResourceHandler("/imagenes/resultados/**")
                .addResourceLocations("file:imagenes/resultados/")
                .setCachePeriod(0);

        // Recursos de usuarios (historial)
        registry.addResourceHandler("/imagenes/usuarios/**")
                .addResourceLocations("file:imagenes/usuarios/")
                .setCachePeriod(0);

        // Recursos predefinidos incluidos en el JAR
        registry.addResourceHandler("/imagenes/predefinidas/**")
                .addResourceLocations("classpath:/static/imagenes/predefinidas/")
                .setCachePeriod(0);

        System.out.println("🛠 ConfiguracionRecursos ACTIVADA");
    }
}
