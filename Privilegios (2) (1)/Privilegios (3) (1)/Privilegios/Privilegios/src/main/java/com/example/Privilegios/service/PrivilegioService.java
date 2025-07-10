package com.example.Privilegios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Privilegios.model.Privilegio;
import com.example.Privilegios.repository.PrivilegioRepository;

@Service
public class PrivilegioService {

    @Autowired
    private PrivilegioRepository privilegioRepository;

    public Privilegio crearPrivilegio(Privilegio privilegio) {
        return privilegioRepository.save(privilegio);
    }

    public List<Privilegio> obtenerTodos() {
        return privilegioRepository.findAll();
    }

    public Privilegio obtenerPorId(Long id) {
        return privilegioRepository.findById(id).orElse(null);
    }

    public void eliminarPrivilegio(Long id) {
        privilegioRepository.deleteById(id);
    }
}