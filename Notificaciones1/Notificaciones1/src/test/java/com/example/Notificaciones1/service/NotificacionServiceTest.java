package com.example.Notificaciones1.service;

import com.example.Notificaciones1.model.Notificacion;
import com.example.Notificaciones1.repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService service;

    @Test
    void testSaveNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje("Test");
        when(repository.save(any(Notificacion.class))).thenReturn(notificacion);

        Notificacion saved = service.saveNotificacion(notificacion);
        assertEquals("Test", saved.getMensaje());
    }

    @Test
    void testGetAllNotificaciones() {
        Notificacion n1 = new Notificacion();
        Notificacion n2 = new Notificacion();
        when(repository.findAll()).thenReturn(Arrays.asList(n1, n2));

        List<Notificacion> result = service.getAllNotificaciones();
        assertEquals(2, result.size());
    }

    @Test
    void testGetNotificacionesByUsuarioId() {
        Notificacion n1 = new Notificacion();
        n1.setUsuarioId(1L);

        when(repository.findByUsuarioId(1L)).thenReturn(List.of(n1));

        List<Notificacion> result = service.getNotificacionesByUsuarioId(1L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUsuarioId());
    }

    @Test
    void testGetNotificacionesNoLeidasByUsuarioId() {
        Notificacion n1 = new Notificacion();
        n1.setUsuarioId(1L);
        n1.setLeida(false);

        when(repository.findByUsuarioIdAndLeidaFalse(1L)).thenReturn(List.of(n1));

        List<Notificacion> result = service.getNotificacionesNoLeidasByUsuarioId(1L);
        assertEquals(1, result.size());
        assertFalse(result.get(0).getLeida());
    }

    @Test
    void testMarcarComoLeida() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setUsuarioId(1L);
        notificacion.setLeida(false);

        when(repository.findById(1L)).thenReturn(Optional.of(notificacion));
        when(repository.save(any(Notificacion.class))).thenReturn(notificacion);

        Notificacion result = service.marcarComoLeida(1L, 1L);
        assertTrue(result.getLeida());
    }

    @Test
    void testMarcarComoLeida_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.marcarComoLeida(1L, 1L);
        });

        assertEquals("Notificación no encontrada", exception.getMessage());
    }

    @Test
    void testMarcarComoLeida_Unauthorized() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setUsuarioId(99L); // usuario incorrecto

        when(repository.findById(1L)).thenReturn(Optional.of(notificacion));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.marcarComoLeida(1L, 1L);
        });

        assertEquals("No autorizado", exception.getMessage());
    }
}