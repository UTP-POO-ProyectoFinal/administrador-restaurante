package com.mycompany.poo_proyecto.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "cajeros")
public class Cajero extends Usuario {

    @Column(name = "turno", nullable = false)
    private String turno;

    @Column(name = "caja_asignada", nullable = false)
    private int cajaAsignada;

    @Column(name = "ventas_diarias", nullable = false)
    private float ventasDiarias;
    @OneToMany(mappedBy = "cajero", fetch = FetchType.LAZY)
    private List<Facturacion> facturaciones = new ArrayList();

    public Cajero() {}

    public Cajero(int dni, String nombre, String apellido, String correo, String password, Roles rol, String turno, int cajaAsignada, float ventasDiarias) {
        super(dni, nombre, apellido, correo, password, rol);
        this.turno = turno;
        this.cajaAsignada = cajaAsignada;
        this.ventasDiarias = ventasDiarias;
    }

    public String getTurno() {
        return turno;
    }

    public int getCajaAsignada() {
        return cajaAsignada;
    }

    public float getVentasDiarias() {
        return ventasDiarias;
    }

    public List<Facturacion> getFacturaciones() {
        return facturaciones;
    }

    public void setFacturaciones(List<Facturacion> facturaciones) {
        this.facturaciones = facturaciones;
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

