package com.example.Proyecto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Proyecto.Model.Proyecto;
import com.example.Proyecto.Service.ProyectoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // ============================
    // ➕ Crear un nuevo proyecto
    // URL: POST http://localhost:8084/api/v1/proyectos
    // JSON ejemplo:
    /*
    {
      "nombreProyecto": "Sistema Web",
      "fechaProyecto": "2025-07-04",
      "description": "Plataforma para gestionar recursos"
    }
    */
    // ✔️ 201 Created si se guarda correctamente
    // ============================
    @Operation(summary = "Crear un nuevo proyecto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proyecto creado correctamente")
    })
    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto){
        Proyecto nuevo = proyectoService.creaProyecto(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ============================
    // 📋 Obtener todos los proyectos
    // URL: GET http://localhost:8084/api/v1/proyectos
    // ✔️ 200 OK con lista
    // ============================
    @Operation(summary = "Obtener todos los proyectos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de proyectos")
    })
    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodosLosProyectos(){
        return ResponseEntity.ok(proyectoService.obtenerTodosLProyectos());
    }

    // ============================
    // 🔍 Obtener proyecto por ID
    // URL: GET http://localhost:8084/api/v1/proyectos/{id}
    // ✔️ 200 OK si existe
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Obtener proyecto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proyecto encontrado"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyecto);
    }

    // ============================
    // 🔄 Actualizar un proyecto existente
    // URL: PUT http://localhost:8084/api/v1/proyectos/{id}
    // JSON igual al POST
    // ✔️ 200 OK si fue actualizado
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Actualizar un proyecto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proyecto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        Proyecto actualizado = proyectoService.actualizarpProyecto(id, proyecto);
        return ResponseEntity.ok(actualizado);
    }

    // ============================
    // ❌ Eliminar proyecto por ID
    // URL: DELETE http://localhost:8084/api/v1/proyectos/{id}
    // ✔️ 200 OK si fue eliminado
    // ❌ 404 Not Found si no existe
    // ============================
    @Operation(summary = "Eliminar proyecto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proyecto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProyecto(@PathVariable Long id){
        proyectoService.elininarProyecto(id);
        return ResponseEntity.ok("Proyecto con ID " + id + " eliminado correctamente");
    }
}