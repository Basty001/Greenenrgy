package com.example.Privilegios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Privilegios.model.Privilegio;
import com.example.Privilegios.service.PrivilegioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/privilegios")
@Tag(name = "Privilegios", description = "API para gestionar privilegios de usuarios")
public class PrivilegioController {

    @Autowired
    private PrivilegioService privilegioService;

    @Operation(summary = "Crear un nuevo privilegio",
               description = "Crea un privilegio con los datos proporcionados y lo guarda en la base de datos",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Privilegio creado correctamente",
                                content = @Content(schema = @Schema(implementation = Privilegio.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
               })
    @PostMapping
    public ResponseEntity<Privilegio> crearPrivilegio(@RequestBody Privilegio privilegio) {
        Privilegio creado = privilegioService.crearPrivilegio(privilegio);
        return ResponseEntity.ok(creado);
    }

    @Operation(summary = "Obtener todos los privilegios",
               description = "Devuelve una lista con todos los privilegios existentes",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de privilegios obtenida",
                                content = @Content(schema = @Schema(implementation = Privilegio.class, type = "array")))
               })
    @GetMapping
    public ResponseEntity<List<Privilegio>> obtenerTodos() {
        List<Privilegio> lista = privilegioService.obtenerTodos();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener privilegio por ID",
               description = "Devuelve el privilegio que coincide con el ID proporcionado",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Privilegio encontrado",
                                content = @Content(schema = @Schema(implementation = Privilegio.class))),
                   @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
               })
    @GetMapping("/{id}")
    public ResponseEntity<Privilegio> obtenerPorId(@PathVariable Long id) {
        Privilegio privilegio = privilegioService.obtenerPorId(id);
        if (privilegio != null) {
            return ResponseEntity.ok(privilegio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar privilegio por ID",
               description = "Elimina el privilegio que coincide con el ID proporcionado",
               responses = {
                   @ApiResponse(responseCode = "204", description = "Privilegio eliminado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Privilegio no encontrado")
               })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrivilegio(@PathVariable Long id) {
        Privilegio existente = privilegioService.obtenerPorId(id);
        if (existente != null) {
            privilegioService.eliminarPrivilegio(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}