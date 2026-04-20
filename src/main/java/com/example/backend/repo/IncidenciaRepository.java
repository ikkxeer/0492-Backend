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
 *
 * @author samui
 */
@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
    
    @Query("SELECT i FROM Incidencia i WHERE i.reportatPer = :userId")
    List<Incidencia> findByReportatPer(@Param("userId") Integer userId);

    @Query("SELECT i FROM Incidencia i WHERE i.assignatA = :userId")
    List<Incidencia> findByAssignatA(@Param("userId") Integer userId);
}