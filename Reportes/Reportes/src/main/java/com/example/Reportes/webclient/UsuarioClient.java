package com.example.Reportes.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    // El baseUrl debe ser solo el host, NO pongas rutas largas aquí.
    // Ejemplo: usuario-service.url=http://localhost:8080
    public UsuarioClient(
            @Value("${usuario-service.url}") String baseUrl,
            @Value("${usuario-service.username}") String username,
            @Value("${usuario-service.password}") String password) {

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl) // Ej: http://localhost:8080
                .defaultHeaders(headers ->
                        headers.setBasicAuth(username, password) // Autenticación básica
                )
                .build();
    }

        // Llama a: GET http://localhost:8080/api/v1/usuario/users
        public List<Map<String, Object>> getUsuarios() {
            try {
                return webClient.get()
                        .uri("/api/v1/usuario/users")
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                        .block();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    // Llama a: GET http://localhost:8080/api/v1/usuario/users/{id}
    public Map<String, Object> getUsuarioPorId(Long id) {
        try {
            return webClient.get()
                    .uri("/api/v1/usuario/users/{id}", id)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}