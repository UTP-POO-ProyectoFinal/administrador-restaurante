package com.mycompany.poo_proyecto.view;

import javax.swing.*;
import java.awt.*;

public class MainCajero extends JFrame{

    public MainCajero() {
        initUI();
    }

    private void initUI() {
        setTitle("Bienvenido al centro de control - Cajero");
        setSize(1024, 768); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        tabbedPane.addTab("Pedidos", new PanelPedidosCajero());

        tabbedPane.addTab("Mesas", new PanelMesasCajero());

        tabbedPane.addTab("Clientes", new PanelClientesCajero());

        tabbedPane.addTab("Productos", new JPanel()); // Panel vacío por ahora

        tabbedPane.addTab("Cierre Caja", new JPanel());

        add(tabbedPane);
    }

    class PanelPedidosCajero extends JPanel {

        public PanelPedidosCajero() {
            add(new JLabel("Aquí irá el dashboard de pedidos (Imagen 10)"));
        }
    }

    class PanelMesasCajero extends JPanel {

        public PanelMesasCajero() {
            add(new JLabel("Aquí irá el mapa de mesas con semáforo"));
        }
    }

    class PanelClientesCajero extends JPanel {

        public PanelClientesCajero() {
            add(new JLabel("Aquí irá la tabla de clientes (Imagen 7)"));
        }
    }
}
