package com.tfc.repositorio;

import com.tfc.modelo.HistorialImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialRepositorio extends JpaRepository<HistorialImagen, Long> {
}
