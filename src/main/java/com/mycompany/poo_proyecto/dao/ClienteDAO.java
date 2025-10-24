package com.mycompany.poo_proyecto.dao;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDAO {
    public void saveCliente(Cliente cliente) {
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
