package com.example.Servicio.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Servicio.Model.Servicio;
import com.example.Servicio.Repository.ServicioRepository;
/**
 * Servicio que gestiona la lógica de negocio relacionada con la entidad Servicio.
 * Proporciona operaciones CRUD básicas utilizando el repositorio ServicioRepository.
 */
@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;
     /**
     * Obtiene la lista de todos los servicios almacenados.
     */
    public List<Servicio> listarTodos(){
        return servicioRepository.findAll();
    }
    /**
     * Guarda un nuevo servicio o actualiza uno existente.
     */

    public Servicio guardar(Servicio servicio){
        return servicioRepository.save(servicio);
    }
    /**
     * Busca un servicio por su ID.
     */

    public Servicio buscarPorId(Long id){
        return servicioRepository.findById(id).orElse(null);
    }
 /**
     * Elimina un servicio por su ID.
     * @param id ID del servicio a eliminar.
     */
    public void eliminar(Long id){
        servicioRepository.deleteById(id);
    }

}
