package com.example.GestionSolicitudes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una solicitud de instalación")
public class SolicitudInstalacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la solicitud", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Descripción de la solicitud", example = "Instalación de sistema eléctrico")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Dirección donde se realizará la instalación", example = "Av. Siempre Viva 123")
    private String direccion;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la solicitud", example = "2025-07-05T10:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que creó la solicitud", example = "1")
    private Long usuarioId;
}