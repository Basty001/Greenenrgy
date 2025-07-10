package com.example.Servicio.Service;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioServiceTest {

    @Mock
    private com.example.Servicio.Repository.ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private com.example.Servicio.Model.Servicio servicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servicio = new com.example.Servicio.Model.Servicio(
            1L,
            LocalDate.now(),
            "Servicio de prueba",
            "Nombre del servicio"
        );
    }

    @Test
    public void testListarTodos() {
        List<com.example.Servicio.Model.Servicio> lista = Arrays.asList(servicio);
        when(servicioRepository.findAll()).thenReturn(lista);

        List<com.example.Servicio.Model.Servicio> resultado = servicioService.listarTodos();

        assertEquals(1, resultado.size());
        verify(servicioRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        com.example.Servicio.Model.Servicio resultado = servicioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Servicio de prueba", resultado.getDescripcion());
    }

    @Test
    public void testGuardar() {
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        com.example.Servicio.Model.Servicio resultado = servicioService.guardar(servicio);

        assertNotNull(resultado);
        verify(servicioRepository, times(1)).save(servicio);
    }

    @Test
    public void testEliminar() {
        servicioService.eliminar(1L);
        verify(servicioRepository, times(1)).deleteById(1L);
    }
}