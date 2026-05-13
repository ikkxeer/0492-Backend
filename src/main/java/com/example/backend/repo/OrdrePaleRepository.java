package com.example.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrdrePaleRepository extends JpaRepository<Object, Integer> {
    // Usamos Object o la entidad que represente 'ordre_pale'

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ordre_pale WHERE id_pale IN (SELECT id_pale FROM pale WHERE id_grup_pales = ?1)", nativeQuery = true)
    void deleteByGrupPalesId(Integer idGrup);
}