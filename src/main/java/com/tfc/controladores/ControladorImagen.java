package com.tfc.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ControladorImagen {

    @GetMapping("/marco-general")
    public String vistaPrincipal(Model model) {
        model.addAttribute("version", System.currentTimeMillis());
        return "marco-general";
    }

    @PostMapping("/imagenes/subir")
    public String subirImagen(@RequestParam("imagen") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return "redirect:/marco-general?error";
        }

        Long idUsuario = 1L;
        String nombreImagen = "foto_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".jpg";

        Path rutaCarpetaUsuario = Paths.get("imagenes/usuarios/" + idUsuario);
        Path rutaFinalUsuario = rutaCarpetaUsuario.resolve(nombreImagen);
        Path rutaResultados = Paths.get("imagenes/resultados/ultima.jpg");
        Path rutaOriginal = Paths.get("imagenes/resultados/original.jpg");

        try {
            Files.createDirectories(rutaCarpetaUsuario);
            Files.write(rutaFinalUsuario, archivo.getBytes(), StandardOpenOption.CREATE);

            Files.createDirectories(rutaResultados.getParent());
            Files.copy(rutaFinalUsuario, rutaResultados, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(rutaFinalUsuario, rutaOriginal, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Imagen subida como ultima.jpg y original.jpg correctamente");

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/marco-general?error=escritura";
        }

        return "redirect:/marco-general";
    }

    @PostMapping("/imagenes/seleccionar-predefinida")
    public String seleccionarPredefinida(@RequestParam String imagen) {
        try (InputStream in = getClass().getResourceAsStream("/static/imagenes/predefinidas/" + imagen)) {
            if (in == null) {
                System.out.println("❌ No se encontró la imagen predefinida en el classpath: " + imagen);
                return "redirect:/marco-general?error=no-imagen";
            }

            Path destino = Paths.get("imagenes/resultados/ultima.jpg");
            Path copiaOriginal = Paths.get("imagenes/resultados/original.jpg");

            Files.createDirectories(destino.getParent());
            Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(destino, copiaOriginal, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Imagen predefinida seleccionada desde classpath: " + imagen);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/marco-general?error=lectura";
        }
        return "redirect:/marco-general";
    }

    @GetMapping("/galeria")
    @ResponseBody
    public String galeria() {
        Long idUsuario = 1L;
        Path carpetaFiltros = Paths.get("imagenes/usuarios/" + idUsuario + "/filtros");

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Galería de Filtros</title></head><body>");
        html.append("<h2>Galería de imágenes filtradas</h2>");

        if (Files.exists(carpetaFiltros)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(carpetaFiltros, "*.jpg")) {
                for (Path archivo : stream) {
                    String nombreArchivo = archivo.getFileName().toString();
                    html.append("<div style='display:inline-block;margin:10px;text-align:center;'>")
                            .append("<img src='/imagenes/usuarios/1/filtros/").append(nombreArchivo)
                            .append("' style='max-width:200px;'><br>").append(nombreArchivo).append("</div>");
                }
            } catch (IOException e) {
                html.append("<p>Error leyendo imágenes.</p>");
            }
        } else {
            html.append("<p>No hay imágenes aún.</p>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    @GetMapping("/")
    public String redirigirInicio() {
        return "redirect:/marco-general";
    }
}
