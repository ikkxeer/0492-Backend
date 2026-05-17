/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;
import com.example.backend.domain.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositori per les incidencies
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
    
    // Troba una incidencia reportada per un usuari en especific
    @Query("SELECT i FROM Incidencia i WHERE i.reportatPer = :userId")
    List<Incidencia> findByReportatPer(@Param("userId") Integer userId);

    // Troba una incidencia assignada a un usuari en especific
    @Query("SELECT i FROM Incidencia i WHERE i.assignatA = :userId")
    List<Incidencia> findByAssignatA(@Param("userId") Integer userId);

    // Agrupa i compta les incidències per estat
    @Query("SELECT i.estat, COUNT(i) FROM Incidencia i GROUP BY i.estat")
    List<Object[]> countByEstat();

    // Aixó genera, SELECT COUNT(*) FROM incidencia WHERE estat = ?
    long countByEstat(String estat);
}