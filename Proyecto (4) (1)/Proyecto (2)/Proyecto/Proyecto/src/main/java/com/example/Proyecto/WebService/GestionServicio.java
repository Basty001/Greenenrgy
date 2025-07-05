package com.example.Proyecto.WebService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionServicio {
    private final WebClient webClient;
    
     public GestionServicio(@Value("${proyecto.service.url:http://localhost:8095/proyectos}") String proyectoServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(proyectoServiceUrl).build();
    }
     public Map<String, Object> getProyectoById(Long id) {
        return this.webClient.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Proyecto no encontrado")))
            .bodyToMono(Map.class)
            .block();
    }



}
