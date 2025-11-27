package com.mycompany.poo_proyecto.model.facturacion;


import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.sql.Date;
import com.mycompany.poo_proyecto.model.usuario.Cajero;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante", nullable = false)
    private TipoComprobante tipoComprobante;
    @Column(name = "numero_comprobante", nullable = false)
    private String numeroComprobante;
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Column(name = "subtotal", nullable = false)
    private double subtotal;
    @Column(name = "igv", nullable = false)
    private double igv;
    @Column(name = "total", nullable = false)
    private double total;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cajero", nullable = false)
    private Cajero cajero;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    public Facturacion() {
    }

    public Facturacion(TipoComprobante tipoComprobante, String numeroComprobante, double subtotal, double igv, double total) {
        this.tipoComprobante = tipoComprobante;
        this.numeroComprobante = numeroComprobante;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
    }
    
    public int getIdFacturacion() {
        return idFacturacion;
    }
    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }
    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }
    public String getNumeroComprobante() {
        return numeroComprobante;
    }
    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }
    public Date getFecha() {
        return fecha;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public double getIgv() {
        return igv;
    }
    public void setIgv(double igv) {
        this.igv = igv;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public Cajero getCajero() {
        return cajero;
    }
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    @PrePersist
    protected void onCreate() {
        fecha = new Date(new java.util.Date().getTime());
    }
}
