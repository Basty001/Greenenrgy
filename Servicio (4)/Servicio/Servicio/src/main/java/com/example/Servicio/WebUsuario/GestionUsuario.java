package com.example.Servicio.WebUsuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionUsuario {
     private final WebClient webclientUsuarios;

    public GestionUsuario(@Value("${usuario.service.url:http://localhost:8080/usuarios}") String usuarioServiceUrl) {
        this.webclientUsuarios = WebClient.builder().baseUrl(usuarioServiceUrl).build();
    }

    public Map<String, Object> getUsuarioById(Long id) {
        return this.webclientUsuarios.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Usuario no encontrado")))
            .bodyToMono(Map.class)
            .block();
    }

    public void crearUsuario(Map<String, Object> usuario) {
        this.webclientUsuarios.post()
            .uri("/")
            .bodyValue(usuario)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Error al crear usuario")))
            .bodyToMono(Void.class)
            .block();
    }

}

