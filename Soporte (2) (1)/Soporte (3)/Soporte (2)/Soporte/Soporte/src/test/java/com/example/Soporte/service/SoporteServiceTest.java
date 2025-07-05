package com.example.Soporte.service;

import com.example.Soporte.model.TicketSoporte;
import com.example.Soporte.repository.TicketSoporteRepository;
import com.example.Soporte.webcliet.UsuarioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {

    @Mock
    private TicketSoporteRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private SoporteService service;

    @Test
    void getAll_returnsListFromRepository() {
        List<TicketSoporte> mockList = List.of(
            new TicketSoporte(1L, "Título", "Descripción", "ABIERTO", LocalDateTime.now(), 1L)
        );

        when(repository.findAll()).thenReturn(mockList);

        List<TicketSoporte> resultado = service.getTicket();

        assertThat(resultado).isEqualTo(mockList);
    }

    

    

    @Test
    void obtenerPorUsuarioId_debeRetornarTicketsFiltrados() {
        TicketSoporte t1 = new TicketSoporte(1L, "Bug", "detalle", "ABIERTO", LocalDateTime.now(), 20L);

        when(repository.findByUsuarioId(20L)).thenReturn(List.of(t1));

        List<TicketSoporte> resultado = service.obtenerPorUsuarioId(20L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(20L);
    }

    @Test
    void obtenerPorEstado_debeIgnorarCase() {
        TicketSoporte t1 = new TicketSoporte(2L, "Error", "detalle", "cerrado", LocalDateTime.now(), 5L);

        when(repository.findByEstadoIgnoreCase("CERRADO")).thenReturn(List.of(t1));

        List<TicketSoporte> resultado = service.obtenerPorEstado("CERRADO");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado().toLowerCase()).isEqualTo("cerrado");
    }

    @Test
    void buscarPorTitulo_debeRetornarCoincidencias() {
        TicketSoporte t1 = new TicketSoporte(3L, "Problema en login", "detalle", "ABIERTO", LocalDateTime.now(), 8L);

        when(repository.findByTituloContainingIgnoreCase("login")).thenReturn(List.of(t1));

        List<TicketSoporte> resultado = service.buscarPorTitulo("login");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo().toLowerCase()).contains("login");
    }
}