package com.example.Reportes.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

import java.util.List;






@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Obtener todos los reportes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay reportes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerReportes() {
        List<Reporte> lista = reporteService.getReportes();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Crear un nuevo reporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud mal formada")
    })
    @PostMapping
    public ResponseEntity<?> crearReporte(@RequestBody Reporte nuevo) {
        try {
            Reporte reporte = reporteService.saveReporte(nuevo);
            return ResponseEntity.status(201).body(reporte);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener reportes por ID de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados"),
            @ApiResponse(responseCode = "204", description = "El usuario no tiene reportes")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reporte>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<Reporte> lista = reporteService.obtenerPorUsuarioId(usuarioId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reportes por palabra clave en el título")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados por coincidencia en el título"),
            @ApiResponse(responseCode = "204", description = "No hay reportes que contengan esa palabra")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Reporte>> buscarPorTitulo(@RequestParam String titulo) {
        List<Reporte> lista = reporteService.buscarPorTitulo(titulo);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
}