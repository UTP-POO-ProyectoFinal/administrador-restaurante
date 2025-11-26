package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.model.usuario.Administrador;
import com.mycompany.poo_proyecto.model.usuario.Cajero;
import com.mycompany.poo_proyecto.model.usuario.Proveedor;
import com.mycompany.poo_proyecto.model.usuario.Usuario;
import com.mycompany.poo_proyecto.utils.GenericDAO;

public class UsuarioService {

    private final GenericDAO<Administrador> adminDAO;
    private final GenericDAO<Cajero> cajeroDAO;
    private final GenericDAO<Proveedor> proveedorDAO;

    public UsuarioService() {
        this.adminDAO = new GenericDAO<>();
        this.cajeroDAO = new GenericDAO<>();
        this.proveedorDAO = new GenericDAO<>();
    }

    public Usuario loginPersonal(String usuarioUser, String password) {
        Administrador admin = adminDAO.buscarPorCampo(Administrador.class, "usuario", usuarioUser);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        Cajero cajero = cajeroDAO.buscarPorCampo(Cajero.class, "usuario", usuarioUser);
        if (cajero != null && cajero.getPassword().equals(password)) {
            return cajero;
        }
        Proveedor proveedor = proveedorDAO.buscarPorCampo(Proveedor.class, "correo", usuarioUser);
        if (proveedor != null) {
            // Aquiw asumo que el proveedor tiene password (si no, omitir esta parte) 
        }

        return null;
    }
}
