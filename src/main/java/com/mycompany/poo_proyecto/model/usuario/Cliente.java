package com.mycompany.poo_proyecto.model.usuario;

import com.mycompany.poo_proyecto.model.pedido.Pedido;
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
    private List<Pedido> historialCompras = new ArrayList();

    public Cliente() {

    }

    public Cliente(int dni, String nombre, String apellido, String correo, String password, Roles rol, int telefono, String direccion, List<Pedido> historialCompras) {
        super(dni, nombre, apellido, correo, password, rol);
        this.telefono = telefono;
        this.direccion = direccion;
        this.historialCompras = historialCompras;
    }
    
    public List<Pedido> getHistorialCompras() {
        return historialCompras;
    }
    
    public void setHistorialCompras(List<Pedido> historialCompras){
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

    @Override
    public boolean login() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
