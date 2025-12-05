package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
@Table(name = "compra")
public class Compra {

    public enum EstadoCompra {
        SIN_PAGAR,
        PAGADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra", nullable = false)
    private int idCompra;
    private Date fecha;
    private double total;
    private EstadoCompra estado;

    @PrePersist
    public void setFecha() {
        fecha = new Date(new java.util.Date().getTime());
    }
}
