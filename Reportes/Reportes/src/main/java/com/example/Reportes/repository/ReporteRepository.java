package com.example.Reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reportes.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findByUsuarioId(Long usuarioId);

    List<Reporte> findByTituloContainingIgnoreCase(String palabra);
}