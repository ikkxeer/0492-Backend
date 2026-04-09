/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.Client;
import com.example.backend.repo.ClientRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per els clients
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    // Devolver numero total de grup de mozos
    public long getTotalClient() {
        return clientRepository.count();
    }
    
    // Obtener todos los palés
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    // Guardar (sirve para crear nuevo o actualizar existente)
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Buscar por ID
    public Optional<Client> findClientById(Integer id) {
        return clientRepository.findById(id);
    }

    // Eliminar por ID
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
    
}
