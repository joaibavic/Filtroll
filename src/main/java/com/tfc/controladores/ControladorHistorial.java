package com.tfc.controladores;

import com.tfc.modelo.HistorialImagen;
import com.tfc.repositorio.HistorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.*;
import java.util.List;

@Controller
public class ControladorHistorial {

    @Autowired
    private HistorialRepositorio historialRepositorio;

    private final String RUTA_CARPETA = "C:/filtroll/imagenes/usuarios/1/filtros/";

    @GetMapping("/historial")
    @ResponseBody
    public String mostrarHistorial() {
        Long usuarioId = 1L;
        List<HistorialImagen> imagenes = historialRepositorio.findAll();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>");
        html.append("<title>Historial de Filtros</title>");
        html.append("<link rel='stylesheet' href='/styles/estiloGeneral.css'>");
        html.append("<link rel='stylesheet' href='/styles/galeria.css'>");
        html.append("<link rel='stylesheet' href='/styles/responsive.css'>");
        html.append("<style>");
        html.append("header { display: flex; justify-content: flex-end; gap: 10px; padding: 20px; }");
        html.append("button { background-color: #ffffff22; color: white; border: 1px solid #ffffff44; padding: 10px 20px; border-radius: 8px; cursor: pointer; transition: 0.3s; }");
        html.append("button:hover { background-color: #ffffff55; }");
        html.append(".tarjeta { background-color: rgba(255, 255, 255, 0.07); padding: 15px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.3); margin: 10px; text-align: center; }");
        html.append(".tarjeta img { max-width: 200px; border-radius: 8px; }");
        html.append(".contenedor-imagenes { display: flex; flex-wrap: wrap; justify-content: center; gap: 20px; padding: 20px; }");
        html.append("</style>");
        html.append("</head><body>");

        html.append("<header>");
        html.append("<form method='GET' action='/marco-general'><button type='submit'>Volver</button></form>");
        html.append("<form method='POST' action='/historial/borrar-todo'><button type='submit'>Borrar todo</button></form>");
        html.append("</header>");

        html.append("<h1 style='text-align:center;'>Historial de filtros aplicados</h1>");
        html.append("<div class='contenedor-imagenes'>");

        if (imagenes.isEmpty()) {
            html.append("<p style='text-align:center;'>No hay imágenes en el historial.</p>");
        } else {
            for (HistorialImagen img : imagenes) {
                if (img.getUsuarioId().equals(usuarioId)) {
                    html.append("<div class='tarjeta'>")
                            .append("<img src='/imagenes/usuarios/1/filtros/").append(img.getNombreArchivo()).append("' alt='imagen filtrada' />")
                            .append("<p>").append(img.getFechaAplicacion()).append("</p>")
                            .append("<form method='POST' action='/historial/eliminar'>")
                            .append("<input type='hidden' name='id' value='").append(img.getId()).append("' />")
                            .append("<input type='hidden' name='archivo' value='").append(img.getNombreArchivo()).append("' />")
                            .append("<button type='submit'>Eliminar</button>")
                            .append("</form>")
                            .append("</div>");
                }
            }
        }

        html.append("</div></body></html>");
        return html.toString();
    }

    @PostMapping("/historial/eliminar")
    public String eliminarImagen(@RequestParam Long id, @RequestParam String archivo) {
        historialRepositorio.deleteById(id);

        File file = new File(RUTA_CARPETA + archivo);
        if (file.exists()) {
            file.delete();
        }

        return "redirect:/historial";
    }

    @PostMapping("/historial/borrar-todo")
    public String borrarTodo() {
        List<HistorialImagen> imagenes = historialRepositorio.findAll();
        for (HistorialImagen img : imagenes) {
            if (img.getUsuarioId().equals(1L)) {
                File file = new File(RUTA_CARPETA + img.getNombreArchivo());
                if (file.exists()) {
                    file.delete();
                }
                historialRepositorio.deleteById(img.getId());
            }
        }
        return "redirect:/historial";
    }
}
