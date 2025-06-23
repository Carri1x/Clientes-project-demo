package com.clientes.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "clientes") // Nombre de la tabla en la base de datos
public class Cliente {
    //Como en la base de datos el campo id es tipo SERIAL, no es necesario definirlo como @Id y @GeneratedValue
    //Si se quisiera definirlo como @Id y @GeneratedValue, se podría hacer de la siguiente manera:
    // @Id o @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Cliente(Long id,String nickname, String nombre, String apellido, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "\nid=" + id + 
        "\n, nombre=" + nombre +
        "\n, apellido=" + apellido +
        "\n, email=" + email +
        "\n, telefono=" + telefono;
    }
    
}
