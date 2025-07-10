package com.example.Privilegios.controller;

import com.example.Privilegios.config.SecurityTestConfig;
import com.example.Privilegios.model.Privilegio;
import com.example.Privilegios.service.PrivilegioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrivilegioController.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
@Import(SecurityTestConfig.class)
public class PrivilegioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrivilegioService privilegioService;

    @Autowired
    private ObjectMapper objectMapper;

    // Datos de prueba
    private Privilegio getPrivilegioEjemplo() {
        return new Privilegio(1L, "bastian_soto", "ADMIN", "ACCESO_TOTAL", 10L);
    }

    @Test
    public void testCrearPrivilegio() throws Exception {
        Privilegio privilegio = getPrivilegioEjemplo();

        when(privilegioService.crearPrivilegio(any(Privilegio.class))).thenReturn(privilegio);

        mockMvc.perform(post("/api/privilegios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privilegio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(privilegio.getId()))
                .andExpect(jsonPath("$.usuario").value(privilegio.getUsuario()))
                .andExpect(jsonPath("$.rol").value(privilegio.getRol()))
                .andExpect(jsonPath("$.permiso").value(privilegio.getPermiso()))
                .andExpect(jsonPath("$.usuarioId").value(privilegio.getUsuarioId()));
    }

    @Test
    public void testObtenerTodosLosPrivilegios() throws Exception {
        List<Privilegio> lista = Arrays.asList(getPrivilegioEjemplo());

        when(privilegioService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/privilegios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(lista.size()))
                .andExpect(jsonPath("$[0].usuario").value("bastian_soto"));
    }

    @Test
    public void testObtenerPrivilegioPorIdExistente() throws Exception {
        Privilegio privilegio = getPrivilegioEjemplo();

        when(privilegioService.obtenerPorId(1L)).thenReturn(privilegio);

        mockMvc.perform(get("/api/privilegios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("bastian_soto"));
    }

    @Test
    public void testObtenerPrivilegioPorIdNoExistente() throws Exception {
        when(privilegioService.obtenerPorId(100L)).thenReturn(null);

        mockMvc.perform(get("/api/privilegios/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarPrivilegioExistente() throws Exception {
        Privilegio privilegio = getPrivilegioEjemplo();
        when(privilegioService.obtenerPorId(1L)).thenReturn(privilegio);
        doNothing().when(privilegioService).eliminarPrivilegio(1L);

        mockMvc.perform(delete("/api/privilegios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarPrivilegioNoExistente() throws Exception {
        when(privilegioService.obtenerPorId(999L)).thenReturn(null);

        mockMvc.perform(delete("/api/privilegios/999"))
                .andExpect(status().isNotFound());
    }
}