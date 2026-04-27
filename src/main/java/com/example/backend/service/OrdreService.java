/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.Ordre;
import com.example.backend.domain.Pale;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.repo.PaleRepository;
import com.example.backend.repo.UserRepository;
import com.example.backend.web.dto.OrdreCreateDTO;
import com.example.backend.web.dto.OrdreDTO;
import org.springframework.transaction.annotation.Transactional; 
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private PaleRepository paleRepository;

    // rolId=1: ADMIN totes les ordres
    public List<OrdreDTO> findAll() {
        return ordreRepository.findAll().stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }

    // rolId=2: GESTOR ordres on ell és el gestor
    public List<OrdreDTO> findByGestor(String nom) {
        return ordreRepository.findByGestorNom(nom).stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }

    // rolId=3: MOZO ordres assignades al seu grup
    public List<OrdreDTO> findByMozoGrupo(String nomMozo) {
        return ordreRepository.findByGrupoMozoUsuarioNom(nomMozo).stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }

    // rolId=4: TRANSPORTISTA ordres assignades a ell
    public List<OrdreDTO> findByTransportista(String nom) {
        return ordreRepository.findByTransportistaNom(nom).stream()
                .map(OrdreDTO::new)
                .collect(Collectors.toList());
    }

    // Buscar per identificador (detall)
    // CORRECCIÓN: Eliminado el método duplicado y añadida la anotación correcta
    @Transactional(readOnly = true)
    public Optional<OrdreDTO> findByIdentificador(String id) {
        return ordreRepository.findAll().stream()
                .filter(o -> o.getIdentificador().equals(id))
                .findFirst()
                .map(OrdreDTO::new);
    }
    
    // Crear orden
    @Transactional
    public OrdreDTO crear(OrdreCreateDTO dto) {
        Ordre o = new Ordre();
        
        // Datos básicos
        o.setAdreca(dto.adreca);
        o.setCiutat(dto.ciutat);
        o.setCp(dto.cp);
        o.setTelefon(dto.telefon);
        o.setPrioritat(dto.prioritat);
        o.setTemporada(dto.temporada);
        o.setPreu(BigDecimal.valueOf(dto.preu != null ? dto.preu : 0.0));
        o.setTendaDestinataria(dto.tendaDestinataria);
        
        // Estado inicial según tu frontend
        o.setEstat("ESBORRANY");
        o.setData_creacio(LocalDateTime.now());
        
        // Generar Identificador (Ejemplo: ORD-2026-XXXX)
        o.setIdentificador("ORD-" + System.currentTimeMillis() % 100000);

        // Asignar Gestor
        if (dto.gestorId != null) {
            userRepository.findById(dto.gestorId).ifPresent(o::setGestor);
        }

        // Asignar Pales seleccionados
        if (dto.paleIds != null && !dto.paleIds.isEmpty()) {
            List<Pale> palesSeleccionados = paleRepository.findAllById(dto.paleIds);
            o.setPales(palesSeleccionados);
            
            // Cambiar estado de los pales a 'reservado'
            palesSeleccionados.forEach(p -> p.setEstat("reservat"));
        }

        Ordre guardada = ordreRepository.save(o);
        return new OrdreDTO(guardada);
    }
}