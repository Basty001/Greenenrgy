package com.example.Soporte.controller;


import com.example.Soporte.model.TicketSoporte;
import com.example.Soporte.service.SoporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @MockBean
    private SoporteService soporteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void obtenerTicket_returnsOkAndJson() throws Exception {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(1L);
        ticket.setTitulo("Prueba");
        ticket.setEstado("Abierto");
        ticket.setUsuarioId(1L);

        when(soporteService.getTicket()).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/v1/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser
    void crearTicket_success() throws Exception {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(1L);
        ticket.setTitulo("Nuevo ticket");
        ticket.setEstado("Abierto");
        ticket.setUsuarioId(2L);

        when(soporteService.saveTicket(ticket)).thenReturn(ticket);

        mockMvc.perform(post("/api/v1/soporte")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"titulo\":\"Nuevo ticket\",\"estado\":\"Abierto\",\"usuarioId\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nuevo ticket"));
    }

    @Test
    @WithMockUser
    void obtenerPorUsuario_returnsOk() throws Exception {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(2L);
        ticket.setUsuarioId(99L);

        when(soporteService.obtenerPorUsuarioId(99L)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/v1/soporte/usuario/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(99));
    }

    @Test
    @WithMockUser
    void obtenerPorEstado_returnsOk() throws Exception {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(3L);
        ticket.setEstado("Cerrado");

        when(soporteService.obtenerPorEstado("Cerrado")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/v1/soporte/estado/Cerrado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("Cerrado"));
    }

    @Test
    @WithMockUser
    void buscarPorTitulo_returnsOk() throws Exception {
        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(4L);
        ticket.setTitulo("Error en el sistema");

        when(soporteService.buscarPorTitulo("Error")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/v1/soporte/buscar?titulo=Error"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Error en el sistema"));
    }
}