package com.mycompany.poo_proyecto.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.mycompany.poo_proyecto.model.usuario.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.inventario.Inventario;

public class DatabaseConnection {    
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost/poo_proyecto");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "");
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Administrador.class);
                configuration.addAnnotatedClass(Cajero.class);
                configuration.addAnnotatedClass(Cliente.class);
                configuration.addAnnotatedClass(Proveedor.class);
                configuration.addAnnotatedClass(Facturacion.class);
                configuration.addAnnotatedClass(Inventario.class);

                StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
