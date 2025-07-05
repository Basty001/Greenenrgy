package com.example.resena1.controller;

import com.example.resena1.model.Resena;
import com.example.resena1.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResenaControllerTest {

    @Mock
    private ResenaService resenaService;

    @InjectMocks
    private ResenaController resenaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearResena_DebeRetornarResenaCreada() {
        Resena resena = new Resena();
        resena.setId(1L);
        when(resenaService.crearResena(any(Resena.class))).thenReturn(resena);

        ResponseEntity<Resena> response = resenaController.crearResena(resena);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void obtenerTodasLasResenas_DebeRetornarLista() {
        Resena resena1 = new Resena();
        Resena resena2 = new Resena();
        List<Resena> resenas = Arrays.asList(resena1, resena2);
        when(resenaService.obtenerTodasLasResenas()).thenReturn(resenas);

        ResponseEntity<List<Resena>> response = resenaController.obtenerTodasLasResenas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void obtenerResenaPorId_DebeRetornarResena() {
        Resena resena = new Resena();
        resena.setId(1L);
        when(resenaService.obtenerResenaPorId(1L)).thenReturn(resena);

        ResponseEntity<Resena> response = resenaController.obtenerResenaPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void actualizarResena_DebeRetornarResenaActualizada() {
        Resena resena = new Resena();
        resena.setId(1L);
        when(resenaService.actualizarResena(eq(1L), any(Resena.class))).thenReturn(resena);

        ResponseEntity<Resena> response = resenaController.actualizarResena(1L, resena);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void banearResena_DebeRetornarResenaBaneada() {
        Resena resena = new Resena();
        resena.setId(1L);
        resena.setBaneada(true);
        when(resenaService.banearResena(1L, 1L)).thenReturn(resena);

        ResponseEntity<Resena> response = resenaController.banearResena(1L, 1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isBaneada());
    }

    @Test
    void eliminarResena_DebeRetornarMensajeExitoso() {
        doNothing().when(resenaService).eliminarResena(1L);

        ResponseEntity<String> response = resenaController.eliminarResena(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reseña con Id 1 eliminada correctamente", response.getBody());
    }
}