package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Usuario {
    public enum Roles {
        ADMINISTRADOR, CAJERO, CLIENTE, PROVEEDOR, USUARIO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    protected int idUsuario;

    @Column(name = "dni", nullable = false)
    protected int dni;

    @Column(name = "nombre", nullable = false)
    protected String nombre;

    @Column(name = "apellido", nullable = false)
    protected String apellido;

    @Column(name = "correo", nullable = false)
    protected String correo;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "rol", nullable = false)
    protected Roles rol;


    public Usuario() {
    }

    public Usuario(int dni, String nombre, String apellido, String correo, String password, Roles rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }


    public abstract boolean login(); //Esta parte aun no se desarrolla

    public abstract void logout(); //Esta parte aun no se desarrolla

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRol() {
        return rol;
    }
}
