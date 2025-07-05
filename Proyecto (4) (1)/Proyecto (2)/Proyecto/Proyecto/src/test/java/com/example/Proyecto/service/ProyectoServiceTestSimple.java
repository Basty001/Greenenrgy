package com.example.Proyecto.service;

import com.example.Proyecto.Model.Proyecto;
import com.example.Proyecto.Repository.ProyectoRepository;
import com.example.Proyecto.Service.ProyectoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProyectoServiceTestSimple {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private Proyecto proyecto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proyecto = new Proyecto(1L, "Proyecto Test", LocalDate.of(2025, 6, 24), "Descripción simple");
    }

    // Test crear proyecto (save)
    @Test
    void creaProyecto_deberiaLlamarSave() {
        proyectoService.creaProyecto(proyecto);
        verify(proyectoRepository, times(1)).save(proyecto);
    }

    // Test obtener todos los proyectos
    @Test
    void obtenerTodosLProyectos_deberiaLlamarFindAll() {
        proyectoService.obtenerTodosLProyectos();
        verify(proyectoRepository, times(1)).findAll();
    }

    // Test obtener proyecto por ID
    @Test
    void obtenerProyectoPorId_deberiaLlamarFindById() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        proyectoService.obtenerProyectoPorId(1L);
        verify(proyectoRepository, times(1)).findById(1L);
    }

    // Test actualizar proyecto si existe
    @Test
    void actualizarProyecto_deberiaLlamarSaveSiExiste() {
        when(proyectoRepository.existsById(anyLong())).thenReturn(true);
        proyectoService.actualizarpProyecto(1L, proyecto);
        verify(proyectoRepository, times(1)).existsById(1L);
        verify(proyectoRepository, times(1)).save(proyecto);
    }

    // Test actualizar proyecto si NO existe
    public Proyecto actualizarpProyecto(Long id, Proyecto proyectoActualizado) {
    if (!proyectoRepository.existsById(id)) {
        return null;
    }
    proyectoActualizado.setId(id);
    return proyectoRepository.save(proyectoActualizado);
}



    // Test eliminar proyecto si existe
    @Test
    void eliminarProyecto_deberiaLlamarDeleteByIdSiExiste() {
        when(proyectoRepository.existsById(anyLong())).thenReturn(true);
        proyectoService.elininarProyecto(1L);
        verify(proyectoRepository, times(1)).existsById(1L);
        verify(proyectoRepository, times(1)).deleteById(1L);
    }

    // Test eliminar proyecto si NO existe
   @Test
    void eliminarProyecto_noDeberiaEliminarSiNoExiste() {
    when(proyectoRepository.existsById(1L)).thenReturn(false);

    // Verifica que al llamar se lance la excepción 404
    assertThrows(ResponseStatusException.class, () -> proyectoService.elininarProyecto(1L));

    verify(proyectoRepository, times(1)).existsById(1L);
    verify(proyectoRepository, never()).deleteById(anyLong());
}
}
