package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.model.usuario.Usuario;
import com.mycompany.poo_proyecto.service.UsuarioService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFX extends Stage{

    private final UsuarioService usuarioService;
    private Stage loginStage; 

    public LoginFX() {
        this.usuarioService = new UsuarioService();
    }

    public void start(Stage stage) {
        this.loginStage = stage; 
        
        Label lblTitulo = new Label("Cafeteria UTP");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario de Sistema (Ej: YixSoviet)");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");

        Button btnIngresar = new Button("INICIAR SESIÓN");
        btnIngresar.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);

        btnIngresar.setOnAction(e -> {
            String user = txtUsuario.getText();
            String pass = txtPass.getText();

            Usuario personal = usuarioService.loginPersonal(user, pass);

            if (personal != null) {
                abrirDashboardSegunRol(personal);
            } else {
                mostrarAlerta("Error", "Credenciales incorrectas o usuario no encontrado.");
            }
        });

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lblTitulo, new Label("Acceso Administrativo"), txtUsuario, txtPass, btnIngresar);

        Scene scene = new Scene(root, 350, 400);
        stage.setTitle("Login - Cafetería UTP");
        stage.setScene(scene);
        stage.show();
    }

    private void abrirDashboardSegunRol(Usuario usuario) {
        String rol = usuario.getRol().toString();
        
        loginStage.close(); 

        if (rol.equals("CAJERO")) {
            new MainCajeroFX().start(new Stage()); 
        } else if (rol.equals("ADMINISTRADOR")) {
            new MainAdminFX().show();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
