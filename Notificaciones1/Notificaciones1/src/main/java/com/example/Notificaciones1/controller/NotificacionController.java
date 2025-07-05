package com.example.Notificaciones1.controller;

import com.example.Notificaciones1.model.Notificacion;
import com.example.Notificaciones1.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // ============================
    // 📃 Obtener todas las notificaciones (solo ADMIN)
    // URL: GET http://localhost:8084/api/notificaciones
    // ✔️ 200 OK si hay resultados
    // ❌ 403 Forbidden si no es admin
    // ============================
    @Operation(summary = "Obtener todas las notificaciones (ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de notificaciones"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - solo ADMIN")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        return ResponseEntity.ok(notificacionService.getAllNotificaciones());
    }

    // ============================
    // 📄 Crear una nueva notificacion (usa usuario autenticado)
    // URL: POST http://localhost:8084/api/notificaciones
    // Basic Auth: user: user / pass: password
    // JSON ejemplo:
    /*
    {
      "mensaje": "Tienes una nueva tarea asignada"
    }
    */
    // ✔️ 201 Created si se guarda bien
    // ============================
    @Operation(summary = "Crear una nueva notificacion para el usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificacion creada correctamente")
    })
    @PostMapping
    public ResponseEntity<Notificacion> saveNotificacion(@RequestBody Notificacion notificacion) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long usuarioId = obtenerUsuarioIdDesdeUsername(username); // Debes implementar esta lógica
        notificacion.setUsuarioId(usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.saveNotificacion(notificacion));
    }

    // ============================
    // 🔍 Obtener notificaciones por usuario
    // URL: GET http://localhost:8084/api/notificaciones/usuario/1
    // ✔️ 200 OK con lista
    // ❌ 403 si no es su propio ID
    // ============================
    @Operation(summary = "Obtener notificaciones por ID de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificaciones del usuario"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("#usuarioId == authentication.principal.id")
    public ResponseEntity<List<Notificacion>> getNotificacionesByUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getNotificacionesByUsuarioId(usuarioId));
    }

    // ============================
    // 🔍 Obtener notificaciones NO leídas por usuario
    // URL: GET http://localhost:8084/api/notificaciones/usuario/1/no-leidas
    // ✔️ 200 OK con lista filtrada
    // ❌ 403 si no es su propio ID
    // ============================
    @Operation(summary = "Obtener notificaciones NO leídas de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificaciones no leídas"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    @PreAuthorize("#usuarioId == authentication.principal.id")
    public ResponseEntity<List<Notificacion>> getNotificacionesNoLeidasByUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getNotificacionesNoLeidasByUsuarioId(usuarioId));
    }

    // ============================
    // ✅ Marcar una notificacion como leída (usuario autenticado)
    // URL: PUT http://localhost:8084/api/notificaciones/5/marcar-leida
    // ✔️ 200 OK si es exitosa
    // ❌ 403 si no es suya
    // ❌ 404 si no existe
    // ============================
    @Operation(summary = "Marcar una notificacion como leída")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion marcada como leída"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    })
    @PutMapping("/{id}/marcar-leida")
    public ResponseEntity<Notificacion> marcarNotificacionComoLeida(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long usuarioId = obtenerUsuarioIdDesdeUsername(username); // Debes implementar esto
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id, usuarioId));
    }

    // Simulacion de metodo auxiliar (deberías conectarte al microservicio de usuarios)
    private Long obtenerUsuarioIdDesdeUsername(String username) {
        // TODO: usar UsuarioClient para obtener el id del usuario a partir del username
        if (username.equals("user")) return 1L;
        if (username.equals("victor")) return 2L;
        return 0L;
    }
}
