package com.mycompany.poo_proyecto;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppEscritorio extends Application {

    @Override
    public void start(Stage stage) {
        new com.mycompany.poo_proyecto.view.LoginFX().start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
