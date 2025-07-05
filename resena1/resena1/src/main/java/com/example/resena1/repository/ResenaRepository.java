package com.example.resena1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.resena1.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByIdUsuario(Long usuarioId);
}