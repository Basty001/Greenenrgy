package com.example.Notificaciones1.webclient;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // Busca usuario por id y retorna Optional con los datos
    public Optional<Map<String, Object>> getUsuarioById(Long usuarioId) {
        try {
            Mono<Map> response = webClient.get()
                    .uri("/{id}", usuarioId) // ✅ Asegúrate que el endpoint sea correcto
                    .retrieve()
                    .bodyToMono(Map.class);

            Map<String, Object> usuario = response.block();

            return Optional.ofNullable(usuario);

        } catch (Exception e) {
            System.err.println("Error al obtener usuario con id " + usuarioId + ": " + e.getMessage());
            return Optional.empty();
        }
    }
}