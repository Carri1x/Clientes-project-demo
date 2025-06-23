package com.clientes.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.demo.model.User;
import com.clientes.demo.service.UserService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> login(@PathVariable User user){
        Optional<User> u = userService.getUserById(user.getId());
        /*Si el usuario no existe (Es un optional.empty) mandamos en el body el usuario que 
        que ha fallado y si no falla pues mandamos el usario correcto el cual si esta
        en la base de datos
        */
        if(u.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            u.get()
        );
    }
    
    
}
