package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.model.usuario.Usuario;
import com.mycompany.poo_proyecto.service.UsuarioService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private final UsuarioService usuarioService;

    public Login() {
        usuarioService = new UsuarioService();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión - Cafetería UTP ");
        setSize(350, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        JLabel lblTitulo = new JLabel("<html><center>ACCESO<br>ADMINISTRATIVO</center></html>", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBounds(25, 30, 300, 60);
        add(lblTitulo);

        JLabel lblSub = new JLabel("Solo personal autorizado");
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(25, 90, 300, 20);
        add(lblSub);

        JLabel lblUser = new JLabel("Usuario de Sistema:");
        lblUser.setBounds(40, 130, 200, 20);
        add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 145, 260, 35);
        add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(40, 200, 200, 20);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 225, 260, 35);
        add(txtPassword);

        JButton btnIngresar = new JButton("INICIAR SESIÓN");
        btnIngresar.setBounds(40, 290, 260, 45);
        btnIngresar.setBackground(new Color(220, 20, 60)); // Rojo UTP
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.addActionListener(this::procesarLogin);
        add(btnIngresar);
    }

    private void procesarLogin(ActionEvent e) {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese sus credenciales.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario personal = usuarioService.loginPersonal(user, pass);

        if (personal != null) {
            String rol = personal.getRol().toString();
            JOptionPane.showMessageDialog(this, "Bienvenido " + personal.getNombre() + "\nRol: " + rol);

            if (rol.equals("ADMINISTRADOR")) {
                //new DashboardAdmin().setVisible(true);
            } else if (rol.equals("CAJERO")) {
                new MainCajero().setVisible(true);
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Acceso denegado.\nUsuario no encontrado o clave incorrecta.", "Error de Seguridad", JOptionPane.ERROR_MESSAGE);
        }
    }
}
