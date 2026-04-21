/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;
import com.example.backend.domain.*;
import com.example.backend.repo.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service per les incidencies
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repo;

    // Retorna les incidencies segons el rol
    public List<Incidencia> obtenerSegunRol(String rol, Integer userId) {
        if ("ADMIN".equals(rol)) return repo.findAll();
        
        if ("GESTOR".equals(rol)) {
            return repo.findAll().stream()
                .filter(i -> userId.equals(i.getAssignatA()) || userId.equals(i.getReportatPer()))
                .toList();
        }
        
        if ("MOZO".equals(rol)) return repo.findByAssignatA(userId);
        if ("TRANSPORTISTA".equals(rol)) return repo.findByReportatPer(userId);
        
        return List.of();
    }

    // Crea una incidencia
    public Incidencia crear(Incidencia inc) {
        inc.setDataCreacio(LocalDateTime.now()); 
        inc.setEstat("obert");
        
        if (inc.getReportatPer() != null) {
            inc.getHistorial().add(new EntradaHistorial(
                "Incidència creada", 
                "Creada por usuario ID: " + inc.getReportatPer(), 
                inc.getReportatPer().toString()
            ));
        }
        
        return repo.save(inc);
    }
    
    // Actualitza l'estat d'una incidencia
    public Incidencia actualizarEstado(Integer id, String nuevoEstado, String autor) {
        Incidencia inc = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        inc.setEstat(nuevoEstado);

        EntradaHistorial entrada = new EntradaHistorial();
        entrada.setAccio("Estat actualitzat");
        entrada.setDescripcio("L'estat ha canviat a: " + nuevoEstado);
        entrada.setDataHora(LocalDateTime.now());
        entrada.setAutor(autor);

        entrada.setIncidencia(inc); 

        inc.getHistorial().add(entrada);

        return repo.save(inc);
    }
}