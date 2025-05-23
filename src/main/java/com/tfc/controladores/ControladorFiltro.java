package com.tfc.controladores;

import com.tfc.servicios.ServicioFiltro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControladorFiltro {

    @Autowired
    private ServicioFiltro servicioFiltro;

    @PostMapping("/filtros/aplicar")
    public String aplicarFiltro(@RequestParam("filtro") String filtroNombre) {
        servicioFiltro.aplicarFiltro(filtroNombre);
        return "redirect:/marco-general";
    }
}
