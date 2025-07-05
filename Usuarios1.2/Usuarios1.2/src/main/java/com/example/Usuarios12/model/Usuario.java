package com.example.Usuarios12.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity // Indica que esta clase es una entidad JPA
@Table(name = "usuarios") // Especifica el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos
public class Usuario {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id; // ID único del usuario

    @Column(nullable = false, unique = true, length = 50) // Restricciones de columna
    private String username; // Nombre de usuario (único, máximo 50 caracteres)

    @Column(nullable = false) // La contraseña no puede ser nula
    @JsonIgnore // No se incluirá en las respuestas JSON por seguridad
    private String password; // Contraseña encriptada

    @ManyToOne // Relación muchos a uno con Rol
    @JoinColumn(name = "rol_id") // Nombre de la columna de relación
    @JsonIgnoreProperties("users") // Evita recursión infinita al serializar
    private Rol rol; // Rol asignado al usuario
}

