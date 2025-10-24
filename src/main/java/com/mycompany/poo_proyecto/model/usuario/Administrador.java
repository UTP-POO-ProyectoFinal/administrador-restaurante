package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {
    @Column(name = "nivel_acceso", nullable = false)
    int nivelAcceso;
    @Column(name = "area_responsable", nullable = false)
    String areaResponsable;
    
    public Administrador() {
        
    }
    
    public Administrador(int dni, String nombre, String apellido, String correo, String password, String rol, int nivelAcceso, String areaResponsable) {
        super(dni, nombre, apellido, correo, password, rol);
        
        this.nivelAcceso = nivelAcceso;
        this.areaResponsable = areaResponsable;
    }
}
