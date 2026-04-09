/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Ordre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositori per les ordres
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface OrdreRepository extends JpaRepository<Ordre, Integer> {
    List<Ordre> findByGestorNom(String nom);
}
