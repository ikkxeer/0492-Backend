/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Ordre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositori per les ordres
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface OrdreRepository extends JpaRepository<Ordre, Integer> {

    // Sacamos las órdenes filtrando por el nombre del gestor asignado
    @Query(value = "SELECT o.* FROM ordre o " +
                   "INNER JOIN usuari u ON o.id_gestor = u.id " +
                   "WHERE u.nom = :nom",
           nativeQuery = true)
    List<Ordre> findByGestorNom(@Param("nom") String nom);

    // Buscamos órdenes asociadas a un mozo navegando a través de los grupos y la tabla intermedia.
    @Query(value = "SELECT o.* FROM ordre o " +
                   "INNER JOIN grupmozos gm ON o.id_grup_mozos = gm.id_grup " +
                   "INNER JOIN grupmozos_usuaris gmu ON gm.id_grup = gmu.id_grup " +
                   "INNER JOIN usuari u ON gmu.id_usuari = u.id " +
                   "WHERE u.nom = :nomMozo",
           nativeQuery = true)
    List<Ordre> findByGrupoMozoUsuarioNom(@Param("nomMozo") String nomMozo);

    // Buscamos órdenes que tiene asignadas un transportista específico por su nombre
    @Query(value = "SELECT o.* FROM ordre o " +
                   "INNER JOIN usuari u ON o.id_transportista = u.id " +
                   "WHERE u.nom = :nom",
           nativeQuery = true)
    List<Ordre> findByTransportistaNom(@Param("nom") String nom);
    
    // Buscamos por una orden con los pales incluidos (todo junto)
    @Query("SELECT o FROM Ordre o LEFT JOIN FETCH o.pales WHERE o.identificador = :id")
    Optional<Ordre> findByIdentificadorWithPales(@Param("id") String id);
}
