package com.example.Privilegios.model;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "privilegio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un privilegio de usuario")
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del privilegio", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario asociado", example = "bastian_soto")
    private String usuario;

    @Column(nullable = false)
    @Schema(description = "Rol asignado al usuario", example = "ADMIN")
    private String rol;

    @Column(nullable = false)
    @Schema(description = "Permiso otorgado al usuario", example = "ACCESO_TOTAL")
    private String permiso;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID del usuario que creó el ticket", example = "10")
    private Long usuarioId;
}