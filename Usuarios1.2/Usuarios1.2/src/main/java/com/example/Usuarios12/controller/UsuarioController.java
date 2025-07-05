package com.example.Usuarios12.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.service.UsuarioService;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controlador REST para la gestión de usuarios.
 * Expone endpoints para operaciones CRUD y autenticación de usuarios.
 */
@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuario API", description = "Operaciones para gestión de usuarios y roles")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return ResponseEntity con lista de usuarios o estado 204 si no hay contenido
     */
    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista completa de todos los usuarios registrados en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No hay usuarios registrados"
        )
    })
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> users = usuarioService.obtenerUsuarios();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    /**
     * Obtiene un usuario específico por su ID único.
     * @param id ID del usuario a buscar
     * @return ResponseEntity con el usuario encontrado o mensaje de error
     */
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Busca un usuario por su ID único."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = Usuario.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(schema = @Schema(example = "Usuario no encontrado"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(example = "Error interno: mensaje_de_error"))
        )
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar", example = "1", required = true)
            @PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * @param datos Mapa con los datos del usuario (username, password, rolId)
     * @return ResponseEntity con el usuario creado o mensaje de error
     */
    @Operation(
        summary = "Crear nuevo usuario",
        description = "Registra un nuevo usuario en el sistema. La contraseña se encripta automáticamente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de usuario inválidos",
            content = @Content(schema = @Schema(example = "Mensaje de error específico"))
        )
    })
    @PostMapping("/users")
    public ResponseEntity<?> crearUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos requeridos para crear un usuario",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"username\":\"nuevoUsuario\",\"password\":\"clave123\",\"rolId\":1}")
                )
            )
            @RequestBody Map<String, Object> datos) {
        try {
            String username = (String) datos.get("username");
            String password = (String) datos.get("password");
            Long rolId = Long.valueOf(datos.get("rolId").toString());
            Usuario nuevo = usuarioService.crearUsuario(username, password, rolId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param id ID del usuario a actualizar
     * @param datos Mapa con los datos a actualizar (username, password, rolId)
     * @return ResponseEntity con el usuario actualizado o mensaje de error
     */
    @Operation(
        summary = "Actualizar usuario existente",
        description = "Actualiza parcial o totalmente los datos de un usuario."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(schema = @Schema(example = "Mensaje de error específico"))
        )
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos a actualizar (todos opcionales)",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"username\":\"nuevoNombre\",\"password\":\"nuevaClave\",\"rolId\":2}")
                )
            )
            @RequestBody Map<String, Object> datos) {
        try {
            String username = (String) datos.get("username");
            String password = (String) datos.get("password");
            Long rolId = datos.get("rolId") != null ? Long.valueOf(datos.get("rolId").toString()) : null;
            Usuario actualizado = usuarioService.actualizarUsuario(id, username, password, rolId);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Elimina un usuario del sistema por su ID.
     * @param id ID del usuario a eliminar
     * @return ResponseEntity con estado 204 si se eliminó correctamente
     */
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina permanentemente un usuario del sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Usuario eliminado exitosamente"
        )
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1", required = true)
            @PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Autentica un usuario con credenciales.
     * @param datos Mapa con las credenciales (username, password)
     * @return ResponseEntity con mensaje de éxito o error
     */
    @Operation(
        summary = "Autenticar usuario",
        description = "Valida las credenciales de un usuario para permitir acceso al sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @Content(schema = @Schema(example = "Login exitoso"))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Faltan campos obligatorios",
            content = @Content(schema = @Schema(example = "Faltan campos 'username' o 'password'"))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = @Content(schema = @Schema(example = "Credenciales inválidas"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(example = "Error interno: mensaje_de_error"))
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales de autenticación",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{\"username\":\"admin\",\"password\":\"12345\"}")
                )
            )
            @RequestBody Map<String, String> datos) {
        try {
            String username = datos.get("username");
            String password = datos.get("password");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Faltan campos 'username' o 'password'");
            }

            boolean valido = usuarioService.validarCredenciales(username, password);
            return valido
                ? ResponseEntity.ok("Login exitoso")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno: " + e.getMessage());
        }
    }
}
