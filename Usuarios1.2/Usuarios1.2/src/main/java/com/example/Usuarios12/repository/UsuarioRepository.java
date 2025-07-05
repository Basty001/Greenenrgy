package com.example.Usuarios12.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Usuarios12.model.Usuario;

/**
 * Repositorio para operaciones CRUD con la entidad Usuario.
 * Incluye un método personalizado para buscar por nombre de usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su nombre de usuario único.
     * @param username Nombre de usuario a buscar
     * @return Optional que contiene el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);
}
