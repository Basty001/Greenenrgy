package com.example.GestionSolicitudes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.GestionSolicitudes.model.SolicitudInstalacion;
import com.example.GestionSolicitudes.service.SolicitudService;

import org.springframework.web.bind.annotation.RequestBody;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;




@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Operation(summary = "Obtener todas las solicitudes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes encontradas"),
            @ApiResponse(responseCode = "204", description = "No hay solicitudes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<SolicitudInstalacion>> obtenerSolicitudes() {
        List<SolicitudInstalacion> lista = solicitudService.getSolicitudes();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Crear una nueva solicitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud mal formada")
    })
    @PostMapping
public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudInstalacion nueva) {
    try {
        SolicitudInstalacion solicitud = solicitudService.saveSolicitud(nueva);
        return ResponseEntity.status(201).body(solicitud);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}

    @Operation(summary = "Obtener solicitudes por ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes encontradas"),
            @ApiResponse(responseCode = "204", description = "El usuario no tiene solicitudes")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitudInstalacion>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<SolicitudInstalacion> lista = solicitudService.obtenerPorUsuarioId(usuarioId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
}