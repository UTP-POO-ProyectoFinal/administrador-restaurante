package com.mycompany.poo_proyecto.utils;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import java.util.List;

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
            System.err.println("Error al guardar: " + e);
        }
    }

    public void updateClass(T classGen) {
        Transaction transaction = null;
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(classGen);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            System.out.println("Error al actualizar: " + e);
        }
    }

    public T buscarPorCampo(Class<T> clase, String nombreCampo, String valor) {
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            String hql = "FROM " + clase.getSimpleName() + " WHERE " + nombreCampo + " = :valor";
            return session.createQuery(hql, clase).setParameter("valor", valor).uniqueResult(); 
        } catch (Exception e) {
            System.err.println("Error al buscar por " + nombreCampo + ": " + e);
            return null;
        }
    }

    public List<T> listarTodos(Class<T> clase) {
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + clase.getSimpleName(), clase).list();
        } catch (Exception e) {
            System.err.println("Error al listar: " + e);
            return null;
        }
    }

}
