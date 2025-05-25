package com.tfc.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionRecursos implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/resultados/**")
                .addResourceLocations("file:imagenes/resultados/")
                .setCachePeriod(0);
        System.out.println("🛠 ConfiguracionRecursos ACTIVADA");
    }
}
