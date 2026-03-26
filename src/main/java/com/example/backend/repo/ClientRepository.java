/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.backend.repo;

import com.example.backend.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author samui
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {
}

