package com.mycompany.poo_proyecto.dao;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import com.mycompany.poo_proyecto.model.usuario.Cajero;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CajeroDAO {
    public void saveCajero(Cajero cajero) {
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(cajero);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
