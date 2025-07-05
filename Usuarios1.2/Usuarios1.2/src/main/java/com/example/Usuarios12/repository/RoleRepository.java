package com.example.Usuarios12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Usuarios12.model.Rol;

/**
 * Repositorio para operaciones CRUD con la entidad Rol.
 * Extiende JpaRepository que proporciona métodos básicos:
 * - save(), findById(), findAll(), deleteById(), etc.
 */
@Repository
public interface RoleRepository extends JpaRepository<Rol, Long> {
    // No se requieren métodos adicionales para las operaciones básicas
    // JpaRepository ya proporciona todos los métodos necesarios
}
