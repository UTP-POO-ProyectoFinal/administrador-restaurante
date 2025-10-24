package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
abstract public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    protected int idUsuario;
    
    @Column(name = "dni", nullable = false)
    protected int dni;
    protected String nombre;
    protected String apellido;
    @Column(name = "correo", nullable = false)
    protected String correo;
    @Column(name = "contraseña", nullable = false)
    protected String password;
    protected String rol;
    
    public Usuario() {
    }
    
    public Usuario(int dni, String nombre, String apellido, String correo, String password, String rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }
}
