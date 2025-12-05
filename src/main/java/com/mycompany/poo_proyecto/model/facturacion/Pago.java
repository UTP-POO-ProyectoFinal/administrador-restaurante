package com.mycompany.poo_proyecto.model.facturacion;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private int idPago;

    @Column(name = "monto", nullable = false)
    private double monto;

    @Column(name = "metodo", nullable = false)
    private String metodo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "numero_transaccion", nullable = false)
    private String numeroTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_facturacion", nullable = false)
    private Facturacion facturacion;

    
    public Pago() {
        this.fecha = LocalDateTime.now(); 
        this.numeroTransaccion = generarNumeroTransaccion();
    }

    public Pago(double monto, String metodo, String estado, int idFacturacion) {
        this.monto = monto;
        this.metodo = metodo;
        this.estado = estado;
        this.idFacturacion = idFacturacion;
        this.fecha = LocalDateTime.now();
        this.numeroTransaccion = generarNumeroTransaccion();
    }


    public boolean procesarPago() {

        if (validarTransaccion()) {
            this.estado = "Procesado";
            return true;
        }
        return false;
    }

    public boolean validarTransaccion() {

        return this.monto > 0 && this.metodo != null && !this.metodo.isEmpty();
    }

    public int getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public int getIdFacturacion() {
        return idFacturacion;
    }

    private String generarNumeroTransaccion() {
        return "TX-" + System.currentTimeMillis();
    }
}

