package com.example.Soporte.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Soporte.model.TicketSoporte;
import com.example.Soporte.repository.TicketSoporteRepository;

@Service
public class SoporteService {

    @Autowired
    private TicketSoporteRepository ticketSoporteRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    // URL del microservicio de usuarios (activa solo hasta las 4 am, según comentario)
    private final String URL_USUARIOS = "http://localhost:8080/api/v1/usuario/users";

    // Obtener todos los tickets
    public List<TicketSoporte> getTicket() {
        return ticketSoporteRepository.findAll();
    }

    // Guardar un nuevo ticket si el usuario existe en el microservicio
    public TicketSoporte saveTicket(TicketSoporte nuevoTicket) {
        List<Map<String, Object>> usuarios = restTemplate.getForObject(URL_USUARIOS, List.class);

        if (usuarios == null || usuarios.isEmpty()) {
            throw new RuntimeException("No se pudo obtener la lista de usuarios");
        }

        boolean existeUsuario = usuarios.stream()
            .anyMatch(u -> u.get("id") != null && u.get("id").toString().equals(nuevoTicket.getUsuarioId().toString()));

        if (!existeUsuario) {
            throw new RuntimeException("Usuario no encontrado. No se puede guardar el ticket");
        }

        return ticketSoporteRepository.save(nuevoTicket);
    }

    public List<TicketSoporte> obtenerPorUsuarioId(Long usuarioId) {
        return ticketSoporteRepository.findByUsuarioId(usuarioId);
    }

    public List<TicketSoporte> obtenerPorEstado(String estado) {
        return ticketSoporteRepository.findByEstadoIgnoreCase(estado);
    }

    public List<TicketSoporte> buscarPorTitulo(String palabra) {
        return ticketSoporteRepository.findByTituloContainingIgnoreCase(palabra);
    }
}