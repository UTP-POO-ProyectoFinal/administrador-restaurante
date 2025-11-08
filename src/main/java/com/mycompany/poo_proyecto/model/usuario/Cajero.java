package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cajeros")
public class Cajero extends Usuario {

    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "caja_asignada", nullable = false)
    private int cajaAsignada;

    @Column(name = "ventas_diarias", nullable = false)
    private float ventasDiarias;

    public Cajero() {

    }

    public Cajero(int dni, String nombre, String apellido, String correo, String password, Roles rol, String turno, int cajaAsignada, float ventasDiarias) {
        super(dni, nombre, apellido, correo, password, rol);
        this.turno = turno;
        this.cajaAsignada = cajaAsignada;
        this.ventasDiarias = ventasDiarias;
    }
}
