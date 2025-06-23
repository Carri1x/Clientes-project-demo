package com.clientes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.demo.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{
    // No es necesario agregar métodos adicionales, JpaRepository ya proporciona
    // métodos CRUD básicos como save, findById, findAll, deleteById, etc.
    // Si se necesitan consultas personalizadas, se pueden agregar aquí.

}
