package com.mycompany.poo_proyecto.utils;

import com.mycompany.poo_proyecto.config.DatabaseConnection;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class GenericDAO<T> {
    public void saveClass(T classGen) {        
        Transaction transaction = null;
        
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(classGen);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al guardar: "+e);
        }
    }
    
    public void updateClass(T classGen){
        Transaction transaction = null;
        try(Session session = DatabaseConnection.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(classGen);
            transaction.commit();
        } catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
                
            }
            System.out.println("Error al actualizar: "+e);
        }
    }
}
