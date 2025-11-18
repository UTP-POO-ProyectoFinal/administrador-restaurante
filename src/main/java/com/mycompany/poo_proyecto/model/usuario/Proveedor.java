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

    public Proveedor() {
    }

    public Proveedor(int dni, String correo, Roles rol,
                     String contacto, String empresa, String ruc) {
        super(dni, correo, rol);
        this.contacto = contacto;
        this.empresa = empresa;
        this.ruc = ruc;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getRuc() {
        return ruc;
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

