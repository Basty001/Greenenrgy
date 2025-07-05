package com.example.Proyecto.controller;

import com.example.Proyecto.Controller.ProyectoController;
import com.example.Proyecto.Model.Proyecto;
import com.example.Proyecto.Service.ProyectoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProyectoControllerTest {

    @Mock
    private ProyectoService proyectoService;

    @InjectMocks
    private ProyectoController proyectoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearProyecto_DebeRetornarProyectoCreado() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        
        when(proyectoService.creaProyecto(any(Proyecto.class))).thenReturn(proyecto);

        ResponseEntity<Proyecto> response = proyectoController.crearProyecto(proyecto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void obtenerTodosLosProyectos_DebeRetornarLista() {
        Proyecto proyecto1 = new Proyecto();
        Proyecto proyecto2 = new Proyecto();
        List<Proyecto> proyectos = Arrays.asList(proyecto1, proyecto2);
        
        when(proyectoService.obtenerTodosLProyectos()).thenReturn(proyectos);

        ResponseEntity<List<Proyecto>> response = proyectoController.obtenerTodosLosProyectos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void obtenerProyectoPorId_DebeRetornarProyecto() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        
        when(proyectoService.obtenerProyectoPorId(1L)).thenReturn(proyecto);

        ResponseEntity<Proyecto> response = proyectoController.obtenerProyectoPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void actualizarProyecto_DebeRetornarProyectoActualizado() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        
        when(proyectoService.actualizarpProyecto(eq(1L), any(Proyecto.class))).thenReturn(proyecto);

        ResponseEntity<Proyecto> response = proyectoController.actualizarProyecto(1L, proyecto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void eliminarProyecto_DebeRetornarMensajeExitoso() {
        doNothing().when(proyectoService).elininarProyecto(1L);

        ResponseEntity<String> response = proyectoController.eliminarProyecto(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("proyecto con Id 1 eliminar correctamente", response.getBody());
    }
}