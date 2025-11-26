package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.model.usuario.Usuario;
import com.mycompany.poo_proyecto.service.ClienteService;
import com.mycompany.poo_proyecto.service.UsuarioService;
import com.mycompany.poo_proyecto.utils.EmailService;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public Login() {
        clienteService = new ClienteService();
        usuarioService = new UsuarioService();

        initUI();
    }

    private void initUI() {
        setTitle("Cafetería UTP - Acceso");
        setSize(350, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        JLabel lblTitulo = new JLabel("<html><center>CAFETERÍA<br>UTP</center></html>", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(200, 40, 40));
        lblTitulo.setBounds(25, 20, 300, 80);
        add(lblTitulo);

        JLabel lblUser = new JLabel("Código UTP:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setBounds(40, 120, 200, 20);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 145, 260, 35);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPass.setBounds(40, 190, 200, 20);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 215, 260, 35);
        add(txtPassword);

        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setBounds(40, 280, 260, 45);
        btnIngresar.setBackground(new Color(220, 20, 60));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setOpaque(true);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(this::procesarLogin);
        add(btnIngresar);

        JSeparator sep = new JSeparator();
        sep.setBounds(40, 350, 260, 10);
        add(sep);

        JLabel lblNuevo = new JLabel("¿Eres alumno nuevo?");
        lblNuevo.setBounds(40, 370, 150, 20);
        add(lblNuevo);

        JButton btnRegistro = new JButton("Regístrate aquí");
        btnRegistro.setBounds(160, 370, 140, 20);
        btnRegistro.setBorderPainted(false);
        btnRegistro.setContentAreaFilled(false);
        btnRegistro.setForeground(new Color(0, 102, 204));
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistro.addActionListener(e -> mostrarRegistro());
        add(btnRegistro);
    }

    private void procesarLogin(ActionEvent e) {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los campos.");
            return;
        }

        Cliente cliente = clienteService.login(user, pass);
        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "¡Hola " + cliente.getNombre() + "!\nBienvenido al menú.");
            //aquí iria ell menu del cliente
            //new MenuCliente(cliente).setVisible(true);
            //this.dispose();
            return;
        }

        Usuario personal = usuarioService.loginPersonal(user, pass);
        if (personal != null) {
            String rol = personal.getRol().toString();
            JOptionPane.showMessageDialog(this, "Acceso concedido: " + rol + "\nUsuario: " + personal.getNombre());

            //aqui iria el menu correspondiente al personal q ingrese
            //if (rol.equals("ADMINISTRADOR")) new DashboardAdmin().setVisible(true);
            //if (rol.equals("CAJERO")) new CajaView().setVisible(true);
            //this.dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarRegistro() {
        String correo = JOptionPane.showInputDialog(this, "Ingresa tu correo institucional (@utp.edu.pe):");
        if (correo == null || correo.trim().isEmpty()) {
            return;
        }

        if (!correo.endsWith("@utp.edu.pe")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten correos de la UTP.");
            return;
        }

        EmailService emailService = new EmailService();
        String codigoGenerado = emailService.generarCodigo();

        JOptionPane.showMessageDialog(this, "Enviando código a " + correo + "...");
        boolean enviado = emailService.enviarCodigoVerificacion(correo, codigoGenerado);

        if (enviado) {
            String codigoIngresado = JOptionPane.showInputDialog(this, "Código enviado. Ingrésalo:");

            if (codigoIngresado != null && codigoIngresado.equals(codigoGenerado)) {
                abrirFormularioDatos(correo);
            } else {
                JOptionPane.showMessageDialog(this, "Código incorrecto.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al enviar correo. Verifica tu conexión.");
        }
    }

    private void abrirFormularioDatos(String correo) {
        String codigoSugerido = correo.split("@")[0];

        JTextField txtNombre = new JTextField();
        JTextField txtTel = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        Object[] message = {
            "Código UTP:", codigoSugerido,
            "Nombre Completo:", txtNombre,
            "Teléfono:", txtTel,
            "Crea tu contraseña:", txtPass,
            "Dirección:", new JTextField("Universidad") //consideero q estaa info es un poco innecesaria
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Terminar Registro", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int telefono = Integer.parseInt(txtTel.getText());
                String pass = new String(txtPass.getPassword());

                boolean exito = clienteService.registrarEstudiante(
                        codigoSugerido,
                        txtNombre.getText(),
                        pass,
                        telefono,
                        "UTP Piura"
                );

                if (exito) {
                    JOptionPane.showMessageDialog(this, "¡Registro exitoso! Ahora inicia sesión. Bienvenido a la BETA");
                } else {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos (El teléfono debe ser números).");
            }
        }
    }
}

/**
 * private final int resolutionW = 800; private final int resolutionH = 450;
 *
 * JButton btnClose;
 *
 * public Login() { this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * this.setSize(resolutionH, resolutionW); this.setLocationRelativeTo(null);
 * this.setLayout(null); this.setResizable(false);
 *
 * btnClose = new JButton("Close"); btnClose.setBounds((resolutionH / 2) - (220
 * / 2), (resolutionW / 2) - (50 / 2), 220, 50); btnClose.addActionListener(new
 * ActionListener() { public void actionPerformed(ActionEvent evt) {
 * btnCloseActionPerformed(evt); } });
 *
 * this.add(btnClose);
 *
 * this.setVisible(true);
 *
 * }
 *
 * public void btnCloseActionPerformed(ActionEvent args) {
 * this.setVisible(false); this.dispose(); } }
*
 */
