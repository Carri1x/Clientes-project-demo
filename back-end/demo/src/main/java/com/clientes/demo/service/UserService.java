package com.clientes.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.clientes.demo.model.User;
import com.clientes.demo.repository.IUserRepository;

@Service
public class UserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Optional<User> getUserById(Long id){
        if(userRepository.existsById(id)){
            return userRepository.findById(id);
        }
        return Optional.empty();
    }
}
