package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.service.EstadisticasService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainAdminFX extends Stage {

    private final EstadisticasService statsService;

    public MainAdminFX() {
        // Al instanciar el servicio, se preparan las consultas a la BD
        this.statsService = new EstadisticasService();
        initUI();
    }

    private void initUI() {
        setTitle("Panel de Administrador - Restaurante UTP (En Vivo)");
        setMaximized(true);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f4f6f8;");

        // 1. Barra Lateral
        root.setLeft(crearSidebar());

        // 2. Dashboard con Scroll
        ScrollPane scrollPane = new ScrollPane(crearDashboardContent());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        root.setCenter(scrollPane);

        Scene scene = new Scene(root);
        setScene(scene);
    }

    private VBox crearSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #2c3e50;");

        Label lblTitulo = new Label("ADMIN PANEL");
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 0 0 20 0;");

        Button btnDash = crearBotonMenu("📊 Dashboard", true);
        Button btnUsuarios = crearBotonMenu("👥 Usuarios", false); // Placeholder
        Button btnInventario = crearBotonMenu("📦 Inventario", false); // Placeholder
        
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        Button btnSalir = new Button("Cerrar Sesión");
        btnSalir.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-cursor: hand;");
        btnSalir.setOnAction(e -> {
            new LoginFX().show();
            this.close();
        });

        sidebar.getChildren().addAll(lblTitulo, btnDash, btnUsuarios, btnInventario, spacer, btnSalir);
        return sidebar;
    }

    private Button crearBotonMenu(String texto, boolean activo) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setPadding(new Insets(10, 15, 10, 15));
        String estiloBase = "-fx-background-color: " + (activo ? "#34495e" : "transparent") + "; " +
                            "-fx-text-fill: " + (activo ? "#3498db" : "#bdc3c7") + "; " +
                            "-fx-font-size: 14px; -fx-cursor: hand;";
        btn.setStyle(estiloBase);
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 14px;"));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBase));
        return btn;
    }

    private VBox crearDashboardContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label lblSaludo = new Label("Resumen de Operaciones (Tiempo Real)");
        lblSaludo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // --- SECCIÓN KPI: Datos Reales ---
        HBox kpiContainer = new HBox(20);
        
        // Formatear dinero a 2 decimales
        String ingresosHoy = String.format("S/. %.2f", statsService.obtenerIngresosHoy());
        
        kpiContainer.getChildren().addAll(
            crearTarjetaKPI("Ventas Hoy", ingresosHoy, "#27ae60"),
            crearTarjetaKPI("Pedidos Hoy", String.valueOf(statsService.obtenerTotalPedidosHoy()), "#2980b9"),
            crearTarjetaKPI("Plato Estrella", statsService.obtenerPlatoEstrella(), "#e67e22"),
            crearTarjetaKPI("Mesas Activas", statsService.obtenerEstadoMesas(), "#8e44ad") // Mostrará algo como "3 / 26"
        );

        // --- SECCIÓN GRÁFICOS ---
        HBox graficosContainer = new HBox(20);
        VBox.setVgrow(graficosContainer, Priority.ALWAYS);

        // 1. PieChart: Top Productos
        VBox panelPie = new VBox(10);
        estilarPanelGrafico(panelPie);
        HBox.setHgrow(panelPie, Priority.ALWAYS);

        Label lblPie = new Label("Top Productos Vendidos");
        lblPie.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        PieChart pieChart = new PieChart(statsService.obtenerPlatosMasVendidos());
        pieChart.setLegendVisible(false);
        // Si no hay datos, mostramos mensaje o gráfico vacío
        if(pieChart.getData().isEmpty()){
            pieChart.setTitle("Sin ventas registradas aún");
        }
        
        panelPie.getChildren().addAll(lblPie, pieChart);

        // 2. BarChart: Horas Pico
        VBox panelBar = new VBox(10);
        estilarPanelGrafico(panelBar);
        HBox.setHgrow(panelBar, Priority.ALWAYS);

        Label lblBar = new Label("Afluencia por Hora (Hoy)");
        lblBar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Hora");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pedidos");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> dataSeries = statsService.obtenerHorasConcurrentes();
        
        if (!dataSeries.getData().isEmpty()) {
            barChart.getData().add(dataSeries);
        } else {
            barChart.setTitle("Esperando primeros pedidos del día...");
        }
        
        barChart.setLegendVisible(false);
        panelBar.getChildren().addAll(lblBar, barChart);

        graficosContainer.getChildren().addAll(panelPie, panelBar);

        // Botón Actualizar (Opcional, pero útil para Admin)
        Button btnRefresh = new Button("🔄 Actualizar Datos");
        btnRefresh.setOnAction(e -> {
            // Recargar la escena (forma rápida de refrescar)
            this.close();
            new MainAdminFX().show();
        });

        content.getChildren().addAll(lblSaludo, new Separator(), kpiContainer, graficosContainer, btnRefresh);
        return content;
    }
    
    private void estilarPanelGrafico(VBox panel) {
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        panel.setPadding(new Insets(15));
    }

    private VBox crearTarjetaKPI(String titulo, String valor, String colorBorde) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15, 20, 15, 20));
        card.setPrefWidth(250);
        card.setStyle("-fx-background-color: white; " +
                      "-fx-background-radius: 8; " +
                      "-fx-border-color: " + colorBorde + "; " +
                      "-fx-border-width: 0 0 0 5; " +
                      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 1);");

        Label lblTitulo = new Label(titulo.toUpperCase());
        lblTitulo.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 22px; -fx-font-weight: bold;");
        lblValor.setWrapText(true);

        card.getChildren().addAll(lblTitulo, lblValor);
        return card;
    }
}