package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {

    @Column(name = "nivel_acceso", nullable = false)
    private int nivelAcceso;

    @Column(name = "area_responsable", nullable = false)
    private String areaResponsable;

    public Administrador() {
    }

    public Administrador(int dni, String usuario, String nombre, String apellido, String correo, String password, Roles rol, int nivelAcceso, String areaResponsable) {
        super(dni, usuario, nombre, apellido, correo, password, rol);
        this.nivelAcceso = nivelAcceso;
        this.areaResponsable = areaResponsable;
    }

    public boolean gestionarUsuarios() {
        return true;
    }

    public List<Object> generarReportes() {
        return new ArrayList<>();
    }

    public boolean gestionarInventario() {
        return true;
    }

    public boolean registrarProveedor() {
        return true;
    }

    public boolean registrarProducto() {
        return true;
    }

    public boolean registrarPlatillo() {
        return true;
    }

    public boolean registrarCompra() {
        return true;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public String getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    @Override
    public boolean login() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
