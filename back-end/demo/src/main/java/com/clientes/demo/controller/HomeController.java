package com.clientes.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.demo.model.Cliente;
import com.clientes.demo.service.ClienteService;

@RestController
@RequestMapping("/home")
public class HomeController {

    private ClienteService clienteService;

    public HomeController(ClienteService clienteService){
        this.clienteService = clienteService;
    }
    //Comprobado, funciona correctamente
    @GetMapping("/clientes")
    public List<Cliente> getAllClients() {
        // Devuelve Json tan sencillo por simplemente poner @RestController
        // Si hubiesemos puesto @Controller literalmente hubiese enviado esta api el
        // objeto en si
        return clienteService.getAllClients();
    }
    //Comprobado, funciona correctamente
    @GetMapping("cliente/{id}")
    public Cliente getClientById(@PathVariable Long id) {
        return clienteService.getClientById(id);
    }

    //Comprobado, funciona correctamente
    @PostMapping("/add")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevoCliente) {
        Cliente clienteGuardado = clienteService.createClient(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    //Comprobado, funciona correctamente
    @PatchMapping("cliente/{id}/nickname/{nickname}")
    public ResponseEntity<Cliente> updateNicknameCliente(@PathVariable String nickname, @PathVariable Long id){
        //El HttpStatus se cambia a ok para una actualización 
        /*
        Usa @PatchMapping para cambios parciales.
        Usa @PutMapping para reemplazos completos.
        Usa @PostMapping solo para crear nuevos recursos.
        */
        return ResponseEntity.status(HttpStatus.OK).body(
                clienteService.updateNicknameClient(nickname, id)
            );
    }

    @DeleteMapping("delete/{id}")
    public boolean eliminarCliente(@PathVariable Long id){
        return clienteService.deleteClient(id);
    }

}
