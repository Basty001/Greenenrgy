package com.example.resena1.webclient;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {
    private final WebClient webClient;

    public UsuarioClient(
            @Value("${usuario-service.url}") String baseUrl,
            @Value("${usuario-service.username}") String username,
            @Value("${usuario-service.password}") String password) {
        
        String auth = "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes());
        
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", auth)
                .build();
    }

    public boolean esAdministrador(Long userId) {
        try {
            Map<String, Object> usuario = this.webClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), 
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Usuario no encontrado")))
                .bodyToMono(Map.class)
                .block();
            
            return usuario != null && "ADMIN".equalsIgnoreCase(usuario.get("rol").toString());
        } catch (Exception e) {
            System.err.println("Error al verificar administrador: " + e.getMessage());
            return false;
        }
    }
}