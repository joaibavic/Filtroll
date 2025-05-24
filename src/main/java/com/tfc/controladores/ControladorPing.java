package com.tfc.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorPing {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
