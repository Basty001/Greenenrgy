package com.example.resena1.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.resena1.webclient.UsuarioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.resena1.model.Resena;
import com.example.resena1.repository.ResenaRepository;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    public void crearResena_Valida_Success() {
        Resena resena = new Resena();
        resena.setCalificacion(4);
        resena.setComentario("Buena experiencia");
        
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);
        
        Resena result = resenaService.crearResena(resena);
        
        assertNotNull(result);
        assertEquals(4, result.getCalificacion());
        assertFalse(result.isBaneada());
        verify(resenaRepository).save(any(Resena.class));
    }

    @Test
    public void crearResena_CalificacionInvalida_ThrowsException() {
        Resena resena = new Resena();
        resena.setCalificacion(6);
        
        assertThrows(ResponseStatusException.class, () -> resenaService.crearResena(resena));
    }

    @Test
    public void obtenerResenaPorId_Exists_Success() {
        Resena resena = new Resena();
        resena.setId(1L);
        
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));
        
        Resena result = resenaService.obtenerResenaPorId(1L);
        
        assertEquals(1L, result.getId());
    }

    @Test
    public void obtenerResenaPorId_NotExists_ThrowsException() {
        when(resenaRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResponseStatusException.class, () -> resenaService.obtenerResenaPorId(1L));
    }

    @Test
    public void actualizarResena_Exists_Success() {
        Resena existing = new Resena();
        existing.setId(1L);
        existing.setCalificacion(3);
        
        Resena updated = new Resena();
        updated.setCalificacion(5);
        
        when(resenaRepository.existsById(1L)).thenReturn(true);
        when(resenaRepository.save(any(Resena.class))).thenReturn(updated);
        
        Resena result = resenaService.actualizarResena(1L, updated);
        
        assertEquals(5, result.getCalificacion());
    }

    @Test
    public void banearResena_AdminUser_Success() {
        Resena resena = new Resena();
        resena.setId(1L);
        resena.setBaneada(false);
        
        when(usuarioClient.esAdministrador(1L)).thenReturn(true);
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);
        
        Resena result = resenaService.banearResena(1L, 1L);
        
        assertTrue(result.isBaneada());
    }

    @Test
    public void banearResena_NonAdminUser_ThrowsException() {
        when(usuarioClient.esAdministrador(1L)).thenReturn(false);
        
        assertThrows(ResponseStatusException.class, () -> resenaService.banearResena(1L, 1L));
    }

    @Test
    public void eliminarResena_Exists_Success() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        
        assertDoesNotThrow(() -> resenaService.eliminarResena(1L));
        verify(resenaRepository).deleteById(1L);
    }

    @Test
    public void eliminarResena_NotExists_ThrowsException() {
        when(resenaRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResponseStatusException.class, () -> resenaService.eliminarResena(1L));
    }
}