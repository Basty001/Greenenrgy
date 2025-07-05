package com.example.Usuarios12.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

@Override
public UserDetails loadUserByUsername(String username) {
    Usuario usuario = usuarioRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    // Devuelve el nombre del rol exacto en BD ("Administrador")
    return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            Collections.singletonList(() -> usuario.getRol().getNombre()) 
    );
}
}