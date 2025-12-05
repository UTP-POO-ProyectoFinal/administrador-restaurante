package com.mycompany.poo_proyecto.model.pedido;

import jakarta.persistence.*;

@Entity
@Table(name = "platillo")
public class Platillo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_platillo")
    private int idPlatillo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private String categoria;

    @Column(name = "tiempo_preparacion", nullable = false)
    private int tiempoPreparacion;

    @Column(nullable = false)
    private boolean disponible;

    @OneToMany(mappedBy = "platillo", fetch = FetchType.LAZY)
    private List<DetallePedido> detalles = new ArrayList<>();

    public Platillo() {
    }

    public Platillo(String nombre, String descripcion, double precio, String categoria, int tiempoPreparacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.tiempoPreparacion = tiempoPreparacion;
        this.disponible = true;
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
