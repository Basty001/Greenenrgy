package com.example.Proyecto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Proyecto.Model.Proyecto;
import com.example.Proyecto.Repository.ProyectoRepository;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Crear un nuevo proyecto
    public Proyecto creaProyecto(Proyecto proyecto){
        return proyectoRepository.save(proyecto);
    }

    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLProyectos(){
        return proyectoRepository.findAll();
    }

    // Obtener proyecto por ID 
    public Proyecto obtenerProyectoPorId(Long id){
        return proyectoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con ID " + id + " no encontrado"));
    }

    // Actualizar proyecto 
    public Proyecto actualizarpProyecto(Long id, Proyecto proyectoActualizado){
        if (!proyectoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con ID " + id + " no encontrado");
        }
        proyectoActualizado.setId(id);
        return proyectoRepository.save(proyectoActualizado);
    }

    // Eliminar proyecto 
    public void elininarProyecto(Long id){
        if (!proyectoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto con ID " + id + " no encontrado");
        }
        proyectoRepository.deleteById(id);
    }
}