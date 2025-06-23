package com.clientes.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clientes.demo.model.Cliente;
import com.clientes.demo.repository.IClienteRepository;

@Service    
public class ClienteService {
    
    private final IClienteRepository clienteRepository;

    public ClienteService(@Autowired IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getAllClients() {
        return clienteRepository.findAll();
    }

    public Cliente getClientById(Long id) {
        try {
            return clienteRepository.findById(id).orElseThrow(
                () -> new Exception("Cliente no encontrado con id: " + id)
            );
        } catch (Exception e) {
            // Manejo de la excepción, por ejemplo, loguear el error
            System.out.println("En ClienteService.getClientById: " + e.getMessage());
            return null; // O lanzar una excepción personalizada
        }
        
    }

    public Cliente createClient(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateNicknameClient(String nickname, Long id) {
        //Al hacer una excepción y controlarla en el servidor no hace falta comprobar nada
        //Aun así sería mejor práctica trabajar con Optionals...
        Cliente cliente = getClientById(id);
        return clienteRepository.save(new Cliente(
            cliente.getId(),
            nickname,
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getEmail(),
            cliente.getTelefono()
        ));
    }
    /**
     * Busca si existe el cliente si lo encuentra lo elimina si no lo encuentra devuelve false y no hace nada
     * 
     * Este método podría hacerse lanzando excepciones pero por ahora he decidido
     * que devuelva un booleano
     * 
     * @param id
     * @return
     */
    public boolean deleteClient(Long id){
        if(!clienteRepository.existsById(id)){
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }
}

