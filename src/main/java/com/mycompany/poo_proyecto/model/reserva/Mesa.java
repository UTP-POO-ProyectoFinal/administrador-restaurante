package com.mycompany.poo_proyecto.model.reserva;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", nullable = false)
    private int idMesa;

    @Column(name = "numero_mesa", nullable = false)
    private int numeroMesa;

    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    @OneToMany(mappedBy = "mesa", fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Mesa() {
    }

    public Mesa(int numeroMesa, int capacidad) {
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}

