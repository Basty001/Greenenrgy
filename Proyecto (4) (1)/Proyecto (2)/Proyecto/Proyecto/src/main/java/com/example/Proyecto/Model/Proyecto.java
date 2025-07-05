package com.example.Proyecto.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proyecto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_proyecto", nullable = false)
    private String nombreProyecto;

    

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaProyecto;



    @Column(nullable = false)
    private String description;


}
