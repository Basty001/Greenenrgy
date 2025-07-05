package com.example.Notificaciones1.service;

import com.example.Notificaciones1.model.Notificacion;
import com.example.Notificaciones1.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {
    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public List<Notificacion> getAllNotificaciones() {
        return repository.findAll();
    }

    public Notificacion saveNotificacion(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    public List<Notificacion> getNotificacionesByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public List<Notificacion> getNotificacionesNoLeidasByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioIdAndLeidaFalse(usuarioId);
    }

    public Notificacion marcarComoLeida(Long id, Long usuarioId) {
        return repository.findById(id)
                .map(notificacion -> {
                    if (notificacion.getUsuarioId().equals(usuarioId)) {
                        notificacion.setLeida(true);
                        return repository.save(notificacion);
                    }
                    throw new RuntimeException("No autorizado");
                })
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }
}