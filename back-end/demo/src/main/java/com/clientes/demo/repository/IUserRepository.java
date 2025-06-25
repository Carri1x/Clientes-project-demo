package com.clientes.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.demo.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    /*IMPORTANTE 
     * Gracias a la convención de nombres haciendo aquí un método abstracto
     * puede saber JpaRepository como hacer la consulta que deseas hacer 
     * para recoger información de la base de datos!!!
     * EJEMPLO:
     */

    boolean existsByEmail(String email); // <-- Este método lo crea Spring automáticamente
    Optional<User> findByEmail(String email); // (opcional, útil para login) 
}
