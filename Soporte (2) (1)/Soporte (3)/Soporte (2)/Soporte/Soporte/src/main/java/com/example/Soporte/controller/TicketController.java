package com.example.Soporte.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Soporte.model.TicketSoporte;
import com.example.Soporte.service.SoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/v1/soporte")
public class TicketController {

    @Autowired
    private SoporteService soporteService;

    // ============================
    //  Obtener todos los tickets
    // URL: GET http://localhost:8081/api/v1/soporte
    //  Devuelve 200 OK con la lista o 204 si está vacía
    // ============================
    @Operation(summary = "Obtener todos los tickets de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tickets encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay tickets disponibles")
    })
    @GetMapping
    public ResponseEntity<List<TicketSoporte>> obtenerTicket() {
        List<TicketSoporte> lista = soporteService.getTicket();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // ============================
    //  Crear un nuevo ticket
    // URL: POST http://localhost:8081/api/v1/soporte
    // JSON para Postman:
    /*
    {
      "titulo": "Error al iniciar sesión",
      "descripcion": "No puedo ingresar al sistema desde ayer",
      "estado": "ABIERTO",
      "fechaCreacion": "2025-07-04T20:00:00",
      "usuarioId": 1
    }
    */
    //  Devuelve 201 si se crea bien
    //  404 si no existe el usuario
    // ============================
    @Operation(summary = "Crear un nuevo ticket de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket creado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud mal formada")
    })
    @PostMapping
    public ResponseEntity<?> crearTicket(@RequestBody TicketSoporte nuevo) {
        try {
            TicketSoporte ticket = soporteService.saveTicket(nuevo);
            return ResponseEntity.status(201).body(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ============================
    //  Buscar tickets por ID de usuario
    // URL: GET http://localhost:8081/api/v1/soporte/usuario/1
    // Devuelve 200 si hay tickets, 204 si no hay
    // ============================
    @Operation(summary = "Obtener tickets por ID de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados"),
        @ApiResponse(responseCode = "204", description = "El usuario no tiene tickets")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TicketSoporte>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<TicketSoporte> lista = soporteService.obtenerPorUsuarioId(usuarioId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // ============================
    //  Buscar tickets por estado (ABIERTO, EN PROCESO, CERRADO)
    // URL: GET http://localhost:8081/api/v1/soporte/estado/ABIERTO
    //  Devuelve 200 o 204 si no hay
    // ============================
    @Operation(summary = "Obtener tickets por estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados por estado"),
        @ApiResponse(responseCode = "204", description = "No hay tickets con ese estado")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TicketSoporte>> obtenerPorEstado(@PathVariable String estado) {
        List<TicketSoporte> lista = soporteService.obtenerPorEstado(estado);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // ============================
    //  Buscar tickets por palabra en el título
    // URL: GET http://localhost:8081/api/v1/soporte/buscar?titulo=login
    //  Devuelve 200 o 204 si no hay coincidencias
    // ============================
    @Operation(summary = "Buscar tickets por palabra clave en el título")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets encontrados por coincidencia en el título"),
        @ApiResponse(responseCode = "204", description = "No hay tickets que contengan esa palabra")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<TicketSoporte>> buscarPorTitulo(@RequestParam String titulo) {
        List<TicketSoporte> lista = soporteService.buscarPorTitulo(titulo);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
}