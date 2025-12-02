package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.model.pedido.EstadoPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.border.EmptyBorder;

public class PanelPedidosCajero extends JPanel {

    private final java.util.List<Object[]> datosOcultos = new ArrayList();
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    
    private JLabel lblValTotal, lblValCocina, lblValHoy;

    private JPanel pnlDetalleContent;
    private JLabel lblFotoUsuario; 
    private JLabel lblDetCliente, lblDetDNI, lblDetCorreo, lblDetTel, lblDetDir;
    private JTextArea txtDetItems;
    private JLabel lblDetTotal;
    private JButton btnAvanzar, btnCancelar;

    public PanelPedidosCajero() {
        initUI();
        cargarDatosPrueba();
        actualizarEstadisticas();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setOpaque(false);

        // Título y Botón
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblTitulo = new JLabel("Gestión de Pedidos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(33, 37, 41));

        JButton btnNuevo = new JButton("+ Nuevo Pedido");
        btnNuevo.setBackground(new Color(230, 81, 0));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNuevo.setFocusPainted(false);
        btnNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(e -> accionNuevoPedido());

        pnlTop.add(lblTitulo, BorderLayout.WEST);
        pnlTop.add(btnNuevo, BorderLayout.EAST);

       
        JPanel pnlCards = new JPanel(new GridLayout(1, 3, 20, 0));
        pnlCards.setOpaque(false);
        pnlCards.setPreferredSize(new Dimension(0, 110));

        pnlCards.add(crearTarjeta("Total de Pedidos", "Registrados hoy", "0", Color.WHITE));
        pnlCards.add(crearTarjeta("En Preparación", "Activos en cocina", "0", new Color(255, 248, 225)));
        pnlCards.add(crearTarjeta("Completados", "Entregados hoy", "0", new Color(232, 245, 233)));

        lblValTotal = (JLabel) ((JPanel) pnlCards.getComponent(0)).getComponent(1);
        lblValCocina = (JLabel) ((JPanel) pnlCards.getComponent(1)).getComponent(1);
        lblValHoy = (JLabel) ((JPanel) pnlCards.getComponent(2)).getComponent(1);

        pnlHeader.add(pnlTop);
        pnlHeader.add(pnlCards);
        add(pnlHeader, BorderLayout.NORTH);

       
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(650);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);
        splitPane.setBackground(new Color(245, 247, 250));

        
        String[] cols = {"#", "Cliente", "Estado", "Hora", "Total"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(45);
        tablaPedidos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaPedidos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPedidos.getTableHeader().setBackground(new Color(248, 249, 250));
        
        tablaPedidos.getTableHeader().setReorderingAllowed(false); 
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> mostrarDetalle());

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        
        JPanel pnlDetalle = new JPanel(new BorderLayout());
        pnlDetalle.setBackground(Color.WHITE);
        pnlDetalle.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

       
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlAcciones.setBackground(Color.WHITE);
        
        btnAvanzar = new JButton("Avanzar Estado ->");
        configurarBoton(btnAvanzar, new Color(240, 240, 240), Color.BLACK);
        btnAvanzar.addActionListener(e -> accionAvanzar());

        btnCancelar = new JButton("Cancelar");
        configurarBoton(btnCancelar, new Color(220, 53, 69), Color.WHITE);
        btnCancelar.addActionListener(e -> accionCancelar());

        pnlAcciones.add(btnAvanzar);
        pnlAcciones.add(btnCancelar);
        pnlDetalle.add(pnlAcciones, BorderLayout.NORTH);

        
        pnlDetalleContent = new JPanel();
        pnlDetalleContent.setLayout(new BoxLayout(pnlDetalleContent, BoxLayout.Y_AXIS));
        pnlDetalleContent.setBackground(Color.WHITE);
        pnlDetalleContent.setBorder(new EmptyBorder(10, 25, 20, 25));

        
        JPanel pnlFoto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFoto.setBackground(Color.WHITE);
        lblFotoUsuario = new JLabel();
        lblFotoUsuario.setPreferredSize(new Dimension(80, 80));
        lblFotoUsuario.setOpaque(true);
        lblFotoUsuario.setBackground(new Color(230, 230, 230));
        lblFotoUsuario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFotoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoUsuario.setText("FOTO"); // Aquí irá la imagen real luego
        pnlFoto.add(lblFotoUsuario);
        
     
        JPanel pnlInfoTexto = new JPanel();
        pnlInfoTexto.setLayout(new BoxLayout(pnlInfoTexto, BoxLayout.Y_AXIS));
        pnlInfoTexto.setBackground(Color.WHITE);
        
        lblDetCliente = crearLbl("Seleccione pedido", Font.BOLD, 18, Color.BLACK);
        lblDetDNI = crearLbl("DNI: -", Font.PLAIN, 13, Color.GRAY);
        lblDetCorreo = crearLbl("-", Font.PLAIN, 13, Color.GRAY);
        lblDetTel = crearLbl("-", Font.PLAIN, 13, Color.GRAY);
        
        pnlInfoTexto.add(lblDetCliente);
        pnlInfoTexto.add(lblDetDNI);
        pnlInfoTexto.add(lblDetCorreo);
        pnlInfoTexto.add(lblDetTel);

        
        JPanel pnlCabeceraDetalle = new JPanel(new BorderLayout());
        pnlCabeceraDetalle.setBackground(Color.WHITE);
        pnlCabeceraDetalle.add(pnlFoto, BorderLayout.WEST);
        pnlCabeceraDetalle.add(pnlInfoTexto, BorderLayout.CENTER);

        
        lblDetDir = crearLbl("Dirección: -", Font.PLAIN, 13, Color.GRAY);
        txtDetItems = new JTextArea(6, 20);
        txtDetItems.setEditable(false);
        txtDetItems.setBackground(new Color(250, 250, 250));
        txtDetItems.setBorder(BorderFactory.createTitledBorder("Detalle del Pedido"));
        
        lblDetTotal = crearLbl("Total: S/. 0.00", Font.BOLD, 22, new Color(33, 37, 41));
        lblDetTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnlDetalleContent.add(pnlCabeceraDetalle);
        pnlDetalleContent.add(Box.createVerticalStrut(15));
        pnlDetalleContent.add(lblDetDir);
        pnlDetalleContent.add(Box.createVerticalStrut(15));
        pnlDetalleContent.add(txtDetItems);
        pnlDetalleContent.add(Box.createVerticalStrut(15));
        pnlDetalleContent.add(lblDetTotal);

        pnlDetalle.add(pnlDetalleContent, BorderLayout.CENTER);

        splitPane.setLeftComponent(scroll);
        splitPane.setRightComponent(pnlDetalle);
        add(splitPane, BorderLayout.CENTER);
    }


