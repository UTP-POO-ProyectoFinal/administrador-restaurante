package com.mycompany.poo_proyecto.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainCajeroFX {

    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-font-size: 14px;");
        
        Tab tabPedidos = new Tab("Gestión de Pedidos");
        tabPedidos.setClosable(false);
        
        Parent panelVista = new PanelPedidosCajeroFX().getVista();
        ScrollPane scrollPedidos = new ScrollPane(panelVista);
        scrollPedidos.setFitToWidth(true); 
        scrollPedidos.setFitToHeight(true); 
        scrollPedidos.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        
        tabPedidos.setContent(scrollPedidos);
        
        Tab tabMesas = new Tab("Control de Mesas");
        tabMesas.setClosable(false);
        Mesas vistaMesas = new Mesas();
        tabMesas.setContent(vistaMesas.getView());
        
        Tab tabClientes = new Tab("Clientes");
        tabClientes.setClosable(false);
        
        
        tabPane.getTabs().addAll(tabPedidos, tabMesas, tabClientes);
        
        root.setCenter(tabPane);
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth() * 0.9, screenBounds.getHeight() * 0.9);
        
        stage.setTitle("Centro de Control - Cajero UTP");
        stage.setScene(scene);
        stage.setMaximized(true); 
        stage.show();
    }
}