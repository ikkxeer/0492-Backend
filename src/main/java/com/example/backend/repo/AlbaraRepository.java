/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;
import com.example.backend.domain.Albara;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
 * Repositori per als albarans.
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public interface AlbaraRepository extends JpaRepository<Albara, Integer> {

    /** Cerca un albarà pel seu codi únic (ex: ALB-001-746864). Usat en escanejar el QR. */
    Optional<Albara> findByCodi(String codi);

    @Query("SELECT a FROM Albara a WHERE a.ordre.id_ordre = :idOrdre")
    Optional<Albara> findByOrdreId(@Param("idOrdre") Integer idOrdre);
}
