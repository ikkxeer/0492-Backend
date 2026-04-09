/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.Client;
import com.example.backend.service.ClientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador pels clients
 * 
 * /api/client
 *  - /total: retorna el total de clients
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientController {
    // Atributs de la classe
    @Autowired
    private ClientService clientService;
    
    // Endpoint para obtener el total: GET /api/client/total
    @GetMapping("/total")
    public long countAll() {
        return clientService.getTotalClient();
    }
    
    // Endpoint per obtenir tots els clients: GET /api/client
    @GetMapping
    public List<Client> getAll() {
        return clientService.findAllClients();
    }
    
    // Endpoint per crear un client: POST /api/client
    @PostMapping
    public Client create(@RequestBody Client pale) {
        return clientService.saveClient(pale);
    }
    
    // Endpoint per editar un client: PUT /api/client/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client clientDetails) {
        return clientService.findClientById(id).map(clientExistent -> {

            // Actualitzem els camps del client que hem rebut
            clientExistent.setNom(clientDetails.getNom());
            clientExistent.setTelefon(clientDetails.getTelefon());

            // Actualitzem el client
            Client updated = clientService.saveClient(clientExistent);

            // Retornem la resposta
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per eliminar un client: DELETE /api/client/{id} 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // Si el pale existeix
        if (clientService.findClientById(id).isPresent()) {
            // Eliminem el pale
            clientService.deleteClient(id);
            
            // Retornem la resposta
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
