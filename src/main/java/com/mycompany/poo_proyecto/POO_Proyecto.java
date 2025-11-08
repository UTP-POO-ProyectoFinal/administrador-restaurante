package com.mycompany.poo_proyecto;

import com.mycompany.poo_proyecto.dao.*;
import com.mycompany.poo_proyecto.model.usuario.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.inventario.Inventario;
import java.time.LocalDate;

public class POO_Proyecto {

    public static void main(String[] args) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador(
                72212225,
                "Brayan",
                "Saldarriaga Nizama",
                "brayan@company.com",
                "123456",
                Usuario.Roles.ADMINISTRADOR,
                1,
                "cajero");

        adminDAO.saveAdministrador(admin);

        CajeroDAO cajeroDAO = new CajeroDAO();
        Cajero cajero = new Cajero(
                12345678, // dni
                "Carlos", // nombre
                "Ramirez", // apellido
                "carlos.ramirez@tienda.com", // correo
                "1234pass", // password
                Usuario.Roles.CAJERO, // rol
                "Mañana", // turno
                1, // cajaAsignada
                850.75f // ventasDiarias
        );

        cajeroDAO.saveCajero(cajero);

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente1 = new Cliente(
                87654321, // dni
                "Ana", // nombre
                "Lopez", // apellido
                "ana.lopez@gmail.com", // correo
                "abcd1234", // password
                Usuario.Roles.CLIENTE, // rol
                999888777,
                "La cada de alguien",
                2
        );

        clienteDAO.saveCliente(cliente1);

        ProveedorDAO proveedorDAO = new ProveedorDAO();
        Proveedor proveedor1 = new Proveedor(
                45678912, // dni
                "Luis", // nombre
                "Martinez", // apellido
                "luis.martinez@proveedor.com", // correo
                "provepass", // password
                Usuario.Roles.PROVEEDOR, // rol
                "Maria Sanchez", // contacto
                "Distribuidora Norte SAC", // empresa
                "20123456789"
        );

        proveedorDAO.saveProveedor(proveedor1);

        FacturacionDAO facturacionDAO = new FacturacionDAO();
        Facturacion fact1 = new Facturacion(
                Facturacion.TipoComprobante.BOLETA,
                "F001-000123",
                500.00, // subtotal
                96.00,
                590.00
        );
        
        facturacionDAO.saveFacturacion(fact1);
        
        
        InventarioDAO inventarioDAO = new InventarioDAO();
        Inventario inventario = new Inventario(
            100,               // stock
            20,                // stockMinimo
            200,               // stockMaximo
            LocalDate.now()    // fechaActualizacion
        );
        
        inventarioDAO.saveAdministrador(inventario);
        
        System.out.println("Finalmente!!!!");
    }
}
