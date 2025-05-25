package com.tfc;

import com.tfc.modelo.Usuario;
import com.tfc.repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Collections;

@SpringBootApplication
public class Filtroll {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Filtroll.class);

        String port = System.getenv("PORT");
        if (port != null) {
            app.setDefaultProperties(Collections.singletonMap("server.port", port));
        }

        app.run(args);
    }

    @Bean
    public CommandLineRunner demo(UsuarioRepositorio usuarioRepositorio) {
        return args -> {
            Usuario usuario = new Usuario("Guillermo", "guille@filtroll.com");
            usuarioRepositorio.save(usuario);
            System.out.println("Usuario insertado correctamente.");

            Path rutaOriginal = Paths.get("imagenes/resultados/original.jpg");
            Path rutaUltima = Paths.get("imagenes/resultados/ultima.jpg");

            if (!Files.exists(rutaOriginal)) {
                Files.createDirectories(rutaOriginal.getParent());

                String[] imagenes = { "20anios.png", "33anios.png", "50anios.png" };
                String seleccionada = imagenes[(int) (Math.random() * imagenes.length)];

                try (InputStream in = Filtroll.class.getResourceAsStream("/static/imagenes/predefinidas/" + seleccionada)) {
                    if (in != null) {
                        Files.copy(in, rutaOriginal, StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(rutaOriginal, rutaUltima, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("✅ Imagen por defecto seleccionada: " + seleccionada);
                    } else {
                        System.out.println("⚠️ No se encontró en el classpath: " + seleccionada);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error copiando imagen por defecto:");
                    e.printStackTrace();
                }
            } else {
                System.out.println("ℹ️ Ya existe original.jpg, no se sobrescribe.");
            }
        };
    }

    @RestController
    public class ControladorInicio {
        @GetMapping("/health")
        public String inicio() {
            return "¡Filtroll está funcionando en Render!";
        }
    }
}
