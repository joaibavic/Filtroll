package com.tfc.servicios;

import com.tfc.modelo.HistorialImagen;
import com.tfc.repositorio.HistorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ServicioFiltro {

    private final String RUTA_ORIGINAL = "C:/filtroll/imagenes/resultados/original.jpg";
    private final String RUTA_RESULTADO = "C:/filtroll/imagenes/resultados/ultima.jpg";

    @Autowired
    private HistorialRepositorio historialRepositorio;

    public void aplicarFiltro(String filtro) {
        System.out.println("🛠 Aplicando filtro: " + filtro);
        try {
            File archivo = new File(RUTA_ORIGINAL);
            System.out.println("🔍 Leyendo original: " + archivo.getAbsolutePath());

            if (!archivo.exists()) {
                System.out.println("❌ No existe original.jpg");
                return;
            }

            BufferedImage original = ImageIO.read(archivo);
            if (original == null) {
                System.out.println("❌ No se pudo leer original.jpg");
                return;
            }

            BufferedImage procesada = aplicarFiltroElegido(original, filtro);
            if (procesada == null) {
                System.out.println("❌ El filtro no devolvió imagen procesada.");
                return;
            }

            // Guardar imagen temporal
            Path tempPath = Paths.get("C:/filtroll/imagenes/resultados/temporal_" + filtro + ".jpg");
            Files.createDirectories(tempPath.getParent());

            ImageIO.write(procesada, "jpg", tempPath.toFile());
            System.out.println("✅ Imagen temporal creada");

            // Reemplazar ultima.jpg
            Path vistaPrevia = Paths.get(RUTA_RESULTADO);
            if (Files.exists(vistaPrevia)) Files.delete(vistaPrevia);
            Files.copy(tempPath, vistaPrevia, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("📥 Copiado a ultima.jpg");

            // Guardar en historial
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreHistorial = "filtro_" + filtro + "_" + timestamp + ".jpg";

            Path carpeta = Paths.get("C:/filtroll/imagenes/usuarios/1/filtros/");
            Files.createDirectories(carpeta);

            Path rutaFinal = carpeta.resolve(nombreHistorial);
            Files.copy(tempPath, rutaFinal, StandardCopyOption.REPLACE_EXISTING);

            // Guardar en base de datos
            HistorialImagen registro = new HistorialImagen(
                    nombreHistorial,
                    filtro.toLowerCase(),
                    LocalDateTime.now(),
                    1L
            );
            historialRepositorio.save(registro);
            System.out.println("📚 Registro guardado: " + nombreHistorial);

        } catch (Exception e) {
            System.out.println("❌ Error al aplicar filtro:");
            e.printStackTrace();
        }
    }

    private BufferedImage aplicarFiltroElegido(BufferedImage imagen, String filtro) {
        return switch (filtro.toLowerCase()) {
            case "sepia" -> aplicarSepia(imagen);
            case "valencia" -> ajustar(imagen, 1.08f, 1.08f, 1.2f);
            case "hollywood" -> ajustar(imagen, 1.2f, 1.4f, 1.2f);
            case "ludwig" -> ajustar(imagen, 1.1f, 1.2f, 1.3f);
            case "clarendon" -> ajustar(imagen, 1.2f, 1.4f, 1.5f);
            case "invertir" -> invertirColores(imagen);
            case "altocontraste" -> ajustar(imagen, 1.0f, 1.8f, 1.0f);
            case "colorpop", "colorPop" -> destacarRojo(imagen);
            default -> imagen;
        };
    }

    private BufferedImage ajustar(BufferedImage imagen, float brillo, float contraste, float saturacion) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(imagen.getRGB(x, y));

                int r = aplicarContraste(color.getRed(), contraste);
                int g = aplicarContraste(color.getGreen(), contraste);
                int b = aplicarContraste(color.getBlue(), contraste);

                r = (int) (r * brillo);
                g = (int) (g * brillo);
                b = (int) (b * brillo);

                salida.setRGB(x, y, new Color(
                        Math.min(255, Math.max(0, r)),
                        Math.min(255, Math.max(0, g)),
                        Math.min(255, Math.max(0, b))
                ).getRGB());
            }
        }

        return salida;
    }

    private int aplicarContraste(int valor, float contraste) {
        float factor = (259 * (contraste + 255)) / (255 * (259 - contraste));
        return (int) (factor * (valor - 128) + 128);
    }

    private BufferedImage aplicarSepia(BufferedImage imagen) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(imagen.getRGB(x, y));

                int tr = (int) (0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue());
                int tg = (int) (0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue());
                int tb = (int) (0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue());

                salida.setRGB(x, y, new Color(
                        Math.min(255, tr),
                        Math.min(255, tg),
                        Math.min(255, tb)
                ).getRGB());
            }
        }

        return salida;
    }

    private BufferedImage invertirColores(BufferedImage imagen) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(imagen.getRGB(x, y));
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                salida.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return salida;
    }

    private BufferedImage destacarRojo(BufferedImage imagen) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(imagen.getRGB(x, y));
                if (color.getRed() > color.getGreen() + 30 && color.getRed() > color.getBlue() + 30) {
                    salida.setRGB(x, y, color.getRGB());
                } else {
                    int gris = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    salida.setRGB(x, y, new Color(gris, gris, gris).getRGB());
                }
            }
        }

        return salida;
    }
}
