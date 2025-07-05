package com.example.Reportes.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del reporte", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título del reporte", example = "Reporte mensual")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descripción del reporte", example = "Reporte detallado de ventas")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación del reporte", example = "2025-07-05T10:00:00")
    private LocalDateTime  fechaCreacion;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que creó el reporte", example = "1")
    private Long usuarioId;
}