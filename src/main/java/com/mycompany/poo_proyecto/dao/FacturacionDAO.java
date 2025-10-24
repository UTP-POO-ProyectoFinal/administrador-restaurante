package com.mycompany.poo_proyecto.dao;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FacturacionDAO {
        public void saveFacturacion(Facturacion facturacion) {
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(facturacion);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
