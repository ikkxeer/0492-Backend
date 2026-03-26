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
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/api/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userService;

    // GET - Obtenir tots els usuaris
    @GetMapping
    public List<UserAccount> getAll() {
        return userService.getAllUsers();
    }
    
    /**
     * GET - Obtener usuario por email
     * 
     * ResponseEntity nos permite elegir el codigo de estado
     * RequestParam atrapa lo que pongas en la URL despues del ?
     * 
     * @param email Email a buscar
     * @return Usuari complert
     */
    @GetMapping("/search")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                // Si encuentra el usuario, devuelve el usuario completo
                .map(user -> ResponseEntity.ok(user))
                // Si no encuentra el usuario, devuelve 404
                .orElse(ResponseEntity.notFound().build());
    }
}
