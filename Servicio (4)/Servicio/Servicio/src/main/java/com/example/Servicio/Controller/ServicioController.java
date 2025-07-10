package com.example.Servicio.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Servicio.Model.Servicio;
import com.example.Servicio.Service.ServicioService;

@RestController
@RequestMapping("/api/v1/Servicios")

public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio>listar(){
        return servicioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Servicio buscarPorId(@PathVariable Long id) {
        return servicioService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        servicioService.eliminar(id);
    }

}
