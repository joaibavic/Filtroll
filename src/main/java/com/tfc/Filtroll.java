package com.tfc;

import com.tfc.modelo.Usuario;
import com.tfc.repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
public class Filtroll {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Filtroll.class);

        String port = System.getenv("PORT"); // Utiliza el puerto asignado por Railway
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
        };
    }

    // Endpoint de prueba para verificar que la app responde
    @RestController
    public class ControladorInicio {

        @GetMapping("/health")
        public String inicio() {
            return "¡Filtroll está funcionando en Railway!";
        }
    }

}
