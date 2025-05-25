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

        String port = System.getenv("PORT"); // Render lo gestiona igual que Railway
        if (port != null) {
            app.setDefaultProperties(Collections.singletonMap("server.port", port));
        }

        app.run(args);
    }

    // ✅ Al iniciar, insertar un usuario + preparar imagen original por defecto si no existe
    @Bean
    public CommandLineRunner demo(UsuarioRepositorio usuarioRepositorio) {
        return args -> {
            // Insertar usuario demo
            Usuario usuario = new Usuario("Guillermo", "guille@filtroll.com");
            usuarioRepositorio.save(usuario);
            System.out.println("Usuario insertado correctamente.");

            // Crear imagen original.jpg si no existe
            Path rutaOriginal = Paths.get("imagenes/resultados/original.jpg");
            Path rutaUltima = Paths.get("imagenes/resultados/ultima.jpg");

            if (!Files.exists(rutaOriginal)) {
                Files.createDirectories(rutaOriginal.getParent());

                try (InputStream in = Filtroll.class.getResourceAsStream("/static/imagenes/predefinidas/20anios.png")) {
                    if (in != null) {
                        Files.copy(in, rutaOriginal, StandardCopyOption.REPLACE_EXISTING);
                        Files.copy(rutaOriginal, rutaUltima, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("✅ Imagen original.jpg creada por defecto (20anios.png)");
                    } else {
                        System.out.println("⚠️ No se encontró 20anios.png en recursos.");
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

    // Endpoint de prueba
    @RestController
    public class ControladorInicio {
        @GetMapping("/health")
        public String inicio() {
            return "¡Filtroll está funcionando en Render!";
        }
    }
}