    private void accionAvanzar() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return;

        Object[] datos = datosOcultos.get(fila);
        EstadoPedido estadoActual = (EstadoPedido) datos[2];

       
        EstadoPedido nuevoEstado = estadoActual;
        if (estadoActual == EstadoPedido.RESERVADO_WEB) nuevoEstado = EstadoPedido.PENDIENTE;
        else if (estadoActual == EstadoPedido.PENDIENTE) nuevoEstado = EstadoPedido.EN_PREPARACION;
        else if (estadoActual == EstadoPedido.EN_PREPARACION) nuevoEstado = EstadoPedido.LISTO;
        else if (estadoActual == EstadoPedido.LISTO) nuevoEstado = EstadoPedido.ENTREGADO;

        if (nuevoEstado != estadoActual) {
            datos[2] = nuevoEstado; 
            modeloTabla.setValueAt(nuevoEstado, fila, 2); 
            mostrarDetalle(); 
            actualizarEstadisticas(); 
        }
    }

    private void accionCancelar() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) return;

        int confirm = JOptionPane.showConfirmDialog(this, "¿Cancelar pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object[] datos = datosOcultos.get(fila);
            datos[2] = EstadoPedido.CANCELADO;
            modeloTabla.setValueAt(EstadoPedido.CANCELADO, fila, 2);
            mostrarDetalle();
            actualizarEstadisticas();
        }
    }

    private void accionNuevoPedido() {
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        agregarFila("NEW-" + (modeloTabla.getRowCount() + 1), "Cliente Mostrador", EstadoPedido.PENDIENTE, hora, "S/. 15.00", "88888888", "1x Menú del Día", "-", "Mesa 5");
        actualizarEstadisticas();
        JOptionPane.showMessageDialog(this, "Pedido registrado exitosamente.");
    }

    private void agregarFila(String id, String cli, EstadoPedido est, String hora, String tot, String dni, String items, String cor, String dir) {
        modeloTabla.addRow(new Object[]{id, cli, est, hora, tot});
        datosOcultos.add(new Object[]{id, cli, est, hora, tot, dni, items, cor, dir});
    }

    private void mostrarDetalle() {
        int fila = tablaPedidos.getSelectedRow();
        if (fila != -1) {
            Object[] datos = datosOcultos.get(fila);
            
            lblDetCliente.setText(datos[1].toString());
            lblDetDNI.setText("DNI: " + datos[5]);
            lblDetCorreo.setText((String) datos[7]);
            lblDetDir.setText("Dirección: " + datos[8]);
            txtDetItems.setText((String) datos[6]);
            lblDetTotal.setText("Total: " + datos[4]);
            
            String est = datos[2].toString();
            boolean editable = !est.contains("Entregado") && !est.contains("Cancelado");
            btnAvanzar.setEnabled(editable);
            btnCancelar.setEnabled(editable);
            
            if(est.contains("Reserva")) btnAvanzar.setText("Confirmar Pago ->");
            else if(est.contains("Pendiente")) btnAvanzar.setText("Enviar a Cocina ->");
            else if(est.contains("Preparación")) btnAvanzar.setText("Marcar Listo ->");
            else if(est.contains("Listo")) btnAvanzar.setText("Entregar ->");
        }
    }

    private void cargarDatosPrueba() {
        agregarFila("101", "Pablo Rodriguez", EstadoPedido.RESERVADO_WEB, "10:42 a.m.", "S/. 12.50", "12345678", "1x Hamburguesa (S/ 12.50)", "pablo@mail.com", "Av. Arequipa");
        agregarFila("102", "Ana Lopez", EstadoPedido.ENTREGADO, "10:45 a.m.", "S/. 22.00", "87654321", "1x Pizza", "ana@mail.com", "Lima");
    }

    private void actualizarEstadisticas() {
        int total = datosOcultos.size();
        int enPrep = 0;
        int completados = 0;
        for (Object[] d : datosOcultos) {
            String est = d[2].toString();
            if (est.contains("Preparación") || est.contains("Listo")) enPrep++;
            if (est.contains("Entregado")) completados++;
        }
        lblValTotal.setText(String.valueOf(total));
        lblValCocina.setText(String.valueOf(enPrep));
        lblValHoy.setText(String.valueOf(completados));
    }

    private JPanel crearTarjeta(String tit, String sub, String val, Color bg) {
        JPanel c = new JPanel(new BorderLayout(10, 5));
        c.setBackground(bg);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(15, 20, 15, 20)));
        
        JLabel lT = new JLabel(tit); lT.setFont(new Font("Segoe UI", Font.BOLD, 14)); lT.setForeground(Color.GRAY);
        JLabel lV = new JLabel(val); lV.setFont(new Font("Segoe UI", Font.BOLD, 32)); lV.setForeground(new Color(33, 37, 41));
        c.add(lT, BorderLayout.NORTH); c.add(lV, BorderLayout.CENTER); c.add(new JLabel(sub), BorderLayout.SOUTH);
        return c;
    }

    private JLabel crearLbl(String t, int s, int z, Color c) {
        JLabel l = new JLabel(t); l.setFont(new Font("Segoe UI", s, z)); l.setForeground(c); return l;
    }
    
    private void configurarBoton(JButton b, Color bg, Color fg) {
        b.setBackground(bg); b.setForeground(fg); b.setFont(new Font("Segoe UI", Font.BOLD, 12)); b.setFocusPainted(false);
    }
}

