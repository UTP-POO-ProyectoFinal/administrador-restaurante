package com.mycompany.poo_proyecto.model.pedido;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double subtotal;

    @Column(length = 255)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_platillo", nullable = false)
    private Platillo platillo;
    public DetallePedido() {
    }

    public DetallePedido(int cantidad, String observaciones, int idPedido, int idPlatillo) {
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.idPedido = idPedido;
        this.idPlatillo = idPlatillo;
    }

    public double calcularSubtotal() {
        return subtotal;
    }

    public void modificarCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void agregarObservacion(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }
}
