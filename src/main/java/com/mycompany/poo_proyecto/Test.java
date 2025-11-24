package com.mycompany.poo_proyecto;

import com.mycompany.poo_proyecto.utils.GenericDAO;
import com.mycompany.poo_proyecto.model.usuario.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.inventario.Inventario;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import java.time.LocalDate;
import java.util.*;

public class Test {

    public void main() {
        GenericDAO<Administrador> adminDAO = new GenericDAO<>();
        Administrador admin = new Administrador(
                72212225,
                "YixSoviet",
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
                12345678,
                "Carlos",
                "Carlos_cajerito",
                "Ramirez",
                "carlos.ramirez@tienda.com",
                "1234pass",
                Usuario.Roles.CAJERO,
                "Mañana",
                1,
                850.75f
        );

        cajeroDAO.saveClass(cajero);

        GenericDAO<Cliente> clienteDAO = new GenericDAO<>();
        List<Pedido> historialVacio = new ArrayList();
        Cliente cliente1 = new Cliente(
                87654321,
                "Ana Lopez",
                999888777,
                "La casa de alguien",
                historialVacio
                
        );

        clienteDAO.saveClass(cliente1);

        GenericDAO<Proveedor> proveedorDAO = new GenericDAO();
        Proveedor proveedor1 = new Proveedor(
                45678912,
                "luis.martinez@proveedor.com",
                Usuario.Roles.PROVEEDOR,
                "Maria Sanchez",
                "Distribuidora Norte SAC",
                "20123456789"
        );

        proveedorDAO.saveClass(proveedor1);

        GenericDAO<Facturacion> facturacionDAO = new GenericDAO();
        Facturacion fact1 = new Facturacion(
                Facturacion.TipoComprobante.BOLETA,
                "F001-000123",
                500.00,
                96.00,
                590.00
        );
        
        fact1.setCajero(cajero);
        fact1.setCliente(cliente1);
        facturacionDAO.saveClass(fact1);
        
        
        GenericDAO<Inventario> inventarioDAO = new GenericDAO();
        Inventario inventario = new Inventario(
            100,
            20,
            200,
            LocalDate.now()
        );
        
        inventarioDAO.saveClass(inventario);
        
        System.out.println("Finalmente!!!!");
    }
}
