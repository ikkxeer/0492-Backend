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
    
    /**
     * DELETE para eliminar usurios segun el id
     * 
     * @PathVariable es para pillar lo que pasa entre {} y usarlo como id
     * 
     * @param id Id usuario a eliminar
     * @return Usuari eliminat
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            // 204 No Content es el código estándar cuando algo se borra con éxito y no devuelves nada
            return ResponseEntity.noContent().build(); 
        } else {
            // 404 si el ID no existía
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST per a crear un usuari
     * 
     * @RequestBody per a obtenir les dades que arriben desde el cos
     * 
     * @param user Usuari a crear
     * @return Usuari creat
     */
    @PostMapping
    public ResponseEntity<UserAccount> create(@RequestBody UserAccount user) {
        UserAccount nuevoUsuario = userService.createUser(user);
        // Retornem 201 i l'usuari creat
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
    
    /**
     * PUT per a fer update a un usuari
     * 
     * @param id Id usuari a modificar
     * @param datosNuevos Usuari amb les noves dades
     * @return 
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> update(@PathVariable Integer id, @RequestBody UserAccount user) {
        // Fem l'update i guardem el resultat a una variable
        UserAccount actualizado = userService.updateUser(id, user);

        // Comprovem si ho ha actualitzat
        if (actualizado != null) {
            // Si existeix retornem 201 com a trobat
            return ResponseEntity.ok(actualizado);
        } else {
            // Si no existeix, retornem 404
            return ResponseEntity.notFound().build();
        }
    }
}
