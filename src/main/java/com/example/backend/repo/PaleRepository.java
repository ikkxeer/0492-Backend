/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Pale;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author samui
 */


public interface PaleRepository extends JpaRepository<Pale, Integer> {
    // JpaRepository ya incluye el método .count() automáticamente
}
