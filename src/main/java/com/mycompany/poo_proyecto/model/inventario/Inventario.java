package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

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

    public Inventario() {
    }

    public Inventario(int stock, int stockMinimo, int stockMaximo, LocalDate fechaActualizacion) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("stockMinimo no puede ser negativo");
        }
        if (stockMaximo < stockMinimo) {
            throw new IllegalArgumentException("stockMaxmo < stockMinimo");
        }
        if (stock < 0 || stock > stockMaximo) {
            throw new IllegalArgumentException("stock fuera de rango");
        }
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.fechaActualizacion = (fechaActualizacion != null) ? fechaActualizacion : LocalDate.now();
    }

    @PrePersist
    protected void onCreate() {
        if (fechaActualizacion == null) {
            fechaActualizacion = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDate.now();
    }

    public void incrementar(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        }
        if (stock + cantidad > stockMaximo) {
            throw new IllegalArgumentException("supera stockMaximo");
        }
        stock += cantidad;
    }

    public void decrementar(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        }
        if (stock - cantidad < 0) {
            throw new IllegalArgumentException("stock no puede ser negativo");
        }
        stock -= cantidad;
    }

    public boolean isBajoMinimo() {
        return stock <= stockMinimo;
    }

    public int espacioDisponible() {
        return stockMaximo - stock;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0 || stock > stockMaximo) {
            throw new IllegalArgumentException("stock fuera de rango");
        }
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        if (stockMinimo < 0) {
            throw new IllegalArgumentException("stockMinimo no puede ser negativo");
        }
        if (stockMaximo < stockMinimo) {
            throw new IllegalArgumentException("stockMaximo < stockMinimo");
        }
        this.stockMinimo = stockMinimo;
        if (stock < stockMinimo) {
            stock = stockMinimo; 
        }
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        if (stockMaximo < stockMinimo) {
            throw new IllegalArgumentException("stockMaximo < stockMinimo");
        }
        if (stock > stockMaximo) {
            throw new IllegalArgumentException("stock actual supera nuevo maximo");
        }
        this.stockMaximo = stockMaximo;
    }

    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = (fechaActualizacion != null) ? fechaActualizacion : LocalDate.now();
    }
}
