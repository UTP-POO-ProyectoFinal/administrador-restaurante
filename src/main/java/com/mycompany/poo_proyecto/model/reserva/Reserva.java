package com.mycompany.poo_proyecto.model.reserva;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private int idReserva;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "hora_inicio", nullable = false)
    private Time horaInicio;

    @Column(name = "anticipo", nullable = false)
    private double anticipo;

    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "id_mesa", nullable = false)
    private int idMesa;

    // Constructor vacío (requerido por JPA)
    public Reserva() {
    }

    public Reserva(Date fecha, Time horaInicio, double anticipo, int idCliente, int idMesa) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.anticipo = anticipo;
        this.idCliente = idCliente;
        this.idMesa = idMesa;
    }

    public boolean cancelarReserva() {
        System.out.println("La reserva con ID " + idReserva + " ha sido cancelada.");
        return true;
    }

    public boolean confirmarReserva() {
        System.out.println("La reserva con ID " + idReserva + " ha sido confirmada.");
        return true;
    }


    public int getIdReserva() {
        return idReserva;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdMesa() {
        return idMesa;
    }
}
