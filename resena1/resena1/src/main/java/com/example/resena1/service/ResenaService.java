package com.example.resena1.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.resena1.model.Resena;
import com.example.resena1.repository.ResenaRepository;
import com.example.resena1.webclient.UsuarioClient;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;
    
    @Autowired
    private UsuarioClient usuarioClient;

    // Crear una nueva reseña
    public Resena crearResena(Resena resena){
        if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La calificación debe estar entre 1 y 5");
        }
        resena.setFechaCreacion(java.time.LocalDateTime.now());
        resena.setBaneada(false);
        return resenaRepository.save(resena);
    }

    // Obtener todas las reseñas
    public List<Resena> obtenerTodasLasResenas(){
        return resenaRepository.findAll();
    }

    // Obtener reseña por ID
    public Resena obtenerResenaPorId(Long id){
        return resenaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Reseña con ID " + id + " no encontrada"));
    }

    // Actualizar reseña
    public Resena actualizarResena(Long id, Resena resenaActualizada){
        if (!resenaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Reseña con ID " + id + " no encontrada");
        }
        resenaActualizada.setId(id);
        return resenaRepository.save(resenaActualizada);
    }

    // Banear reseña (solo admin)
    public Resena banearResena(Long id, Long adminId){
        if (!usuarioClient.esAdministrador(adminId)) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Solo los administradores pueden banear reseñas");
        }
        
        Resena resena = resenaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Reseña con ID " + id + " no encontrada"));
        
        resena.setBaneada(true);
        return resenaRepository.save(resena);
    }

    // Eliminar reseña
    public void eliminarResena(Long id){
        if (!resenaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Reseña con ID " + id + " no encontrada");
        }
        resenaRepository.deleteById(id);
    }
}