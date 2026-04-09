/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.web.dto.OrdreDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service per les ordres
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class OrdreService {

    @Autowired
    private OrdreRepository ordreRepository;

    // Retorna totes les ordres
    public List<OrdreDTO> findAll() {
        return ordreRepository.findAll().stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar per Gestor
    public List<OrdreDTO> findByGestor(String nom) {
        return ordreRepository.findByGestorNom(nom).stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }
    
    // Buscar per ID
    public Optional<OrdreDTO> findByIdentificador(String id) {
        return ordreRepository.findAll().stream()
                .filter(o -> o.getIdentificador().equals(id))
                .findFirst()
                .map(OrdreDTO::new);
    }
    
    public List<OrdreDTO> findByMozoGrupo(String nomMozo) {
        return ordreRepository.findByGrupoMozoUsuarioNom(nomMozo).stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }
}