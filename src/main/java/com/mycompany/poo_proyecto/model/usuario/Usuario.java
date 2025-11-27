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

    @Column(name = "dni", nullable = false, unique = true)
    protected int dni;

    @Column(name = "usuario", nullable = false, unique = true)
    protected String usuario;
    
    @Column(name = "nombre", nullable = true)
    protected String nombre;

    @Column(name = "apellidos", nullable = true)
    protected String apellidos;

    @Column(name = "correo", nullable = false, unique = true)
    protected String correo;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "rol", nullable = false)
    protected Roles rol;

    public Usuario() {
    }

    public Usuario(int dni, String usuario, String nombre, String apellidos, String correo, String password, Roles rol) {
        this.dni = dni;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }
    
    public Usuario(int dni, String correo, Roles rol) {
        this.dni = dni;
        this.correo = correo;
        this.rol = rol; 
    }

    public abstract boolean login(); //Esta parte aun no se desarrolla

    public abstract void logout(); //Esta parte aun no se desarrolla

    public int getIdUsuario() {
        return idUsuario;
    }
    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
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
