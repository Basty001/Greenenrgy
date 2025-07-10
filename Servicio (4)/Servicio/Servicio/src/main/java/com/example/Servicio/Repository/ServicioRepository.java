package com.example.Servicio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Servicio.Model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long>{

}
