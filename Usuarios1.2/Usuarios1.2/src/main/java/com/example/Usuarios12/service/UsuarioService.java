package com.example.Usuarios12.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Usuarios12.model.Rol;
import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.repository.RoleRepository;
import com.example.Usuarios12.repository.UsuarioRepository;

import jakarta.transaction.Transactional;



/**
 * Servicio que contiene la lógica de negocio para la gestión de usuarios.
 * Maneja operaciones CRUD, autenticación y encriptación de contraseñas.
 */
@Service
@Transactional
public class UsuarioService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return Lista de usuarios (vacía si no hay registros)
     */
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * @param username Nombre de usuario (debe ser único)
     * @param password Contraseña en texto plano (se encriptará)
     * @param roleId ID del rol asignado
     * @return Usuario creado
     * @throws RuntimeException Si el rol no existe
     */
    public Usuario crearUsuario(String username, String password, Long roleId) {
        // Validar que el rol exista
        Rol rol = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado ID: " + roleId));

        // Crear y guardar el nuevo usuario
        Usuario nuevo = new Usuario();
        nuevo.setUsername(username);
        nuevo.setPassword(passwordEncoder.encode(password));
        nuevo.setRol(rol);
        return usuarioRepository.save(nuevo);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param id ID del usuario a actualizar
     * @param username Nuevo nombre de usuario (opcional)
     * @param password Nueva contraseña (opcional, se encriptará)
     * @param roleId Nuevo ID de rol (opcional)
     * @return Usuario actualizado
     * @throws RuntimeException Si el usuario o rol no existen
     */
    public Usuario actualizarUsuario(Long id, String username, String password, Long roleId) {
        // Buscar usuario existente
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado ID: " + id));

        // Actualizar campos si se proporcionan
        if (username != null) existente.setUsername(username);
        if (password != null) existente.setPassword(passwordEncoder.encode(password));
        if (roleId != null) {
            Rol rol = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado ID: " + roleId));
            existente.setRol(rol);
        }
        return usuarioRepository.save(existente);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar
     */
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario a buscar
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Valida las credenciales de un usuario.
     * @param username Nombre de usuario
     * @param rawPassword Contraseña en texto plano
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String username, String rawPassword) {
        Optional<Usuario> opt = usuarioRepository.findByUsername(username);
        // Verificar si el usuario existe y si la contraseña coincide
        return opt.isPresent() && passwordEncoder.matches(rawPassword, opt.get().getPassword());
    }
}