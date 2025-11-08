package com.mycompany.poo_proyecto.model.facturacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "facturacion")
public class Facturacion {

    public enum TipoComprobante {
        BOLETA, FACTURA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facturacion", nullable = false)
    private int idFacturacion;
    @Column(name = "tipo_comprobante", nullable = false)
    private TipoComprobante tipoComprobante;
    @Column(name = "numero_comprobante", nullable = false)
    private String numeroComprobante;
    private Date fecha;
    private double subtotal;
    private double igv;
    private double total;

    public Facturacion() {
    }

    public Facturacion(TipoComprobante tipoComprobante, String numeroComprobante, double subtotal, double igv, double total) {
        this.tipoComprobante = tipoComprobante;
        this.numeroComprobante = numeroComprobante;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
    }

    @PrePersist
    protected void onCreate() {
        fecha = new Date(new java.util.Date().getTime());
    }
}
