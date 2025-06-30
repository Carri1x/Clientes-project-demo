package com.clientes.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.demo.model.User;
import com.clientes.demo.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    //Perfecto funciona perfectamentes
    /*Y si existe devuelve exactamente lo que retorno return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userInvalid);
        aun así me gustaría saber para que puedo usar ese HttpStatus y el usuario invalido no se si es buena prácica*/
    @PostMapping("/registrar")
    public ResponseEntity<User> registrar(@RequestBody User user){
        /*
        Si el email no existe tiene puede crearse en la base de datos
        */
        if(!userService.emailExists(user.getEmail())){
            return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.saveUser(user)
            );
        }
        //Si existe devuelve un HttpStatus.UNAUTHORIZED con un usuario inválido
        User userInvalid = new User();
        userInvalid.setNombre("Usuario "+ user.getNombre()+" ya existe");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(userInvalid);
        /*
         * 409 Conflict es el código más apropiado cuando intentas registrar un usuario y el email ya existe.
            401 Unauthorized se usa cuando el usuario no está autenticado, no para conflictos de datos.
         */
    }

    //Funciona correctamente
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginData){
        String email = loginData.get("email");
        String password = loginData.get("password");
        Optional<User> userOpt = userService.findByEmail(email); 
        if(userOpt.isPresent() && userService.checkPassword(password, userOpt.get().getPassword())){
            //Puede hacerse esto tambien return ResponseEntity.ok(userOpt.get())
            //Aquí debería de devolver un JWT, pero para pruebas esto está bien
            return ResponseEntity.ok().body(userOpt.get());
        }
        /*Esto devuelve HttpStatus.UNAUTHORIZED = "El cliente no está autenticado 
        (no ha enviado o ha enviado mal un token o credenciales)"
            El .build() Construye un ResponseEntity sin cuerpo, es decir,
            devuelve una respuesta vacía pero con el código 401.*/
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/usuarios")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    
    
}
