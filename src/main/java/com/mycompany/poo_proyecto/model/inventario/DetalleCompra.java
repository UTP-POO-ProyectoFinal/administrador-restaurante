package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;
    private int cantidad;
    @Column(name = "precio_unitario")
    private double precioUnitario;
    private double subtotal;

}
