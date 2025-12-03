package com.mycompany.poo_proyecto.utils;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> {
    
    public void saveClass(T classGen) {
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            Class<?> clase = classGen.getClass();
            String tableName = clase.getSimpleName().toLowerCase();
            
            Field[] fields = clase.getDeclaredFields();
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            List<Object> params = new ArrayList<>();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(classGen);
                
                if (value != null && !field.getName().equals("id")) {
                    if (columns.length() > 0) {
                        columns.append(", ");
                        values.append(", ");
                    }
                    columns.append(field.getName());
                    values.append("?");
                    params.add(value);
                }
            }
            
            String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
            ps = connection.prepareStatement(sql);
            
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            ps.executeUpdate();
            System.out.println("Guardado exitosamente");
            
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateClass(T classGen) {
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            Class<?> clase = classGen.getClass();
            String tableName = clase.getSimpleName().toLowerCase();
            
            Field[] fields = clase.getDeclaredFields();
            StringBuilder setClause = new StringBuilder();
            List<Object> params = new ArrayList<>();
            Object idValue = null;
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(classGen);
                
                if (field.getName().equals("id")) {
                    idValue = value;
                } else if (value != null) {
                    if (setClause.length() > 0) {
                        setClause.append(", ");
                    }
                    setClause.append(field.getName()).append(" = ?");
                    params.add(value);
                }
            }
            
            if (idValue == null) {
                throw new Exception("No se encontró el ID para actualizar");
            }
            
            String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE id = ?";
            ps = connection.prepareStatement(sql);
            
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ps.setObject(params.size() + 1, idValue);
            
            ps.executeUpdate();
            System.out.println("Actualizado exitosamente");
            
        } catch (Exception e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public T buscarPorCampo(Class<T> clase, String nombreCampo, String valor) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            String tableName = clase.getSimpleName().toLowerCase();
            String sql = "SELECT * FROM " + tableName + " WHERE " + nombreCampo + " = ? LIMIT 1";
            
            ps = connection.prepareStatement(sql);
            ps.setString(1, valor);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToObject(rs, clase);
            }
            
        } catch (Exception e) {
            System.err.println("Error al buscar por " + nombreCampo + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public List<T> listarTodos(Class<T> clase) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> lista = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            
            String tableName = clase.getSimpleName().toLowerCase();
            String sql = "SELECT * FROM " + tableName;
            
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                T objeto = mapResultSetToObject(rs, clase);
                if (objeto != null) {
                    lista.add(objeto);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al listar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
    
    private T mapResultSetToObject(ResultSet rs, Class<T> clase) {
        try {
            T objeto = clase.getDeclaredConstructor().newInstance();
            Field[] fields = clase.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = rs.getObject(field.getName());
                    if (value != null) {
                        field.set(objeto, value);
                    }
                } catch (SQLException e) {
                    // Campo no existe en la BD, continuar
                }
            }
            return objeto;
            
        } catch (Exception e) {
            System.err.println("Error al mapear objeto: " + e.getMessage());
            return null;
        }
    }
}