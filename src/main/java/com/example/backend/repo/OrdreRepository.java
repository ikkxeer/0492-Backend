/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Ordre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositori per les ordres
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface OrdreRepository extends JpaRepository<Ordre, Integer> {
    List<Ordre> findByGestorNom(String nom);
    
    @Query(value = "SELECT o.* FROM ordre o " +
                   "INNER JOIN grupmozos gm ON o.id_grup_mozos = gm.id_grup " +
                   "INNER JOIN grupmozos_usuaris gmu ON gm.id_grup = gmu.id_grup " +
                   "INNER JOIN usuari u ON gmu.id_usuari = u.id " +
                   "WHERE u.nom = :nomMozo", 
           nativeQuery = true)
    List<Ordre> findByGrupoMozoUsuarioNom(@Param("nomMozo") String nomMozo);
}
