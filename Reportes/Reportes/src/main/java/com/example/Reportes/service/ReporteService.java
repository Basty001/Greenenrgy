package com.example.Reportes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;
import com.example.Reportes.webclient.UsuarioClient;





import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Reporte> getReportes() {
        return reporteRepository.findAll();
    }

    public Reporte saveReporte(Reporte nuevoReporte) {
        Map<String, Object> usuario = usuarioClient.getUsuarioPorId(nuevoReporte.getUsuarioId());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado. No se puede guardar el reporte");
        }
        return reporteRepository.save(nuevoReporte);
    }

    public List<Reporte> obtenerPorUsuarioId(Long usuarioId) {
        return reporteRepository.findByUsuarioId(usuarioId);
    }

    public List<Reporte> buscarPorTitulo(String palabra) {
        return reporteRepository.findByTituloContainingIgnoreCase(palabra);
    }
}