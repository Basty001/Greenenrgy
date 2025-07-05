package com.example.Usuarios12.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "roles") // Especifica el nombre de la tabla en la base de datos
@Data // Genera automáticamente getters, setters, toString, equals y hashCode
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos
public class Rol {
    
    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id; // ID único del rol

    @Column(nullable = false, unique = true) // Restricciones de columna
    private String nombre; // Nombre del rol (debe ser único)

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL) // Relación uno a muchos con Usuario
    @JsonIgnore // Evita recursión infinita al serializar a JSON
    private List<Usuario> users; // Lista de usuarios con este rol
}