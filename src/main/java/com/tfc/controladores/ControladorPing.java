package com.tfc.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorPing {

    @GetMapping("/")
    public String ping() {
        return "✔ Filtroll desplegada correctamente en Railway";
    }
}
