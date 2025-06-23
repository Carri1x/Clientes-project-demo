package com.clientes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.demo.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

}
