package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.model.reserva.Mesa;
import com.mycompany.poo_proyecto.model.reserva.Reserva;
import com.mycompany.poo_proyecto.model.reserva.Reserva.EstadoReserva;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.utils.GenericDAO;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaService {

    private final GenericDAO<Reserva> reservaDAO;

    public ReservaService() {
        this.reservaDAO = new GenericDAO<>();
    }
    private void actualizarEstado(Reserva reserva, EstadoReserva nuevoEstado) {
        reserva.setEstado(nuevoEstado);
        reservaDAO.updateClass(reserva);
    }
    
    
    public void solicitarReserva(LocalDate fecha, LocalTime hora, int personas, Cliente cliente, Mesa mesa) {
        if (mesa.getCapacidad() < personas) {
            System.err.println("Error: La Mesa " + mesa.getNumeroMesa() + " es muy pequeña (Capacidad: " + mesa.getCapacidad() + ")");
            return;
        }
        
    
        if (fecha.isBefore(LocalDate.now())) {
            System.err.println("Aun no se crean las maquinas en el tiempo para reservar una mesa en el pasado...");
            return;
        }
        Reserva nuevaReserva = new Reserva(fecha, hora, personas, EstadoReserva.PENDIENTE, cliente, mesa);
        reservaDAO.saveClass(nuevaReserva);
        System.out.println("""
                             📅 Reserva generada con éxito.
                                Cliente: %s
                                Código:  %s
                                Mesa:    %d
                                """.formatted(
                                    cliente.getNombre(),
                                    cliente.getCodigoUTP(),
                                    mesa.getNumeroMesa()));
        
    }
    
    public void confirmarReserva(Reserva reserva) {
        if (reserva.getEstado() == EstadoReserva.PENDIENTE) {
            
            actualizarEstado(reserva, EstadoReserva.CONFIRMADA);
            System.out.println("Reserva CONFIRMADA para " + reserva.getCliente().getNombre());
            
        } else {
            System.out.println("No se puede confirmar: La reserva actual no está en estado PENDIENTE.");
        }
    }
    
    public void cancelarReserva(Reserva reserva) {
        if (reserva.estaActiva()) { 
            actualizarEstado(reserva, EstadoReserva.CANCELADA);
            System.out.println("La reserva de " + reserva.getCliente().getNombre() + " ha sido CANCELADA.");
            
        } else {
            System.out.println("La reserva ya estaba cancelada o vencida anteriormente.");
        }
    }
    

}
