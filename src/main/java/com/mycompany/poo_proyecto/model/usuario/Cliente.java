package com.mycompany.poo_proyecto.model.usuario;

import com.mycompany.poo_proyecto.model.pedido.Pedido;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "dni", nullable = false, unique = true)
    private int dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false, unique = true)
    private int telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "historial_compras", nullable = false)
    private List<Pedido> historialCompras = new ArrayList();

    public Cliente() {

    }

    public Cliente(int dni, String nombre, int telefono, String direccion, List<Pedido> historialCompras) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.historialCompras = historialCompras;
    }

    public List<Pedido> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<Pedido> historialCompras) {
        this.historialCompras = historialCompras;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
