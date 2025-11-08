package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.*;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(name = "telefono", nullable = false)
    private int telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "historial_compras", nullable = false)
    private List<Pedido> historialCompras;

    public Cliente() {

    }

    public Cliente(int dni, String nombre, String apellido, String correo, String password, Roles rol, int telefono, String direccion, List<Pedido> historialCompras) {
        super(dni, nombre, apellido, correo, password, rol);
        this.telefono = telefono;
        this.direccion = direccion;
        this.historialCompras = historialCompras;
    }
    
    public List<Pedido> getHistorialCompras() {
        this.historialCompras = historialCompras;
    }
    
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getHistorialCompras() {
        return historialCompras;
    }
}
