/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.GrupMozos;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositori per els grupmozos
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface GrupMozosRepository extends JpaRepository<GrupMozos, Integer> {
}

