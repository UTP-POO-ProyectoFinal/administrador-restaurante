package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "historial_compras", nullable = false)
    private int historialCompras;

    public Cliente() {

    }

    public Cliente(int dni, String nombre, String apellido, String correo, String password, String rol, String telefono, String direccion, int historialCompras) {
        super(dni, nombre, apellido, correo, password, rol);
        this.telefono = telefono;
        this.direccion = direccion;
        this.historialCompras = historialCompras;
    }
}
