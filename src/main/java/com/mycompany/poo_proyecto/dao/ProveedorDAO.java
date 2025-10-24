package com.mycompany.poo_proyecto.dao;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import com.mycompany.poo_proyecto.model.usuario.Proveedor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProveedorDAO {
    public void saveProveedor(Proveedor proveedor) {
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(proveedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
