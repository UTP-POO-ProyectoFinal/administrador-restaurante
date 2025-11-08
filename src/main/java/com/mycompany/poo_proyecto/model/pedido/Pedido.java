/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo_proyecto.model.pedido;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "id_mesa", nullable = false)
    private int idMesa;

    public Pedido() {
    }

    public Pedido(String estado, String tipo, int idCliente, int idMesa) {
        this.estado = estado;
        this.tipo = tipo;
        this.idCliente = idCliente;
        this.idMesa = idMesa;
        this.fechaHora = LocalDateTime.now();
    }

    public void cambiarEstado(String estado) {
        this.estado = estado;
    }

    public boolean cancelarPedido() {
        if (!this.estado.equalsIgnoreCase("cancelado")) {
            this.estado = "cancelado";
            return true;
        }
        return false;
    }

    public double calcularTotal() {
        return total;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public String getTipo() {
        return tipo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdMesa() {
        return idMesa;
    }
}
