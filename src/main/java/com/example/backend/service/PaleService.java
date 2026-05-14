/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.GrupPales;
import com.example.backend.domain.Ordre;
import com.example.backend.domain.Pale;
import com.example.backend.repo.GrupPalesRepository;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.repo.PaleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service per els pales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class PaleService {

    @Autowired
    private PaleRepository paleRepository;
    
    @Autowired
    private GrupPalesRepository grupPalesRepository;

    
    @Autowired
    private OrdreRepository ordreRepository;

    // Devolver numero total de pales
    public long getTotalPales() {
        return paleRepository.count();
    }
    
    // Obtener todos los palés
    public List<Pale> findAllPales() {
        return paleRepository.findAll();
    }

    // Guardar (sirve para crear nuevo o actualizar existente)
    public Pale savePale(Pale pale) {
        return paleRepository.save(pale);
    }

    // Buscar por ID
    public Optional<Pale> findPaleById(Integer id) {
        return paleRepository.findById(id);
    }
    
    // Retornar total de pales segons estat
    public long countByEstado(String estado) {
        return paleRepository.countByEstat(estado);
    }

    @Transactional
    public void deletePale(Integer id) {
        // 1. Borrar asociaciones con órdenes (LA CLAVE DEL ERROR)
        ordreRepository.deleteAssociationsByPaleId(id);

        // 2. Sincronizar para que MySQL sepa que la tabla intermedia está limpia
        paleRepository.flush();

        // 3. Gestionar la desvinculación del grupo antes de borrar
        paleRepository.findById(id).ifPresent(pale -> {
            GrupPales grup = pale.getGrupPales();
            if (grup != null) {
                grup.getPales().remove(pale);
                grupPalesRepository.save(grup);
            }
            // 4. Borrar el palé físico
            paleRepository.delete(pale);
        });
    }

}
