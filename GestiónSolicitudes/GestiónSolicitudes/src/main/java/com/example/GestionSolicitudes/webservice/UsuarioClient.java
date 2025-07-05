package com.example.GestionSolicitudes.webservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;


import java.util.Map;



@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(
            @Value("${usuario-service.url}") String baseUrl,
            @Value("${usuario-service.username}") String username,
            @Value("${usuario-service.password}") String password) {

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers ->
                        headers.setBasicAuth(username, password))
                .build();
    }

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