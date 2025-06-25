package com.clientes.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clientes.demo.model.User;
import com.clientes.demo.repository.IUserRepository;

@Service
public class UserService {

    private IUserRepository userRepository;
    //Encripta las contraseñas
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        //Fácil hashea la contraseña antes de guardar el usuario FUNCIONA
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        if(userRepository.existsById(id)){
            return userRepository.findById(id);
        }
        return Optional.empty();
    }
    /**
     * Comprueba si la contraseña es igual a la que se guardó en la base de datos
     * con el usuario, para poder hacer login
     * @param rawPassword
     * @param encodedPassword
     * @return boolean, si es igual la contraseña devuelve true y el usuario puede logearse
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
