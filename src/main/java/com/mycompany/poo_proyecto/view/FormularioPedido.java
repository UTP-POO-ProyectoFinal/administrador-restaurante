package com.mycompany.poo_proyecto.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class FormularioPedido extends JDialog {

    private boolean pedidoGuardado = false;
    private String clienteSeleccionado = "Cliente mostrador";
    private double total = 0.0;
    private JTextField txtBuscarProd;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloListaProd;
    private ArrayList<String> todosLosProductos;

    private DefaultTableModel modeloItems;
    private JLabel lblTotal;

    private JTextField txtBuscarDNI;
    private JTextField txtBuscarNombre;
    private JLabel lblClienteEncontrado;

    public FormularioPedido(Frame parent) {
        super(parent, "Nuevo Pedido", true);
        initData();
        initUI();
    }

    private void initData() {
        todosLosProductos = new ArrayList();
        todosLosProductos.add("Hamburguesa - S/. 12.50");
        todosLosProductos.add("Pizza Americana - S/. 22.00");
        todosLosProductos.add("Pizza Pepperoni - S/. 25.00");
        todosLosProductos.add("Inka Kola 500ml - S/. 4.50");
        todosLosProductos.add("Café Americano - S/. 5.00");
        todosLosProductos.add("Ensalada César - S/. 18.00");
    }

    private void initUI() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlIzquierda = new JPanel(new BorderLayout(0, 10));
        pnlIzquierda.setBorder(new EmptyBorder(20, 20, 20, 10));

        JTabbedPane tabsCliente = new JTabbedPane();
        tabsCliente.setBorder(BorderFactory.createTitledBorder("1. Seleccionar Cliente"));

        JPanel pnlDNI = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscarDNI = new JTextField(15);
        JButton btnBuscarDNI = new JButton("Buscar");
        btnBuscarDNI.addActionListener(e -> buscarCliente("DNI", txtBuscarDNI.getText()));
        pnlDNI.add(new JLabel("Código/DNI:"));
        pnlDNI.add(txtBuscarDNI);
        pnlDNI.add(btnBuscarDNI);

        JPanel pnlNombre = new JPanel(new BorderLayout(5, 5));
        txtBuscarNombre = new JTextField();
        txtBuscarNombre.setToolTipText("Escribe para predecir...");

        txtBuscarNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (txtBuscarNombre.getText().length() > 3) {
                    lblClienteEncontrado.setText("Sugerencia: " + txtBuscarNombre.getText() + " Perez");
                }
            }
        });
        pnlNombre.add(new JLabel("Nombre:"), BorderLayout.WEST);
        pnlNombre.add(txtBuscarNombre, BorderLayout.CENTER);

        tabsCliente.addTab("Por Código", pnlDNI);
        tabsCliente.addTab("Por Nombre", pnlNombre);

        pnlIzquierda.add(tabsCliente, BorderLayout.NORTH);

        JPanel pnlProd = new JPanel(new BorderLayout(5, 5));
        pnlProd.setBorder(BorderFactory.createTitledBorder("2. Agregar Productos"));

        txtBuscarProd = new JTextField();
        txtBuscarProd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarProductos();
            }
        });

        modeloListaProd = new DefaultListModel<>();
        todosLosProductos.forEach(modeloListaProd::addElement);

        listaProductos = new JList<>(modeloListaProd);
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnAgregar = new JButton("Agregar al Pedido ↓");
        btnAgregar.addActionListener(e -> agregarProductoSeleccionado());

        pnlProd.add(txtBuscarProd, BorderLayout.NORTH);
        pnlProd.add(new JScrollPane(listaProductos), BorderLayout.CENTER);
        pnlProd.add(btnAgregar, BorderLayout.SOUTH);

        pnlIzquierda.add(pnlProd, BorderLayout.CENTER);

        JPanel pnlDerecha = new JPanel(new BorderLayout(0, 10));
        pnlDerecha.setBorder(new EmptyBorder(20, 10, 20, 20));
        pnlDerecha.setBorder(BorderFactory.createTitledBorder("3. Resumen de Orden"));

        lblClienteEncontrado = new JLabel("Cliente: Anónimo");
        lblClienteEncontrado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblClienteEncontrado.setForeground(new Color(0, 102, 204));
        pnlDerecha.add(lblClienteEncontrado, BorderLayout.NORTH);

        String[] cols = {"Producto", "Precio"};
        modeloItems = new DefaultTableModel(cols, 0);
        JTable tabla = new JTable(modeloItems);
        pnlDerecha.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total: S/. 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton btnConfirmar = new JButton("CONFIRMAR PEDIDO");
        btnConfirmar.setBackground(new Color(40, 167, 69)); // Verde éxito
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirmar.setPreferredSize(new Dimension(0, 50));
        btnConfirmar.addActionListener(e -> confirmar());

        pnlFooter.add(lblTotal, BorderLayout.NORTH);
        pnlFooter.add(Box.createVerticalStrut(10));
        pnlFooter.add(btnConfirmar, BorderLayout.SOUTH);

        pnlDerecha.add(pnlFooter, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlIzquierda, pnlDerecha);
        split.setDividerLocation(400);
        split.setResizeWeight(0.5);

        add(split);
    }

    private void filtrarProductos() {
        String texto = txtBuscarProd.getText().toLowerCase();
        modeloListaProd.clear();
        for (String p : todosLosProductos) {
            if (p.toLowerCase().contains(texto)) {
                modeloListaProd.addElement(p);
            }
        }
    }

    private void agregarProductoSeleccionado() {
        String val = listaProductos.getSelectedValue();
        if (val == null) {
            return;
        }

        String[] partes = val.split(" - S/. ");
        String nom = partes[0];
        double prec = Double.parseDouble(partes[1]);

        modeloItems.addRow(new Object[]{nom, prec});
        total += prec;
        lblTotal.setText("Total: S/. " + String.format("%.2f", total));
    }

    private void buscarCliente(String tipo, String valor) {
        if (valor.equals("123")) {
            clienteSeleccionado = "Pablo Rodriguez";
            lblClienteEncontrado.setText("Cliente: " + clienteSeleccionado + " (Verificado)");
        } else {
            lblClienteEncontrado.setText("Cliente: No encontrado (Se creará nuevo)");
            clienteSeleccionado = "Nuevo Cliente";
        }
    }

    private void confirmar() {
        if (modeloItems.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue productos.");
            return;
        }
        pedidoGuardado = true;
        this.dispose();
    }

    public boolean isPedidoGuardado() {
        return pedidoGuardado;
    }

    public String getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }

    public String getResumenProductos() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modeloItems.getRowCount(); i++) {
            sb.append("1x ").append(modeloItems.getValueAt(i, 0)).append("\n");
        }
        return sb.toString();
    }
}
