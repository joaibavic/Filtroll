package com.tfc.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_imagenes")
public class HistorialImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String filtroAplicado;
    private LocalDateTime fechaAplicacion;
    private Long usuarioId;

    public HistorialImagen() {}

    public HistorialImagen(String nombreArchivo, String filtroAplicado, LocalDateTime fechaAplicacion, Long usuarioId) {
        this.nombreArchivo = nombreArchivo;
        this.filtroAplicado = filtroAplicado;
        this.fechaAplicacion = fechaAplicacion;
        this.usuarioId = usuarioId;
    }

    // ✅ GETTERS NECESARIOS
    public Long getId() {
        return id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getFiltroAplicado() {
        return filtroAplicado;
    }

    public LocalDateTime getFechaAplicacion() {
        return fechaAplicacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    // (Setters si los necesitas también...)
}
