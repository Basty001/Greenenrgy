package com.example.Proyecto.WebNotificacion;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionNotificaciones {
       private final WebClient webclientProyectos;

    public GestionNotificaciones(@Value("${proyecto.service.url:http://localhost:8084/proyectos}") String proyectoServiceUrl) {
        this.webclientProyectos = WebClient.builder().baseUrl(proyectoServiceUrl).build();
    }

    public Map<String, Object> getProyectoById(Long id) {
        return this.webclientProyectos.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Proyecto no encontrado")))
            .bodyToMono(Map.class)
            .block();
    }
    

}
