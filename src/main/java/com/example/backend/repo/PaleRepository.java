/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Pale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositori per els pales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface PaleRepository extends JpaRepository<Pale, Integer> {
    // Aixó genera, SELECT COUNT(*) FROM pale WHERE estat = ?
    long countByEstat(String estat);
    
    // Query per trobar totes les pales que perteneixen a un grup de pales
    @Query("SELECT p FROM Pale p WHERE p.grupPales.id_grup_pales = :idGrup")
    List<Pale> findByIdGrupPales(@Param("idGrup") Integer idGrup);
}
