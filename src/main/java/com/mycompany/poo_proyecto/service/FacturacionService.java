package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion.TipoComprobante;
import com.mycompany.poo_proyecto.model.facturacion.Pago;
import com.mycompany.poo_proyecto.model.usuario.Cajero;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.utils.GenericDAO;

public class FacturacionService {

    private final GenericDAO<Facturacion> facturacionDAO;
    private final GenericDAO<Pago> pagoDAO;
    private static final double TASA_IGV = 0.18;

    public FacturacionService() {
        this.facturacionDAO = new GenericDAO<>();
        this.pagoDAO = new GenericDAO<>();
    }

    //Estos metodos estan creados de esta manera, pero pueden cambiar a la hora de aplicar la API de pago, idk
    public void emitirComprobante(TipoComprobante tipo, String numeroComprobante, double subtotal, Cajero cajero, Cliente cliente) {
        double igvCalculado = subtotal * TASA_IGV;
        double totalCalculado = subtotal + igvCalculado;

        Facturacion nuevaFactura = new Facturacion(tipo, numeroComprobante, subtotal, igvCalculado, totalCalculado);
        nuevaFactura.setCajero(cajero);
        nuevaFactura.setCliente(cliente);

        facturacionDAO.saveClass(nuevaFactura);
        System.out.println("""
                            Comprobante generado exitosamente.
                            Cliente: %s
                            Total a Pagar: S/ %.2f
                            """.formatted(cliente.getNombre(),
                                totalCalculado));
    }

    public void registrarPago(double monto, String metodo, int idFacturacion) {
        Pago nuevoPago = new Pago(monto, metodo, "PENDIENTE", idFacturacion);
        pagoDAO.saveClass(nuevoPago); // INSERT
        System.out.println("Pago registrado (Pendiente de confirmación).");
    }

    public void confirmarPago(Pago pago) {
        if (pago.procesarPago()) { 
            pagoDAO.updateClass(pago);
            System.out.println("Pago APROBADO y procesado correctamente.");
            
        } else {
            System.out.println("Error: No se pudo procesar el pago (Monto inválido o datos faltantes).");
        }
    }
}
