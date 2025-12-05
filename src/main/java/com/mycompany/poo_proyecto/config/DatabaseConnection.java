package com.mycompany.poo_proyecto.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.mycompany.poo_proyecto.model.usuario.*;
import com.mycompany.poo_proyecto.model.facturacion.Facturacion;
import com.mycompany.poo_proyecto.model.facturacion.Pago;
import com.mycompany.poo_proyecto.model.inventario.Inventario;
import com.mycompany.poo_proyecto.model.pedido.DetallePedido;
import com.mycompany.poo_proyecto.model.reserva.Reserva;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import com.mycompany.poo_proyecto.model.pedido.Platillo;
import com.mycompany.poo_proyecto.model.reserva.Mesa;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DatabaseConnection {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://luisgonzaganeiraayala.com:3306/luisgonz_kevin?useSSL=false&requireSSL=false&serverTimezone=UTC");
                settings.put("hibernate.connection.username", "luisgonz_kevin");
                settings.put("hibernate.connection.password", "-aspDX;FLPEtSC0o");
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
                configuration.addAnnotatedClass(Reserva.class);
                configuration.addAnnotatedClass(Pedido.class);
                configuration.addAnnotatedClass(Mesa.class);
                configuration.addAnnotatedClass(Pago.class);
                configuration.addAnnotatedClass(Platillo.class);
                configuration.addAnnotatedClass(DetallePedido.class);

                StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());
                System.out.println("Base online conectada");
            } catch (HibernateException e) {
                System.err.print("Error conenctado la bd online " + e);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
