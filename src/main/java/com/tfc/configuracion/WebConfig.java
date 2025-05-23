package com.tfc.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 📂 Sirve imágenes desde C:/filtroll/imagenes/resultados/
        registry.addResourceHandler("/imagenes/resultados/**")
                .addResourceLocations("file:///C:/filtroll/imagenes/resultados/")
                .setCachePeriod(0); // desactiva caché para ver cambios en tiempo real

        // (Opcional) Sirve también imágenes de usuario si las necesitas
        registry.addResourceHandler("/imagenes/usuarios/**")
                .addResourceLocations("file:///C:/filtroll/imagenes/usuarios/")
                .setCachePeriod(0);

        // (Opcional) Sirve imágenes predefinidas si están fuera del proyecto
        registry.addResourceHandler("/imagenes/predefinidas/**")
                .addResourceLocations("file:///C:/filtroll/imagenes/predefinidas/")
                .setCachePeriod(0);
    }
}
