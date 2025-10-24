package com.mycompany.poo_proyecto.dao;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import com.mycompany.poo_proyecto.model.usuario.Administrador;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdministradorDAO {
    public void saveAdministrador(Administrador admin) {
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(admin);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
