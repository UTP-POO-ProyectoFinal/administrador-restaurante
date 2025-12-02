package com.mycompany.poo_proyecto.service;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.utils.GenericDAO;
import java.util.*;

public class ClienteService {
    private final GenericDAO<Cliente> clienteDAO;

    public ClienteService() {
        this.clienteDAO = new GenericDAO<>();
    }
    
    public Cliente buscarPorCodigo(String codigoUTP) {
        return clienteDAO.buscarPorCampo(Cliente.class, "codigoUTP", codigoUTP);
        }
    
    public void crearClienteRapido(String codigo, String nombre) {
        if (buscarPorCodigo(codigo)==null) {
        Cliente nuevo = new Cliente(codigo,"U-------", nombre, 0, "Presencial");
        clienteDAO.saveClass(nuevo); 
        }
    }
}