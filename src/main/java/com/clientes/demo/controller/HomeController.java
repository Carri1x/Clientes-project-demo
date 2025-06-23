package com.clientes.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.demo.model.Cliente;
import com.clientes.demo.service.ClienteService;

@RestController
public class HomeController {

    private ClienteService clienteService;

    @GetMapping("/")
    public List<Cliente> getAllClients() {
        return clienteService.getAllClients();
    }

}
