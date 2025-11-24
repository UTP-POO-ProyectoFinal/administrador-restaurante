package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.model.inventario.Inventario;
import com.mycompany.poo_proyecto.utils.GenericDAO;
import java.time.LocalDate;

public class InventarioService {

    private final GenericDAO<Inventario> inventarioDAO;

    public InventarioService() {
        this.inventarioDAO = new GenericDAO<>();
    }

    public void registrarNuevoIngreso(int stockInicial, int minimo, int maximo) {
        try {
            Inventario item = new Inventario(stockInicial, minimo, maximo, LocalDate.now());

            if (item.isBajoMinimo()) {
                System.out.println("==ALERTA==\nEl stock inicial esta por debajo del minimo.");
            }

            inventarioDAO.saveClass(item);
            System.out.println("Item de inventario registrado correctamente.");

        } catch (IllegalArgumentException e) {
            System.err.println("Error al registrar inventario: " + e.getMessage());
        }
    }

    public void actualizarStock(Inventario item, int cantidad, boolean esEntrada) {
        try {
            if (esEntrada) {
                item.incrementar(cantidad);
            } else {
                item.decrementar(cantidad);
            }
            item.setFechaActualizacion(LocalDate.now());
            
            inventarioDAO.updateClass(item);
            
            if (item.isBajoMinimo()) {
                System.out.println("==ALERTA DE STOCK BAJO==\nQuedan " + item.getStock() + " unidades.");
            } else {
                System.out.println("==Stock Actualizado==\nNuevo Total: "+item.getStock());
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
        }
    }
}
