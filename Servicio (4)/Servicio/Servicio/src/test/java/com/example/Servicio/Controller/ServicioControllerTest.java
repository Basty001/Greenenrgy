package com.example.Servicio.Controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ServicioController.class)
public class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.example.Servicio.Service.ServicioService servicioService;

    private com.example.Servicio.Model.Servicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new com.example.Servicio.Model.Servicio(
            1L,
            LocalDate.of(2024, 1, 1),
            "Descripción test",
            "Servicio Test"
        );
    }

    @Test
    public void testListarServicios() throws Exception {
        when(servicioService.listarTodos()).thenReturn(Arrays.asList(servicio));

        mockMvc.perform(get("/api/v1/Servicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Descripción test"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(servicioService.buscarPorId(1L)).thenReturn(servicio);

        mockMvc.perform(get("/api/v1/Servicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreServicio").value("Servicio Test"));
    }

    @Test
    public void testEliminar() throws Exception {
        doNothing().when(servicioService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/Servicios/1"))
                .andExpect(status().isOk());
    }
}