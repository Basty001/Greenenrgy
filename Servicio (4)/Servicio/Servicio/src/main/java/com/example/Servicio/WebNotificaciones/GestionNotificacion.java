package com.example.Servicio.WebNotificaciones;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionNotificacion {
     private final WebClient webclientNotificaciones;

    public GestionNotificacion(@Value("${notificaciones.service.url:http://localhost:8084/notificaciones}") String notificacionesServiceUrl) {
        this.webclientNotificaciones = WebClient.builder().baseUrl(notificacionesServiceUrl).build();
    }

    public Map<String, Object> getNotificacionById(Long id) {
        return this.webclientNotificaciones.get()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Notificación no encontrada")))
            .bodyToMono(Map.class)
            .block();
    }

    public void enviarNotificacion(Map<String, Object> notificacion) {
        this.webclientNotificaciones.post()
            .uri("/")
            .bodyValue(notificacion)
            .retrieve()
            .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("Error al enviar notificación")))
            .bodyToMono(Void.class)
            .block();
    }
}

