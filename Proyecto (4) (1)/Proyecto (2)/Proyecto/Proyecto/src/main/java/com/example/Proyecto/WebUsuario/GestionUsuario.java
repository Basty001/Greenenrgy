package com.example.Proyecto.WebUsuario;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionUsuario {
     private final WebClient webclient;

    public GestionUsuario(@Value("${proyecto.service.url:http://localhost:8080/proyectos}") String proyectoServiceUrl) {
        this.webclient = WebClient.builder().baseUrl(proyectoServiceUrl).build();
    }

    public Map<String, Object> getProyectoById(Long id) {
        return this.webclient.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Proyecto no encontrado")))
            .bodyToMono(Map.class)
            .block();
    }

}
