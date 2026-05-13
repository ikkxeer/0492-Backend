/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.GrupPales;
import com.example.backend.repo.GrupPalesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service per els gruppales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class GrupPalesService {

    @Autowired
    private GrupPalesRepository grupPalesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    // Retorna tots els grups de pales
    public List<GrupPales> findAll() {
        return grupPalesRepository.findAll();
    }

    // Troba un pale segons l'ID
    public Optional<GrupPales> findById(Integer id) {
        return grupPalesRepository.findById(id);
    }

    // Guarda un grup de pales amb altre passat per parametre
    public GrupPales save(GrupPales grup) {
        return grupPalesRepository.save(grup);
    }

    @Transactional
    public void delete(Integer id) {
        String sql = "DELETE FROM ordre_pale WHERE id_pale IN (SELECT id_pale FROM pale WHERE id_grup_pales = :idGrup)";
        entityManager.createNativeQuery(sql)
                .setParameter("idGrup", id)
                .executeUpdate();
        grupPalesRepository.deleteById(id);
    }
}