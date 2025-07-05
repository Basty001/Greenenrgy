package com.example.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.Model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

}
