package com.example.Soporte.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un ticket de soporte")
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ticket", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título del ticket", example = "Problema con la conexión")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada del problema", example = "No puedo acceder al sistema desde ayer en la tarde")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Estado del ticket: ABIERTO, EN PROCESO o CERRADO", example = "ABIERTO")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación del ticket", example = "2025-07-04T18:45:00")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que creó el ticket", example = "10")
    private Long usuarioId;
}