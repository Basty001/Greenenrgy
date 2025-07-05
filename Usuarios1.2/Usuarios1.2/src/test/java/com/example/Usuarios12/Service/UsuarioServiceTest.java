package com.example.Usuarios12.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Usuarios12.model.Rol;
import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.repository.RoleRepository;
import com.example.Usuarios12.repository.UsuarioRepository;
import com.example.Usuarios12.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerUsuarios_debeRetornarListaUsuarios() {
        // 1. Preparar datos de prueba
        Rol rol = new Rol(1L, "Admin", null);
        Usuario usuario1 = new Usuario(1L, "user1", "pass1", rol);
        Usuario usuario2 = new Usuario(2L, "user2", "pass2", rol);
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        // 2. Configurar mock
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // 3. Ejecutar y verificar
        List<Usuario> resultado = usuarioService.obtenerUsuarios();
        
        assertEquals(2, resultado.size());
        assertEquals("user1", resultado.get(0).getUsername());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerUsuarioPorId_debeRetornarUsuarioCuandoExiste() {
        // 1. Preparar datos
        Rol rol = new Rol(1L, "User", null);
        Usuario usuario = new Usuario(1L, "testuser", "testpass", rol);

        // 2. Configurar mock
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // 3. Ejecutar y verificar
        Usuario resultado = usuarioService.obtenerUsuarioPorId(1L);
        
        assertNotNull(resultado);
        assertEquals("testuser", resultado.getUsername());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerUsuarioPorId_debeRetornarNullCuandoNoExiste() {
        // 1. Configurar mock
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // 2. Ejecutar y verificar
        Usuario resultado = usuarioService.obtenerUsuarioPorId(999L);
        
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    void crearUsuario_debeRetornarUsuarioCreado() {
        // 1. Preparar datos
        String username = "newuser";
        String password = "newpass";
        Long roleId = 1L;
        Rol rol = new Rol(roleId, "Admin", null);
        Usuario usuarioGuardado = new Usuario(1L, username, "encryptedPass", rol);

        // 2. Configurar mocks
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode(password)).thenReturn("encryptedPass");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // 3. Ejecutar y verificar
        Usuario resultado = usuarioService.crearUsuario(username, password, roleId);
        
        assertNotNull(resultado);
        assertEquals(username, resultado.getUsername());
        assertEquals("encryptedPass", resultado.getPassword());
        assertEquals("Admin", resultado.getRol().getNombre());
        verify(roleRepository, times(1)).findById(roleId);
        verify(passwordEncoder, times(1)).encode(password);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoRolNoExiste() {
        // 1. Configurar mock
        Long roleId = 999L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // 2. Ejecutar y verificar
        assertThrows(RuntimeException.class, () -> {
            usuarioService.crearUsuario("user", "pass", roleId);
        });
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    void actualizarUsuario_debeActualizarCamposProporcionados() {
        // 1. Preparar datos
        Long userId = 1L;
        Rol rolOriginal = new Rol(1L, "User", null);
        Rol rolNuevo = new Rol(2L, "Admin", null);
        Usuario usuarioExistente = new Usuario(userId, "olduser", "oldpass", rolOriginal);
        Usuario usuarioActualizado = new Usuario(userId, "newuser", "newencryptedpass", rolNuevo);

        // 2. Configurar mocks
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioExistente));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(rolNuevo));
        when(passwordEncoder.encode("newpass")).thenReturn("newencryptedpass");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // 3. Ejecutar y verificar
        Usuario resultado = usuarioService.actualizarUsuario(
            userId, "newuser", "newpass", 2L);
        
        assertEquals("newuser", resultado.getUsername());
        assertEquals("newencryptedpass", resultado.getPassword());
        assertEquals("Admin", resultado.getRol().getNombre());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(2L);
        verify(passwordEncoder, times(1)).encode("newpass");
    }

    @Test
    void actualizarUsuario_debeLanzarExcepcionCuandoUsuarioNoExiste() {
        // 1. Configurar mock
        Long userId = 999L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // 2. Ejecutar y verificar
        assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarUsuario(userId, "user", "pass", 1L);
        });
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void eliminarUsuario_debeEliminarUsuario() {
        // 1. No necesitamos datos para este test
        
        // 2. No necesitamos configurar mock ya que el método es void
        
        // 3. Ejecutar
        usuarioService.eliminarUsuario(1L);
        
        // 4. Verificar
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorUsername_debeRetornarUsuarioCuandoExiste() {
        // 1. Preparar datos
        Rol rol = new Rol(1L, "User", null);
        Usuario usuario = new Usuario(1L, "existinguser", "pass", rol);

        // 2. Configurar mock
        when(usuarioRepository.findByUsername("existinguser")).thenReturn(Optional.of(usuario));

        // 3. Ejecutar y verificar
        Optional<Usuario> resultado = usuarioService.buscarPorUsername("existinguser");
        
        assertTrue(resultado.isPresent());
        assertEquals("existinguser", resultado.get().getUsername());
        verify(usuarioRepository, times(1)).findByUsername("existinguser");
    }

    @Test
    void validarCredenciales_debeRetornarTrueCuandoCredencialesSonCorrectas() {
        // 1. Preparar datos
        Rol rol = new Rol(1L, "User", null);
        Usuario usuario = new Usuario(1L, "validuser", "encryptedPass", rol);

        // 2. Configurar mocks
        when(usuarioRepository.findByUsername("validuser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("validpass", "encryptedPass")).thenReturn(true);

        // 3. Ejecutar y verificar
        boolean resultado = usuarioService.validarCredenciales("validuser", "validpass");
        
        assertTrue(resultado);
        verify(usuarioRepository, times(1)).findByUsername("validuser");
        verify(passwordEncoder, times(1)).matches("validpass", "encryptedPass");
    }

    @Test
    void validarCredenciales_debeRetornarFalseCuandoPasswordEsIncorrecta() {
        // 1. Preparar datos
        Rol rol = new Rol(1L, "User", null);
        Usuario usuario = new Usuario(1L, "validuser", "encryptedPass", rol);

        // 2. Configurar mocks
        when(usuarioRepository.findByUsername("validuser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("invalidpass", "encryptedPass")).thenReturn(false);

        // 3. Ejecutar y verificar
        boolean resultado = usuarioService.validarCredenciales("validuser", "invalidpass");
        
        assertFalse(resultado);
    }

    @Test
    void validarCredenciales_debeRetornarFalseCuandoUsuarioNoExiste() {
        // 1. Configurar mock
        when(usuarioRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // 2. Ejecutar y verificar
        boolean resultado = usuarioService.validarCredenciales("nonexistent", "anypass");
        
        assertFalse(resultado);
    }
}