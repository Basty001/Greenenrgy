package com.example.GestionSolicitudes.controller;

import com.example.GestionSolicitudes.model.SolicitudInstalacion;
import com.example.GestionSolicitudes.service.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SolicitudControllerTest {

    @Mock
    private SolicitudService solicitudService;

    @InjectMocks
    private SolicitudController solicitudController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerSolicitudes_ConResultados() {
        List<SolicitudInstalacion> lista = List.of(new SolicitudInstalacion());
        when(solicitudService.getSolicitudes()).thenReturn(lista);

        ResponseEntity<List<SolicitudInstalacion>> respuesta = solicitudController.obtenerSolicitudes();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertFalse(respuesta.getBody().isEmpty());
    }

    @Test
    public void testObtenerSolicitudes_SinResultados() {
        when(solicitudService.getSolicitudes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<SolicitudInstalacion>> respuesta = solicitudController.obtenerSolicitudes();
        assertEquals(204, respuesta.getStatusCodeValue());
        assertNull(respuesta.getBody());
    }

    @Test
    public void testCrearSolicitud_Exitosa() {
        SolicitudInstalacion solicitud = new SolicitudInstalacion(null, "desc", "dir", null, 1L);
        SolicitudInstalacion guardada = new SolicitudInstalacion(1L, "desc", "dir", LocalDateTime.now(), 1L);

        when(solicitudService.saveSolicitud(solicitud)).thenReturn(guardada);

        ResponseEntity<?> respuesta = solicitudController.crearSolicitud(solicitud);
        assertEquals(201, respuesta.getStatusCodeValue());
        assertEquals(guardada, respuesta.getBody());
    }

    @Test
    public void testCrearSolicitud_UsuarioNoExiste() {
        SolicitudInstalacion solicitud = new SolicitudInstalacion(null, "desc", "dir", null, 999L);

        when(solicitudService.saveSolicitud(solicitud)).thenThrow(new RuntimeException("Usuario no encontrado"));

        ResponseEntity<?> respuesta = solicitudController.crearSolicitud(solicitud);
        assertEquals(404, respuesta.getStatusCodeValue());
        assertEquals("Usuario no encontrado", respuesta.getBody());
    }

    @Test
    public void testObtenerPorUsuario_ConSolicitudes() {
        List<SolicitudInstalacion> lista = List.of(new SolicitudInstalacion());
        when(solicitudService.obtenerPorUsuarioId(1L)).thenReturn(lista);

        ResponseEntity<List<SolicitudInstalacion>> respuesta = solicitudController.obtenerPorUsuario(1L);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertFalse(respuesta.getBody().isEmpty());
    }

    @Test
    public void testObtenerPorUsuario_SinSolicitudes() {
        when(solicitudService.obtenerPorUsuarioId(2L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<SolicitudInstalacion>> respuesta = solicitudController.obtenerPorUsuario(2L);
        assertEquals(204, respuesta.getStatusCodeValue());
        assertNull(respuesta.getBody());
    }
}
