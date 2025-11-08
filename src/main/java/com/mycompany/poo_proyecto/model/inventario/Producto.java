package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", nullable = false)
    private int idProducto;
    private String nombre;
    private double precio;
    private int stock;
}
