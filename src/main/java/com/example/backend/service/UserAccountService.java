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
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    
    // Eliminar usuario por ID
    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Crear usuari
    public UserAccount createUser(UserAccount user) {
        // Posem l'estat a true per defecte al crear l'usuari, es guarda l'estat
        user.setEstat(true); 
        // Fem l'insert i retornem l'usuari creat
        return userRepository.save(user);
    }
    
    // Actualizar usuari
    public UserAccount updateUser(Integer id, UserAccount datosNuevos) {
        // Busquem l'usuari per l'id
        return userRepository.findById(id).map(userExistente -> {

            // Actualitzem l'usuari ja existent amb les dades del frontend
            userExistente.setNom(datosNuevos.getNom());
            userExistente.setEmail(datosNuevos.getEmail());
            userExistente.setTelefon(datosNuevos.getTelefon());
            userExistente.setDepartament(datosNuevos.getDepartament());
            userExistente.setUbicacio(datosNuevos.getUbicacio());
            userExistente.setIdRol(datosNuevos.getIdRol());

            // Actualitzem l'usuari
            return userRepository.save(userExistente);

        }).orElse(null); // Si no troba l'id retorna null
    }
}