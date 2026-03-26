/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.UserAccount;
import com.example.backend.repo.UserRepository; // Ajustado a tu paquete 'repo'
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service per els usuaris
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class UserAccountService {

    @Autowired
    private UserRepository userRepository;

    // Método para listar todos
    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    // Devolver usuario por email
    public Optional<UserAccount> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}