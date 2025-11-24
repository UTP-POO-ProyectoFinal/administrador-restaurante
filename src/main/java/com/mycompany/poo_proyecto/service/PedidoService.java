package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.model.pedido.EstadoPedido;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import com.mycompany.poo_proyecto.utils.GenericDAO;

public class PedidoService {

    private final GenericDAO<Pedido> pedidoDAO;

    public PedidoService() {
        this.pedidoDAO = new GenericDAO<>();
    }

    public void registrarPedido(String tipo, int idCliente, int idMesa) {
        Pedido nuevoPedido = new Pedido(EstadoPedido.PENDIENTE.name(), tipo, idCliente, idMesa);

        pedidoDAO.saveClass(nuevoPedido);
        System.out.println("Pedido registrado con estado: " + EstadoPedido.PENDIENTE);
    }

    private void actualizarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        pedido.setEstado(nuevoEstado.name());
        pedidoDAO.updateClass(pedido);
    }

    public void avanzarEstado(Pedido pedido) {
        String estadoActualStr = pedido.getEstado();

        try {
            EstadoPedido estadoActual = EstadoPedido.valueOf(estadoActualStr);

            switch (estadoActual) {
                case PENDIENTE:
                    actualizarEstado(pedido, EstadoPedido.EN_PREPARACION);
                    System.out.println("Pedido enviado a cocina");
                    break;
                case EN_PREPARACION:
                    actualizarEstado(pedido, EstadoPedido.LISTO);
                    System.out.println("El pedido esta LISTO para recoger.");
                    break;

                case LISTO:
                    actualizarEstado(pedido, EstadoPedido.ENTREGADO);
                    System.out.println("Pedido ENTREGADO al cliente. ¡Buen provecho!");
                    break;

                case ENTREGADO:
                    System.out.println("Este pedido ya fue entregado, no se puede avanzar mas.");
                    break;

                case CANCELADO:
                    System.out.println("No se puede avanzar un pedido CANCELADO.");
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: El estado actual del pedido no es valido en el sistema.");
        }
    }

    public void cancelarPedido(Pedido pedido) {
        if (!pedido.getEstado().equals(EstadoPedido.ENTREGADO.name())) {
            actualizarEstado(pedido, EstadoPedido.CANCELADO);
            System.out.println("El peddido ha sido CANCELADO");

        } else {
            System.out.println("No puedes cancelar un pedido que ya fue consumido/entregado.");
        }
    }
}
