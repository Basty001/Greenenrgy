package com.example.resena1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resena1.model.Resena;
import com.example.resena1.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // ============================
    // 📌 Crear una nueva reseña
    // URL: POST http://localhost:8082/api/v1/resenas
    // JSON para Postman:
    /*
    {
      "idUsuario": 1,
      "calificacion": 5,
      "comentario": "Excelente servicio",
      "fechaCreacion": "2025-07-04T20:00:00",
      "baneada": false
    }
    */
    // ✔️ 200 OK si se crea bien
    // ❌ 400 Bad Request si calificacion no está entre 1 y 5
    // ============================
    @Operation(summary = "Crear una nueva reseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Calificación fuera del rango permitido (1 a 5)")
    })
    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena){
        Resena nueva = resenaService.crearResena(resena);
        return ResponseEntity.ok(nueva);
    }

    // ============================
    // 📃 Obtener todas las reseñas
    // URL: GET http://localhost:8082/api/v1/resenas
    // ✔️ 200 OK siempre (puede retornar lista vacía)
    // ============================
    @Operation(summary = "Obtener todas las reseñas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reseñas")
    })
    @GetMapping
    public ResponseEntity<List<Resena>> obtenerTodasLasResenas(){
        return ResponseEntity.ok(resenaService.obtenerTodasLasResenas());
    }

    // ============================
    // 🔍 Obtener reseña por ID
    // URL: GET http://localhost:8082/api/v1/resenas/1
    // ✔️ 200 OK si existe
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Obtener una reseña por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
        Resena resena = resenaService.obtenerResenaPorId(id);
        return ResponseEntity.ok(resena);
    }

    // ============================
    // ✏️ Actualizar reseña
    // URL: PUT http://localhost:8082/api/v1/resenas/1
    // ✔️ 200 OK si se actualiza
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Actualizar una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resena) {
        Resena actualizada = resenaService.actualizarResena(id, resena);
        return ResponseEntity.ok(actualizada);
    }

    // ============================
    // ⛔ Banear reseña (solo admin)
    // URL: PUT http://localhost:8082/api/v1/resenas/banear/1/10
    // ✔️ 200 OK si se banea
    // ❌ 403 Forbidden si no es admin
    // ❌ 404 Not Found si no existe la reseña
    // ============================
    @Operation(summary = "Banear una reseña (solo administrador)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña baneada correctamente"),
        @ApiResponse(responseCode = "403", description = "Solo los administradores pueden banear reseñas"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/banear/{id}/{adminId}")
    public ResponseEntity<Resena> banearResena(@PathVariable Long id, @PathVariable Long adminId) {
        Resena baneada = resenaService.banearResena(id, adminId);
        return ResponseEntity.ok(baneada);
    }

    // ============================
    // ❌ Eliminar reseña
    // URL: DELETE http://localhost:8082/api/v1/resenas/1
    // ✔️ 200 OK si se elimina
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Eliminar una reseña por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarResena(@PathVariable Long id){
        resenaService.eliminarResena(id);
        return ResponseEntity.ok("Reseña con Id " + id + " eliminada correctamente");
    }
}
