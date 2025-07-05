package com.example.Notificaciones1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testSaveNotificacion() throws Exception {
        mockMvc.perform(post("/api/notificaciones")
               .header("X-User-Id", "1")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"mensaje\":\"Test\"}"))
               .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllNotificaciones() throws Exception {
        mockMvc.perform(get("/api/notificaciones"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_USER"})
    void testGetNotificacionesByUsuario() throws Exception {
        mockMvc.perform(get("/api/notificaciones/usuario/1"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_USER"})
    void testGetNotificacionesNoLeidasByUsuario() throws Exception {
        mockMvc.perform(get("/api/notificaciones/usuario/1/no-leidas"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = {"ROLE_USER"})
    void testMarcarNotificacionComoLeida() throws Exception {
        mockMvc.perform(put("/api/notificaciones/1/marcar-leida")
               .header("X-User-Id", "1"))
               .andExpect(status().isOk());
    }
}