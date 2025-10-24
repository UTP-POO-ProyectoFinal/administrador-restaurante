package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
public class Proveedor extends Usuario {

    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "empresa", nullable = false)
    private String empresa;

    @Column(name = "ruc", nullable = false)
    private String ruc;

    public Proveedor(int dni, String nombre, String apellido, String correo, String password, String rol, String contacto, String empresa, String ruc) {
        super(dni, nombre, apellido, correo, password, rol);
        this.contacto = contacto;
        this.empresa = empresa;
        this.ruc = ruc;
    }
}
