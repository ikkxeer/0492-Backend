/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.GrupMozos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositori per els grupmozos
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface GrupMozosRepository extends JpaRepository<GrupMozos, Integer> {
    
    // Consulta per trobar el grup a través de la taula intermèdia
    @Query(value = "SELECT g.* FROM grupmozos g " +
                   "JOIN grupmozos_usuaris gu ON g.id_grup = gu.id_grup " +
                   "WHERE gu.id_usuari = :idUsuari", nativeQuery = true)
    Optional<GrupMozos> findByUsuariId(@Param("idUsuari") Integer idUsuari);

    // Troba un grup de mozos pel seu nom
    Optional<GrupMozos> findByNom(String nom);
}
