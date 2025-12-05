package com.mycompany.poo_proyecto.model.usuario;

import com.mycompany.poo_proyecto.model.pedido.Pedido;
import jakarta.persistence.*;
import java.util.*;
import jakarta.persistence.Transient;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "codigo_UTP", nullable = false, unique = true)
    private String codigoUTP;
    
    @Column(name ="password", nullable = false)
    private String password;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false, unique = true)
    private int telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente() {

    }

    public Cliente(String codigoUTP, String password, String nombre, int telefono, String direccion){
        this.codigoUTP = codigoUTP;
        this.password = password;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public List<Pedido> getHistorialCompras() {
        return historialCompras;
    }
    
    public int getIdCliente(){
        return idCliente;
    }
    public String getCodigoUTP(){
        return codigoUTP;
    }
    public void setCodigoUTP(String codigoUTP){
        this.codigoUTP = codigoUTP;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
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
