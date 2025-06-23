package com.clientes.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "clientes") // Nombre de la tabla en la base de datos
public class Cliente {
    //Necesito especificar con @Id o @GeneratedValue(strategy = GenerationType.IDENTITY)
    //ya que en la base de datos el id es SERIAL y necesita saber springboot que es 
    //autogenerado el id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    public Cliente(){
        
    }

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

    

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
