package com.example.Soporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Soporte.model.TicketSoporte;

/**
 * Repositorio para operaciones CRUD con la entidad TicketSoporte.
 * Extiende JpaRepository para operaciones básicas.
 */
@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {
    
    /**
     * Busca tickets por ID de usuario
     * @param usuarioId ID del usuario
     * @return Lista de tickets del usuario
     */
    List<TicketSoporte> findByUsuarioId(Long usuarioId);

    /**
     * Busca tickets por estado (ignorando mayúsculas/minúsculas)
     * @param estado Estado del ticket (ABIERTO, EN PROCESO, CERRADO)
     * @return Lista de tickets con el estado especificado
     */
    List<TicketSoporte> findByEstadoIgnoreCase(String estado);

    /**
     * Busca tickets cuyo título contenga la palabra especificada (ignorando mayúsculas/minúsculas)
     * @param palabra Palabra a buscar en el título
     * @return Lista de tickets que coinciden con la búsqueda
     */
    List<TicketSoporte> findByTituloContainingIgnoreCase(String palabra);
}