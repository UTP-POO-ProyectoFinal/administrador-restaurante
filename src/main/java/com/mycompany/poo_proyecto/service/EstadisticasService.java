package com.mycompany.poo_proyecto.service;

import com.mycompany.poo_proyecto.config.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EstadisticasService {

    // Definimos el rango "HOY" desde Java para evitar problemas de UTC en la nube
    private LocalDateTime getInicioDia() {
        return LocalDate.now().atStartOfDay(); // 00:00:00 de hoy en tu PC
    }

    private LocalDateTime getFinDia() {
        return LocalDate.now().atTime(LocalTime.MAX); // 23:59:59 de hoy en tu PC
    }

    // --- KPI 1: Ventas (Dinero Real) ---
    // Coincide con los pedidos que NO han sido cancelados.
    public double obtenerIngresosHoy() {
        double total = 0.0;
        try (Session session = DatabaseConnection.getSession()) {
            String hql = "SELECT SUM(p.total) FROM Pedido p "
                    + "WHERE p.fechaHora BETWEEN :inicio AND :fin "
                    + "AND p.estado != 'CANCELADO'";

            Double resultado = session.createQuery(hql, Double.class)
                    .setParameter("inicio", getInicioDia())
                    .setParameter("fin", getFinDia())
                    .uniqueResult();

            if (resultado != null) {
                total = resultado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // --- KPI 2: Total Pedidos (Coincide con el Cajero "Total Pedidos 16") ---
    // Cuenta TODOS los intentos de pedido del día, incluso cancelados, para cuadrar caja.
    public int obtenerTotalPedidosHoy() {
        long cantidad = 0;
        try (Session session = DatabaseConnection.getSession()) {
            String hql = "SELECT COUNT(p) FROM Pedido p "
                    + "WHERE p.fechaHora BETWEEN :inicio AND :fin";

            Long resultado = session.createQuery(hql, Long.class)
                    .setParameter("inicio", getInicioDia())
                    .setParameter("fin", getFinDia())
                    .uniqueResult();

            if (resultado != null) {
                cantidad = resultado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) cantidad;
    }

    // --- KPI 3: Plato Estrella ---
    public String obtenerPlatoEstrella() {
        String nombrePlato = "Sin Ventas";
        try (Session session = DatabaseConnection.getSession()) {
            // Usamos JOIN implícito gracias a que agregaste las relaciones en el Modelo
            String hql = "SELECT d.platillo.nombre "
                    + "FROM DetallePedido d "
                    + "GROUP BY d.platillo.nombre "
                    + "ORDER BY SUM(d.cantidad) DESC";

            Query<String> query = session.createQuery(hql, String.class);
            query.setMaxResults(1);

            String resultado = query.uniqueResult();
            if (resultado != null) {
                nombrePlato = resultado;
            }
        } catch (Exception e) {
            // e.printStackTrace(); // Silencioso si no hay ventas aún
        }
        return nombrePlato;
    }

    // --- KPI 4: Mesas Activas ---
    public String obtenerEstadoMesas() {
        long totalMesas = 26; // Valor por defecto si falla la BD
        long mesasOcupadas = 0;

        try (Session session = DatabaseConnection.getSession()) {
            // 1. Total Mesas
            Long resTotal = session.createQuery("SELECT COUNT(m) FROM Mesa m", Long.class).uniqueResult();
            if (resTotal != null && resTotal > 0) {
                totalMesas = resTotal;
            }

            // 2. Mesas Ocupadas (Pedidos Pendientes, En Preparacion, Listos)
            // Excluimos ENTREGADO y CANCELADO porque esos ya liberaron mesa.
            String hqlOcupadas = "SELECT COUNT(DISTINCT p.mesa.idMesa) FROM Pedido p "
                    + "WHERE (p.estado = 'PENDIENTE' OR p.estado = 'EN_PREPARACION' OR p.estado = 'LISTO')";

            Long resOcupadas = session.createQuery(hqlOcupadas, Long.class).uniqueResult();
            if (resOcupadas != null) {
                mesasOcupadas = resOcupadas;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesasOcupadas + " / " + totalMesas;
    }

    // --- GRÁFICO 1: PieChart ---
    public ObservableList<PieChart.Data> obtenerPlatosMasVendidos() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        try (Session session = DatabaseConnection.getSession()) {
            String hql = "SELECT d.platillo.nombre, SUM(d.cantidad) "
                    + "FROM DetallePedido d "
                    + "GROUP BY d.platillo.nombre "
                    + "ORDER BY SUM(d.cantidad) DESC";

            List<Object[]> resultados = session.createQuery(hql, Object[].class)
                    .setMaxResults(5)
                    .list();

            for (Object[] fila : resultados) {
                String producto = (String) fila[0];
                Number cantidad = (Number) fila[1];
                datos.add(new PieChart.Data(producto, cantidad.doubleValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }

    // --- GRÁFICO 2: BarChart ---
    public XYChart.Series<String, Number> obtenerHorasConcurrentes() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Pedidos Hoy");

        try (Session session = DatabaseConnection.getSession()) {
            // IMPORTANTE: hour() es funcion HQL. Usamos BETWEEN para filtrar el día correcto.
            String hql = "SELECT hour(p.fechaHora), COUNT(p) "
                    + "FROM Pedido p "
                    + "WHERE p.fechaHora BETWEEN :inicio AND :fin "
                    + "GROUP BY hour(p.fechaHora) "
                    + "ORDER BY hour(p.fechaHora) ASC";

            List<Object[]> resultados = session.createQuery(hql, Object[].class)
                    .setParameter("inicio", getInicioDia())
                    .setParameter("fin", getFinDia())
                    .list();

            for (Object[] fila : resultados) {
                Integer hora = (Integer) fila[0];
                Long cantidad = (Long) fila[1];

                // Formato 13:00
                String etiquetaHora = String.format("%02d:00", hora);
                serie.getData().add(new XYChart.Data<>(etiquetaHora, cantidad));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serie;
    }
}
