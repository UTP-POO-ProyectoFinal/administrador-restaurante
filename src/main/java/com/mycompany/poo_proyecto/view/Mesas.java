package com.mycompany.poo_proyecto.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;
import javafx.application.Platform;

public class Mesas {

    private final Image imgMesaLibre = new Image(getClass().getResourceAsStream("/images/mesad.png"));
    private final Image imgMesaSeleccionada = new Image(getClass().getResourceAsStream("/images/mesaselected.png"));
    private final Image imgMesaOcupada = new Image(getClass().getResourceAsStream("/images/mesaOcupada.png"));
    private final Image imgMostrador = new Image(getClass().getResourceAsStream("/images/mostrador.png"));
    private final Image imgPerfil = new Image(getClass().getResourceAsStream("/images/imagen_sin_perfil.png"));

    private final ToggleGroup toggleGroupMesas = new ToggleGroup();
    private final List<ToggleButton> listaMesas = new ArrayList<>();

    private Label lblNumeroMesa;
    private Label lblEstado;
    private Label lblCliente;
    private Label lblCapacidad;

    private Label lblTiempoRestante;

    private Button btnOcupar;
    private Button btnLiberar;
    private VBox panelDetallesContent;
    private Label lblInstruccion;

    public Node getView() {
        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setPadding(new Insets(10));
        panelIzquierdo.setStyle("-fx-background-color: #f0f2f5;");

        HBox leyenda = crearLeyenda();

        Pane mapaMesas = new Pane();
        mapaMesas.setPrefSize(1000, 500);
        mapaMesas.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        cargarMesasEnMapa(mapaMesas);

        ScrollPane scrollMapa = new ScrollPane(mapaMesas);
        scrollMapa.setFitToWidth(true);
        scrollMapa.setFitToHeight(true);
        scrollMapa.setStyle("-fx-background-color: transparent;");

        panelIzquierdo.getChildren().addAll(leyenda, scrollMapa);

        VBox panelDerecho = crearPanelDerecho();

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(panelIzquierdo, panelDerecho);
        splitPane.setDividerPositions(0.75);

        toggleGroupMesas.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                mostrarDetalles(null);
            } else {
                ToggleButton btnSeleccionado = (ToggleButton) newVal;
                InfoMesa info = (InfoMesa) btnSeleccionado.getUserData();
                mostrarDetalles(info);
            }
        });

        return splitPane;
    }

    private VBox crearPanelDerecho() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: white;");
        panel.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label("Detalles de Mesa");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        lblInstruccion = new Label("Seleccione una mesa para ver información.");
        lblInstruccion.setWrapText(true);
        lblInstruccion.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        panelDetallesContent = new VBox(15);
        panelDetallesContent.setAlignment(Pos.TOP_CENTER);
        panelDetallesContent.setVisible(false);

        ImageView ivPerfil = new ImageView(imgPerfil);
        ivPerfil.setFitWidth(80);
        ivPerfil.setFitHeight(80);
        VBox.setMargin(ivPerfil, new Insets(0, 0, 10, 0));

        lblNumeroMesa = crearLabelDato("Mesa #", "Unknown");
        lblEstado = crearLabelDato("Estado:", "Disponible");
        lblCliente = crearLabelDato("Reservado por:", "-");
        lblCapacidad = crearLabelDato("Capacidad:", "4 Personas");

        lblTiempoRestante = new Label("Tiempo: --:--");
        lblTiempoRestante.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");
        lblTiempoRestante.setVisible(false); 

        btnOcupar = new Button("OCUPAR MESA (30 min)");
        btnOcupar.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnOcupar.setMaxWidth(Double.MAX_VALUE);

        btnLiberar = new Button("LIBERAR / DISPONIBLE");
        btnLiberar.setStyle("-fx-background-color: #51cf66; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnLiberar.setMaxWidth(Double.MAX_VALUE);

        btnOcupar.setOnAction(e -> cambiarEstadoMesaActual(true));
        btnLiberar.setOnAction(e -> cambiarEstadoMesaActual(false));

        panelDetallesContent.getChildren().addAll(
                ivPerfil,
                lblNumeroMesa,
                lblEstado,
                lblCapacidad,
                lblTiempoRestante,
                new javafx.scene.control.Separator(),
                lblCliente,
                new javafx.scene.control.Separator(),
                btnOcupar,
                btnLiberar
        );

        panel.getChildren().addAll(titulo, lblInstruccion, panelDetallesContent);
        return panel;
    }

    private void mostrarDetalles(InfoMesa info) {
        if (info == null) {
            panelDetallesContent.setVisible(false);
            lblInstruccion.setVisible(true);
            return;
        }

        lblInstruccion.setVisible(false);
        panelDetallesContent.setVisible(true);

        lblNumeroMesa.setText("Mesa #" + info.numero);
        lblCapacidad.setText("Capacidad: " + info.capacidad + " personas");

        actualizarUIEstado(info);
    }

    private void actualizarUIEstado(InfoMesa info) {
        if (info.ocupada) {
            lblEstado.setText("Estado: OCUPADA");
            lblEstado.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
            lblCliente.setText("Reservado por: " + (info.clienteNombre.isEmpty() ? "Cliente Casual" : info.clienteNombre));

            lblTiempoRestante.setVisible(true);
            lblTiempoRestante.setText("Tiempo: " + info.tiempoStr);

            btnOcupar.setDisable(true);
            btnLiberar.setDisable(false);
        } else {
            lblEstado.setText("Estado: DISPONIBLE");
            lblEstado.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
            lblCliente.setText("Reservado por: -");

            lblTiempoRestante.setVisible(false);

            btnOcupar.setDisable(false);
            btnLiberar.setDisable(true);
        }
    }

    private void cambiarEstadoMesaActual(boolean ocupar) {
        ToggleButton btnSeleccionado = (ToggleButton) toggleGroupMesas.getSelectedToggle();
        if (btnSeleccionado != null) {
            InfoMesa info = (InfoMesa) btnSeleccionado.getUserData();

            if (ocupar) {
                ocuparMesaLogica(info, btnSeleccionado);
            } else {
                liberarMesaLogica(info, btnSeleccionado);
            }

            actualizarUIEstado(info);
        }
    }

    private void ocuparMesaLogica(InfoMesa info, ToggleButton btnAsociado) {
        info.ocupada = true;
        info.clienteNombre = "Cliente Casual";

        
        info.temporizador = new TemporizadorMesa(1, //Modificar a 30 cuando termines de testear
                (tiempoFormateado) -> {
                    info.tiempoStr = tiempoFormateado;

                    if (toggleGroupMesas.getSelectedToggle() == btnAsociado) {
                        lblTiempoRestante.setText("Tiempo: " + tiempoFormateado);
                    }
                },
                () -> {
                    Platform.runLater(() -> liberarMesaLogica(info, btnAsociado));
                    NotificacionTiempo.mostrar(info.numero);
                }
        );
        info.temporizador.iniciar();
    }

    private void liberarMesaLogica(InfoMesa info, ToggleButton btnAsociado) {
        info.ocupada = false;
        info.clienteNombre = "";
        info.tiempoStr = "--:--";

        if (info.temporizador != null) {
            info.temporizador.detener();
            info.temporizador = null;
        }

        ImageView iv = (ImageView) btnAsociado.getGraphic();
        if (!btnAsociado.isSelected()) {
            iv.setImage(imgMesaLibre);
        }

        if (toggleGroupMesas.getSelectedToggle() == btnAsociado) {
            actualizarUIEstado(info);
        }
    }

    private Label crearLabelDato(String titulo, String valorInicial) {
        Label l = new Label(titulo + " " + valorInicial);
        l.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        return l;
    }

   
    private void cargarMesasEnMapa(Pane mapaMesas) {
       
        agregarMostrador(mapaMesas, 40, 100, 60, 300);
        agregarMostrador(mapaMesas, 40, 50, 240, 50);

        agregarMesa(mapaMesas, "1", 280, 230, 2);

        agregarMesa(mapaMesas, "2", 350, 290, 4);
        agregarMesa(mapaMesas, "3", 350, 170, 4);

        agregarMesa(mapaMesas, "4", 420, 340, 4);
        agregarMesa(mapaMesas, "5", 420, 230, 4);
        agregarMesa(mapaMesas, "6", 420, 110, 4);

        agregarMesa(mapaMesas, "7", 490, 290, 6);
        agregarMesa(mapaMesas, "8", 490, 170, 6);

        agregarMesa(mapaMesas, "9", 560, 340, 2);
        agregarMesa(mapaMesas, "10", 560, 230, 2);
        agregarMesa(mapaMesas, "11", 560, 110, 2);

        agregarMesa(mapaMesas, "12", 620, 290, 4);
        agregarMesa(mapaMesas, "13", 620, 170, 4);

        agregarMesa(mapaMesas, "14", 680, 340, 4);
        agregarMesa(mapaMesas, "15", 680, 230, 4);
        agregarMesa(mapaMesas, "16", 680, 110, 4);

        agregarMesa(mapaMesas, "17", 730, 290, 2);
        agregarMesa(mapaMesas, "18", 730, 170, 2);

        agregarMesa(mapaMesas, "19", 790, 340, 4);
        agregarMesa(mapaMesas, "20", 790, 230, 4);
        agregarMesa(mapaMesas, "21", 790, 110, 4);

        agregarMesa(mapaMesas, "22", 840, 290, 4);
        agregarMesa(mapaMesas, "23", 840, 170, 4);

        agregarMesa(mapaMesas, "24", 900, 340, 8);
        agregarMesa(mapaMesas, "25", 900, 230, 8);
        agregarMesa(mapaMesas, "26", 900, 110, 8);
    
    }

    private void agregarMesa(Pane parent, String numero, double x, double y, int capacidad) {
        ToggleButton btn = new ToggleButton();
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setPrefSize(40, 40);
        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        InfoMesa info = new InfoMesa(numero, capacidad);
        btn.setUserData(info);

        ImageView iv = new ImageView(imgMesaLibre);
        btn.setGraphic(iv);

        btn.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            InfoMesa currentInfo = (InfoMesa) btn.getUserData();
            if (isSelected) {
                iv.setImage(imgMesaSeleccionada);
            } else {
                iv.setImage(currentInfo.ocupada ? imgMesaOcupada : imgMesaLibre);
            }
        });

        btn.setToggleGroup(toggleGroupMesas);
        parent.getChildren().add(btn);
        listaMesas.add(btn);
    }

    private void agregarMostrador(Pane parent, double x, double y, double w, double h) {
        ImageView iv = new ImageView(imgMostrador);
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        parent.getChildren().add(iv);
    }

    private HBox crearLeyenda() {
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        hbox.getChildren().addAll(
                crearItemLeyenda(imgMesaLibre, "Disponible"),
                crearItemLeyenda(imgMesaSeleccionada, "Seleccionada"),
                crearItemLeyenda(imgMesaOcupada, "Ocupada")
        );
        return hbox;
    }

    private HBox crearItemLeyenda(Image img, String texto) {
        HBox item = new HBox(10);
        item.setAlignment(Pos.CENTER_LEFT);
        ImageView iv = new ImageView(img);
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        Label lbl = new Label(texto);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        item.getChildren().addAll(iv, lbl);
        return item;
    }

    private static class InfoMesa {

        String numero;
        int capacidad;
        boolean ocupada;
        String clienteNombre;

        TemporizadorMesa temporizador;
        String tiempoStr = "--:--";

        public InfoMesa(String numero, int capacidad) {
            this.numero = numero;
            this.capacidad = capacidad;
            this.ocupada = false;
            this.clienteNombre = "";
        }
    }
}
