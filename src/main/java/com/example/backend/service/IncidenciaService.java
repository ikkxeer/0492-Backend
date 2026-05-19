/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.*;
import com.example.backend.repo.IncidenciaRepository;
import com.example.backend.repo.UserRepository;
import com.example.backend.repo.GrupMozosRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GrupMozosRepository grupMozosRepository;

    private Incidencia populateNoms(Incidencia inc) {
        if (inc.getReportatPer() != null) {
            userRepository.findById(inc.getReportatPer())
                    .ifPresent(u -> inc.setReportatPerNom(u.getNom()));
        }
        if (inc.getAssignatA() != null) {
            grupMozosRepository.findById(inc.getAssignatA())
                    .ifPresent(g -> inc.setAssignatANom(g.getNom()));
        }
        return inc;
    }

    // Retorna les incidencies segons el rol
    public List<Incidencia> obtenerSegunRol(String rol, Integer userId) {
        System.out.println("[DEBUG - INCIDENCIA] obtenerSegunRol -> rol: [" + rol + "], userId: [" + userId + "]");
        List<Incidencia> result;
        if (rol == null) {
            result = List.of();
        } else if (rol.equalsIgnoreCase("ADMIN")) {
            result = repo.findAll();
        } else if (rol.equalsIgnoreCase("GESTOR")) {
            result = repo.findAll().stream()
                    .filter(i -> userId.equals(i.getAssignatA()) || userId.equals(i.getReportatPer()))
                    .toList();
        } else if (rol.equalsIgnoreCase("MOZO") || rol.equalsIgnoreCase("MOSSO")) {
            java.util.Optional<UserAccount> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                try {
                    List<String> grupNames = userOpt.get().getGrups().stream()
                        .map(g -> g.getNom() + " (ID: " + g.getId_grup() + ")")
                        .toList();
                    System.out.println("[DEBUG - INCIDENCIA] User " + userOpt.get().getNom() + " (ID: " + userId + ") found. Groups: " + grupNames);
                } catch (Exception e) {
                    System.out.println("[DEBUG - INCIDENCIA] Error fetching groups for user: " + e.getMessage());
                }
            } else {
                System.out.println("[DEBUG - INCIDENCIA] User with ID " + userId + " NOT found in DB!");
            }
            result = repo.findByMozoUsuarioId(userId);
            System.out.println("[DEBUG - INCIDENCIA] MOZO query returned count: " + (result != null ? result.size() : 0));
        } else if (rol.equalsIgnoreCase("TRANSPORTISTA") || rol.equalsIgnoreCase("REPARTIDOR")) {
            result = repo.findByReportatPer(userId);
        } else {
            result = List.of();
        }

        List<Incidencia> populated = result.stream().map(this::populateNoms).toList();
        System.out.println("[DEBUG - INCIDENCIA] obtenerSegunRol total populated: " + populated.size());
        return populated;
    }

    // Crea una incidencia
    public Incidencia crear(Incidencia inc) {
        inc.setDataCreacio(LocalDateTime.now());
        inc.setEstat("obert");

        if (inc.getReportatPer() != null) {
            EntradaHistorial entrada = new EntradaHistorial(
                    "Incidència creada",
                    "Creada por usuario ID: " + inc.getReportatPer(),
                    inc.getReportatPer().toString());
            entrada.setIncidencia(inc);
            inc.getHistorial().add(entrada);
        }

        return populateNoms(repo.save(inc));
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

        return populateNoms(repo.save(inc));
    }

    // Assigna un responsable a una incidencia
    public Incidencia assignarResponsable(Integer id, Integer responsableId, String autor) {
        Incidencia inc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        inc.setAssignatA(responsableId);

        EntradaHistorial entrada = new EntradaHistorial();
        entrada.setAccio("Responsable assignat");
        entrada.setDescripcio("Assignat al grup ID: " + responsableId);
        entrada.setDataHora(LocalDateTime.now());
        entrada.setAutor(autor);
        entrada.setIncidencia(inc);
        inc.getHistorial().add(entrada);

        return populateNoms(repo.save(inc));
    }

    // Elimina una incidencia — només si està tancada
    public void eliminar(Integer id) {
        Incidencia inc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidència no trobada"));

        if (!"tancat".equals(inc.getEstat())) {
            throw new IllegalStateException("Només es poden eliminar incidències tancades");
        }

        repo.deleteById(id);
    }
}