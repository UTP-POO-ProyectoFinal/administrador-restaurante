package com.mycompany.poo_proyecto.model.reserva;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.mycompany.poo_proyecto.model.usuario.Cliente;

@Entity
@Table(name = "reservas")
public class Reserva {

    public enum EstadoReserva {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private int idReserva;
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaReserva;

    @Column(name = "num_personas", nullable = false)
    private int numPersonas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    public Reserva() {
    }

    public Reserva(LocalDate fechaReserva, LocalTime horaReserva, int numPersonas,
            EstadoReserva estado, Cliente cliente, Mesa mesa) {
        if (fechaReserva == null) {
            throw new IllegalArgumentException("fechaReserva no puede ser nula");
        }
        if (horaReserva == null) {
            throw new IllegalArgumentException("horaReserva no puede ser nula");
        }
        if (numPersonas <= 0) {
            throw new IllegalArgumentException("numPersonas debe ser > 0");
        }
        if (cliente == null || mesa == null) {
            throw new IllegalArgumentException("cliente y mesa no pueden ser nulos");
        }

        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.numPersonas = numPersonas;
        this.estado = (estado != null) ? estado : EstadoReserva.PENDIENTE;
        this.cliente = cliente;
        this.mesa = mesa;
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDate.now();
    }

    // --- Getters y Setters ---
    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        if (numPersonas <= 0) {
            throw new IllegalArgumentException("numPersonas debe ser > 0");
        }
        this.numPersonas = numPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    // --- Lógica adicional ---
    public boolean esHoy() {
        return fechaReserva.equals(LocalDate.now());
    }

    public boolean estaActiva() {
        return estado == EstadoReserva.CONFIRMADA || estado == EstadoReserva.PENDIENTE;
    }

    public void cancelar() {
        this.estado = EstadoReserva.CANCELADA;
    }
}

