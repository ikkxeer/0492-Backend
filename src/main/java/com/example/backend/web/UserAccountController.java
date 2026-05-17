/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.UserAccount;
import com.example.backend.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador pels Usuaris
 * 
 * /api/users
 *  - /search: Buscar usuari per mail i retornar-ho
 *  - /users: Retorna el total d'usuaris
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuaris")
public class UserAccountController {

    // Atributs de la classe
    @Autowired
    private UserAccountService userService;

    // Endpoint per obtenir tots els usuaris: GET /api/usuaris
    @GetMapping
    public List<UserAccount> getAll() {
        return userService.getAllUsers();
    }
    
    // Endpoint per obtenir un usuari específic per ID: GET /api/usuaris/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per eliminar un usuari: DELETE /api/usuaris/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            // Retornem 204 si s'ha eliminat correctament
            return ResponseEntity.noContent().build(); 
        } else {
            // Retornem 404 si no existia l'usuari
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint per crear un usuari: POST /api/usuaris
    @PostMapping
    public ResponseEntity<UserAccount> create(@RequestBody UserAccount user) {
        UserAccount nuevoUsuario = userService.createUser(user);
        // Retornem 201 i l'usuari creat
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
    
    // Endpoint per actualitzar un usuari: PUT /api/usuaris/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> update(@PathVariable Integer id, @RequestBody UserAccount user) {
        UserAccount actualizado = userService.updateUser(id, user);

        if (actualizado != null) {
            // Retornem 200 amb l'usuari actualitzat
            return ResponseEntity.ok(actualizado);
        } else {
            // Retornem 404 si no s'ha trobat
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint per restablir la contrasenya d'un usuari: POST /api/usuaris/{id}/reset-password
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable Integer id, @RequestBody java.util.Map<String, String> body) {
        String contrasenya = body.get("contrasenya");
        if (contrasenya == null || contrasenya.isEmpty()) {
            return ResponseEntity.badRequest().body("Contrasenya is required");
        }
        
        boolean success = userService.resetPassword(id, contrasenya);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
