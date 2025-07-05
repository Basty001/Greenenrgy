package com.example.GestionSolicitudes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionSolicitudes.model.SolicitudInstalacion;
import com.example.GestionSolicitudes.repository.SolicitudRepository;
import com.example.GestionSolicitudes.webservice.UsuarioClient;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<SolicitudInstalacion> getSolicitudes() {
        return solicitudRepository.findAll();
    }

    public SolicitudInstalacion saveSolicitud(SolicitudInstalacion nuevaSolicitud) {
    Map<String, Object> usuario = usuarioClient.getUsuarioPorId(nuevaSolicitud.getUsuarioId());

    if (usuario == null) {
        throw new RuntimeException("Usuario no encontrado. No se puede guardar la solicitud");
    }

    // Asignar fecha actual automáticamente
    nuevaSolicitud.setFechaCreacion(LocalDateTime.now());

    return solicitudRepository.save(nuevaSolicitud);
}

    public List<SolicitudInstalacion> obtenerPorUsuarioId(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }
}