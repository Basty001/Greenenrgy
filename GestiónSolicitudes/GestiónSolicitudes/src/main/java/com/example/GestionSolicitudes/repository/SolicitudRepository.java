package com.example.GestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestionSolicitudes.model.SolicitudInstalacion;


@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudInstalacion, Long> {

    List<SolicitudInstalacion> findByUsuarioId(Long usuarioId);
}