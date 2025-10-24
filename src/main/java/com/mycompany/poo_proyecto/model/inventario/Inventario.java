package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInventario;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "stock_minimo", nullable = false)
    private int stockMinimo;

    @Column(name = "stock_maximo", nullable = false)
    private int stockMaximo;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDate fechaActualizacion;

    // Constructor vacío (requerido por JPA)
    public Inventario() {
    }

    // Constructor con parámetros
    public Inventario(int stock, int stockMinimo, int stockMaximo, LocalDate fechaActualizacion) {
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.fechaActualizacion = fechaActualizacion;
    }

}
