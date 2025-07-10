package com.example.Privilegios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Privilegios.model.Privilegio;


@Repository
public interface PrivilegioRepository extends JpaRepository<Privilegio, Long> {
}
