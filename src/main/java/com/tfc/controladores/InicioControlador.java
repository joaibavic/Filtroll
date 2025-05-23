package com.tfc.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioControlador {

    @GetMapping("/")
    public String inicio() {
        return "¡Filtroll está en línea!";
    }
}
