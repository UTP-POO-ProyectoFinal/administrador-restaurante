package com.mycompany.poo_proyecto.model.pedido;

import com.mycompany.poo_proyecto.model.reserva.Mesa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;

    // CORRECCIÓN 1: El nombre debe coincidir exactamente con la BD y usarse así en HQL
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

    // Mantenemos el int para compatibilidad con tus inserts actuales
    @Column(name = "id_mesa", nullable = false)
    private int idMesa;

    // CORRECCIÓN 2: Agregamos la relación para consultas (Solo lectura para no romper inserts)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mesa", insertable = false, updatable = false)
    private Mesa mesa;

    public Pedido() {
    }

    public Pedido(String estado, String tipo, int idCliente, int idMesa) {
        this.estado = estado;
        this.tipo = tipo;
        this.idCliente = idCliente;
        this.idMesa = idMesa;
        this.fechaHora = LocalDateTime.now();
        this.total = 0.0;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public Mesa getMesa() {
        return mesa;
    }
}
