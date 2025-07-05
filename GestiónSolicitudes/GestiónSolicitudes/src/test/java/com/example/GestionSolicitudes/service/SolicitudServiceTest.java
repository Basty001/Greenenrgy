package com.example.GestionSolicitudes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.GestionSolicitudes.model.SolicitudInstalacion;
import com.example.GestionSolicitudes.repository.SolicitudRepository;
import com.example.GestionSolicitudes.webservice.UsuarioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private SolicitudService solicitudService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSolicitudes() {
        List<SolicitudInstalacion> solicitudes = List.of(new SolicitudInstalacion());
        when(solicitudRepository.findAll()).thenReturn(solicitudes);

        List<SolicitudInstalacion> resultado = solicitudService.getSolicitudes();
        assertEquals(1, resultado.size());
    }

    @Test
    public void testSaveSolicitud_UsuarioExistente() {
        SolicitudInstalacion solicitud = new SolicitudInstalacion(null, "descripcion", "direccion", null, 1L);

        when(usuarioClient.getUsuarioPorId(1L)).thenReturn(Map.of("id", 1L));
        when(solicitudRepository.save(any(SolicitudInstalacion.class))).thenAnswer(i -> i.getArgument(0));

        SolicitudInstalacion resultado = solicitudService.saveSolicitud(solicitud);
        assertNotNull(resultado.getFechaCreacion());
    }

    @Test
    public void testSaveSolicitud_UsuarioNoExiste() {
        SolicitudInstalacion solicitud = new SolicitudInstalacion(null, "descripcion", "direccion", null, 1L);

        when(usuarioClient.getUsuarioPorId(1L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            solicitudService.saveSolicitud(solicitud);
        });
        assertEquals("Usuario no encontrado. No se puede guardar la solicitud", ex.getMessage());
    }

    @Test
    public void testObtenerPorUsuarioId() {
        List<SolicitudInstalacion> lista = List.of(new SolicitudInstalacion());
        when(solicitudRepository.findByUsuarioId(1L)).thenReturn(lista);

        List<SolicitudInstalacion> resultado = solicitudService.obtenerPorUsuarioId(1L);
        assertEquals(1, resultado.size());
    }
}

