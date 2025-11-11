package com.mycompany.poo_proyecto;

import com.mycompany.poo_proyecto.dao.*;
import com.mycompany.poo_proyecto.model.usuario.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.inventario.Inventario;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import java.time.LocalDate;
import java.util.*;

public class POO_Proyecto {

    public static void main(String[] args) {
        GenericDAO<Administrador> adminDAO = new GenericDAO<>();
        Administrador admin = new Administrador(
                72212225,
                "Brayan",
                "Saldarriaga Nizama",
                "brayan@company.com",
                "123456",
                Usuario.Roles.ADMINISTRADOR,
                1,
                "cajero");

        adminDAO.saveClass(admin);

        GenericDAO<Cajero> cajeroDAO = new GenericDAO<>();
        Cajero cajero = new Cajero(
                12345678, // dni
                "Carlos", // nombre
                "Ramirez", // apellido
                "carlos.ramirez@tienda.com", // correo
                "1234pass", // password
                Usuario.Roles.CAJERO, // enum
                "Mañana", // turno
                1, // cajaAsignada
                850.75f // ventasDiarias
        );

        cajeroDAO.saveClass(cajero);

        GenericDAO<Cliente> clienteDAO = new GenericDAO();
        List<Pedido> historialVacio = new ArrayList();
        Cliente cliente1 = new Cliente(
                87654321, // dni
                "Ana", // nombre
                "Lopez", // apellido
                "ana.lopez@gmail.com", // correo
                "abcd1234", // password
                Usuario.Roles.CLIENTE, // enum
                999888777,
                "La casa de alguien",
                historialVacio
                
        );

        clienteDAO.saveClass(cliente1);

        GenericDAO<Proveedor> proveedorDAO = new GenericDAO();
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

        proveedorDAO.saveClass(proveedor1);

        GenericDAO<Facturacion> facturacionDAO = new GenericDAO();
        Facturacion fact1 = new Facturacion(
                Facturacion.TipoComprobante.BOLETA,
                "F001-000123",
                500.00, // subtotal
                96.00,
                590.00
        );
        
        fact1.setCajero(cajero);
        fact1.setCliente(cliente1);
        facturacionDAO.saveClass(fact1);
        
        
        GenericDAO<Inventario> inventarioDAO = new GenericDAO();
        Inventario inventario = new Inventario(
            100,               // stock
            20,                // stockMinimo
            200,               // stockMaximo
            LocalDate.now()    // fechaActualizacion
        );
        
        inventarioDAO.saveClass(inventario);
        
        System.out.println("Finalmente!!!!");
    }
}
