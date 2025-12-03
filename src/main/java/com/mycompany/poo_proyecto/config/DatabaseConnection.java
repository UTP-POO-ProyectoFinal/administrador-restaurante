package com.mycompany.poo_proyecto.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static String url;
    private static String usuario;
    private static String password;
    private static String driver;
    
    private DatabaseConnection() {
        cargarConfiguracion();
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    private void cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input != null) {
                props.load(input);
                url = props.getProperty("db.url");
                usuario = props.getProperty("db.username");
                password = props.getProperty("db.password");
                driver = props.getProperty("db.driver");
            } else {
                throw new RuntimeException("Archivo database.properties no encontrado");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar configuración: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        getInstance();
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage(), e);
        }
    }
    
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
}