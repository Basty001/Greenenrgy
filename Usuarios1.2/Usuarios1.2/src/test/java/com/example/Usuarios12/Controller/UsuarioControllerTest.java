package com.example.Usuarios12.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.example.Usuarios12.controller.UsuarioController;
import com.example.Usuarios12.model.Rol;
import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Collections;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void getUsuarios_deberiaRetornarListaUsuariosYStatus200() throws Exception {
        // 1. Preparar datos de prueba
        Rol rol = new Rol(1L, "Cliente", null);
        Usuario usuario = new Usuario(1L, "usuario1", "pass123", rol);
        List<Usuario> listaUsuarios = List.of(usuario);

        // 2. Configurar el mock
        when(usuarioService.obtenerUsuarios()).thenReturn(listaUsuarios);

        // 3. Hacer la petición y verificar
        mockMvc.perform(get("/api/v1/usuario/users")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].username").value("usuario1"))
               .andExpect(jsonPath("$[0].rol.nombre").value("Cliente"));
    }

    @Test
    void getUsuarios_deberiaRetornar204CuandoNoHayUsuarios() throws Exception {
        // 1. Configurar mock para lista vacía
        when(usuarioService.obtenerUsuarios()).thenReturn(Collections.emptyList());

        // 2. Hacer la petición y verificar
        mockMvc.perform(get("/api/v1/usuario/users")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    void getUsuarioPorId_deberiaRetornarUsuarioYStatus200() throws Exception {
        // 1. Preparar datos de prueba
        Rol rol = new Rol(1L, "Admin", null);
        Usuario usuario = new Usuario(1L, "admin1", "adminpass", rol);

        // 2. Configurar el mock
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        // 3. Hacer la petición y verificar
        mockMvc.perform(get("/api/v1/usuario/users/1")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value("admin1"))
               .andExpect(jsonPath("$.rol.nombre").value("Admin"));
    }

    @Test
    void getUsuarioPorId_deberiaRetornar404CuandoNoExiste() throws Exception {
        // 1. Configurar mock para usuario no encontrado
        when(usuarioService.obtenerUsuarioPorId(999L)).thenReturn(null);

        // 2. Hacer la petición y verificar
        mockMvc.perform(get("/api/v1/usuario/users/999")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    void crearUsuario_deberiaRetornarUsuarioCreadoYStatus201() throws Exception {
        // 1. Preparar datos de prueba
        Rol rol = new Rol(1L, "Cliente", null);
        Usuario nuevoUsuario = new Usuario(2L, "nuevoUser", "nuevoPass", rol);

        // 2. Configurar el mock
        when(usuarioService.crearUsuario("nuevoUser", "nuevoPass", 1L))
            .thenReturn(nuevoUsuario);

        // 3. Hacer la petición y verificar
        mockMvc.perform(post("/api/v1/usuario/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"username\":\"nuevoUser\",\"password\":\"nuevoPass\",\"rolId\":1}"))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.username").value("nuevoUser"))
               .andExpect(jsonPath("$.rol.id").value(1));
    }

    @Test
    void actualizarUsuario_deberiaRetornarUsuarioActualizadoYStatus200() throws Exception {
        // 1. Preparar datos de prueba
        Rol rol = new Rol(1L, "Admin", null);
        Usuario usuarioActualizado = new Usuario(1L, "userModificado", "nuevaPass", rol);

        // 2. Configurar el mock
        when(usuarioService.actualizarUsuario(1L, "userModificado", "nuevaPass", 1L))
            .thenReturn(usuarioActualizado);

        // 3. Hacer la petición y verificar
        mockMvc.perform(put("/api/v1/usuario/users/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"username\":\"userModificado\",\"password\":\"nuevaPass\",\"rolId\":1}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value("userModificado"));
    }

    @Test
    void eliminarUsuario_deberiaRetornarStatus204() throws Exception {
        // 1. No necesitamos datos de prueba para eliminación
        
        // 2. No necesitamos configurar mock ya que el método es void
        
        // 3. Hacer la petición y verificar
        mockMvc.perform(delete("/api/v1/usuario/users/1")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        // 4. Verificar que se llamó al método
        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

    @Test
    void login_deberiaRetornar200CuandoCredencialesSonValidas() throws Exception {
        // 1. Configurar mock para credenciales válidas
        when(usuarioService.validarCredenciales("userValido", "passValida"))
            .thenReturn(true);

        // 2. Hacer la petición y verificar
        mockMvc.perform(post("/api/v1/usuario/login")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"username\":\"userValido\",\"password\":\"passValida\"}"))
               .andExpect(status().isOk())
               .andExpect(content().string("Login exitoso"));
    }

    @Test
    void login_deberiaRetornar401CuandoCredencialesSonInvalidas() throws Exception {
        // 1. Configurar mock para credenciales inválidas
        when(usuarioService.validarCredenciales("userInvalido", "passInvalida"))
            .thenReturn(false);

        // 2. Hacer la petición y verificar
        mockMvc.perform(post("/api/v1/usuario/login")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"username\":\"userInvalido\",\"password\":\"passInvalida\"}"))
               .andExpect(status().isUnauthorized())
               .andExpect(content().string("Credenciales inválidas"));
    }
}

