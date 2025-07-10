package com.example.Privilegios.service;

import com.example.Privilegios.model.Privilegio;
import com.example.Privilegios.repository.PrivilegioRepository;

import org.junit.jupiter.api.BeforeEach;
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
public class PrivilegioServiceTest {

    @Mock
    private PrivilegioRepository privilegioRepository;

    @InjectMocks
    private PrivilegioService privilegioService;

    private Privilegio privilegio;

    @BeforeEach
    void setUp() {
        privilegio = new Privilegio(1L, "bastian_soto", "ADMIN", "ACCESO_TOTAL", 10L);
    }

    @Test
    public void testCrearPrivilegio() {
        when(privilegioRepository.save(privilegio)).thenReturn(privilegio);

        Privilegio creado = privilegioService.crearPrivilegio(privilegio);

        assertNotNull(creado);
        assertEquals("bastian_soto", creado.getUsuario());
        verify(privilegioRepository, times(1)).save(privilegio);
    }

    @Test
    public void testObtenerTodos() {
        List<Privilegio> lista = Arrays.asList(privilegio);
        when(privilegioRepository.findAll()).thenReturn(lista);

        List<Privilegio> resultado = privilegioService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getRol());
        verify(privilegioRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPorId_Existe() {
        when(privilegioRepository.findById(1L)).thenReturn(Optional.of(privilegio));

        Privilegio resultado = privilegioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("ACCESO_TOTAL", resultado.getPermiso());
        verify(privilegioRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerPorId_NoExiste() {
        when(privilegioRepository.findById(2L)).thenReturn(Optional.empty());

        Privilegio resultado = privilegioService.obtenerPorId(2L);

        assertNull(resultado);
        verify(privilegioRepository, times(1)).findById(2L);
    }

    @Test
    public void testEliminarPrivilegio() {
        doNothing().when(privilegioRepository).deleteById(1L);

        privilegioService.eliminarPrivilegio(1L);

        verify(privilegioRepository, times(1)).deleteById(1L);
    }
}