package com.mycompany.poo_proyecto.service;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.utils.GenericDAO;
import java.util.*;

public class ClienteService {
    private final GenericDAO<Cliente> clienteDAO;

    public ClienteService() {
        this.clienteDAO = new GenericDAO<>();
    }
    
    public Cliente login(String codigoUTP, String password) {
        Cliente clienteEncontrado = clienteDAO.buscarPorCampo(Cliente.class, "codigoUTP", codigoUTP);
        if (clienteEncontrado != null && clienteEncontrado.getPassword().equals(password)) {
            return clienteEncontrado;
        }
        return null;
    }
    public boolean registrarEstudiante(String codigo, String nombre, String pass, int telefono, String direccion) {
        if (clienteDAO.buscarPorCampo(Cliente.class, "codigoUTP", codigo) != null) {
            return false; 
        }
        Cliente nuevo = new Cliente(codigo, pass, nombre, telefono, direccion);
        nuevo.setPassword(pass); 
        
        clienteDAO.saveClass(nuevo);
        return true;
    }
}