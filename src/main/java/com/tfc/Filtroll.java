package com.tfc;

import com.tfc.modelo.Usuario;
import com.tfc.repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Filtroll {

    public static void main(String[] args) {
        SpringApplication.run(Filtroll.class, args);
    }

    @Bean
    public CommandLineRunner demo(UsuarioRepositorio usuarioRepositorio) {
        return args -> {
            Usuario usuario = new Usuario("Guillermo", "guille@filtroll.com");
            usuarioRepositorio.save(usuario);
            System.out.println("Usuario insertado correctamente.");
        };
    }
}
