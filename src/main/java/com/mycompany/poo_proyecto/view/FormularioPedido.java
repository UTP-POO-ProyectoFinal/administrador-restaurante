package com.mycompany.poo_proyecto.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormularioPedido extends JDialog{
    private boolean pedidoGuardado = false;
    
    private JTextField txtCliente;
    private JTable tablaItems;
    private DefaultTableModel modeloItems;
    private JLabel lblTotal;

    public FormularioPedido(Frame parent) {
        super(parent, "Nuevo Pedido", true); 
        initUI();
    }

    private void initUI() {
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JLabel lblTitulo = new JLabel("Registrar Nuevo Pedido");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlHeader.add(lblTitulo, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlContenido = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlContenido.setBorder(new EmptyBorder(0, 20, 20, 20));
        pnlContenido.setOpaque(false);

        JPanel pnlProductos = new JPanel(new BorderLayout(0, 10));
        pnlProductos.setOpaque(false);
        pnlProductos.setBorder(BorderFactory.createTitledBorder("Catálogo de Productos"));
        
        JPanel gridProductos = new JPanel(new GridLayout(4, 2, 10, 10));
        gridProductos.setOpaque(false);
        agregarBotonProducto(gridProductos, "Hamburguesa", 12.50);
        agregarBotonProducto(gridProductos, "Pizza", 22.00);
        agregarBotonProducto(gridProductos, "Inka Kola", 4.50);
        agregarBotonProducto(gridProductos, "Café", 3.00);
        
        pnlProductos.add(gridProductos, BorderLayout.CENTER);
        pnlContenido.add(pnlProductos);

        JPanel pnlResumen = new JPanel(new BorderLayout(0, 10));
        pnlResumen.setOpaque(false);
        pnlResumen.setBorder(BorderFactory.createTitledBorder("Detalle de la Orden"));

        
        JPanel pnlCliente = new JPanel(new BorderLayout(5, 5));
        pnlCliente.setOpaque(false);
        pnlCliente.add(new JLabel("Cliente:"), BorderLayout.NORTH);
        txtCliente = new JTextField();
        pnlCliente.add(txtCliente, BorderLayout.CENTER);
        pnlResumen.add(pnlCliente, BorderLayout.NORTH);

        
        String[] cols = {"Producto", "Precio"};
        modeloItems = new DefaultTableModel(cols, 0);
        tablaItems = new JTable(modeloItems);
        tablaItems.setFillsViewportHeight(true);
        pnlResumen.add(new JScrollPane(tablaItems), BorderLayout.CENTER);

        
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setOpaque(false);
        
        lblTotal = new JLabel("Total: S/. 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JButton btnGuardar = new JButton("Confirmar Pedido");
        btnGuardar.setBackground(new Color(46, 125, 50)); // Verde
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.addActionListener(e -> guardar());

        pnlFooter.add(lblTotal, BorderLayout.NORTH);
        pnlFooter.add(Box.createVerticalStrut(10));
        pnlFooter.add(btnGuardar, BorderLayout.SOUTH);
        
        pnlResumen.add(pnlFooter, BorderLayout.SOUTH);
        pnlContenido.add(pnlResumen);

        add(pnlContenido, BorderLayout.CENTER);
    }

    private void agregarBotonProducto(JPanel panel, String nombre, double precio) {
        JButton btn = new JButton("<html><center>" + nombre + "<br>S/. " + precio + "</center></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> agregarItem(nombre, precio));
        panel.add(btn);
    }

    private void agregarItem(String nombre, double precio) {
        modeloItems.addRow(new Object[]{nombre, precio});
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modeloItems.getRowCount(); i++) {
            total += (double) modeloItems.getValueAt(i, 1);
        }
        lblTotal.setText("Total: S/. " + String.format("%.2f", total));
    }

    private void guardar() {
        if (txtCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cliente.");
            return;
        }
        pedidoGuardado = true;
        this.dispose(); 
    }

    
    public boolean isPedidoGuardado() { return pedidoGuardado; }
    public String getClienteNombre() { return txtCliente.getText(); }
    public String getTotalTexto() { return lblTotal.getText().replace("Total: ", ""); }
    public String getResumenItems() { return modeloItems.getRowCount() + " items"; }
}
