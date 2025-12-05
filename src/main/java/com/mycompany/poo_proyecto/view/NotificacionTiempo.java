package com.mycompany.poo_proyecto.view;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
public class NotificacionTiempo {
    
    public static void mostrar(String numeroMesa) {
        
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true); 
        Label lblTitulo = new Label("¡Tiempo Finalizado!");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #ff6b6b;");

        Label lblMensaje = new Label("La mesa " + numeroMesa + " ha culminado su tiempo.\nRevisar mesa para su correcta rotación.");
        lblMensaje.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        lblMensaje.setWrapText(true);

        VBox textBox = new VBox(5, lblTitulo, lblMensaje);
        textBox.setAlignment(Pos.CENTER_LEFT);
        Button btnCerrar = new Button("X");
        btnCerrar.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #888; " +
            "-fx-font-weight: bold; " +
            "-fx-cursor: hand; " +
            "-fx-border-color: #555; " +
            "-fx-border-radius: 3;"
        );
        btnCerrar.setOnAction(e -> stage.close());
        btnCerrar.setOnMouseEntered(e -> btnCerrar.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-border-color: #666; -fx-border-radius: 3;"));
        btnCerrar.setOnMouseExited(e -> btnCerrar.setStyle("-fx-background-color: transparent; -fx-text-fill: #888; -fx-border-color: #555; -fx-border-radius: 3;"));

        HBox root = new HBox(10, textBox, btnCerrar);
        root.setAlignment(Pos.TOP_RIGHT);
        root.setPadding(new javafx.geometry.Insets(15));
        root.setStyle(
            "-fx-background-color: rgba(30, 30, 30, 0.95);" + 
            "-fx-background-radius: 10;" +
            "-fx-border-color: #ff6b6b;" +
            "-fx-border-width: 0 0 0 3;" + 
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"
        );

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
       
        double notificationWidth = 320; 
        double notificationHeight = 100; 
        
        double margin = 20;
        stage.setX(screenBounds.getMinX() + margin); 
        stage.setY(screenBounds.getMaxY() - notificationHeight - margin); 
        PauseTransition espera = new PauseTransition(Duration.seconds(7));
        
        FadeTransition desvanecer = new FadeTransition(Duration.seconds(3), root);
        desvanecer.setFromValue(1.0);
        desvanecer.setToValue(0.0);

        SequentialTransition secuencia = new SequentialTransition(espera, desvanecer);
        secuencia.setOnFinished(e -> stage.close()); 
        secuencia.play();

        stage.show();
    }
}
