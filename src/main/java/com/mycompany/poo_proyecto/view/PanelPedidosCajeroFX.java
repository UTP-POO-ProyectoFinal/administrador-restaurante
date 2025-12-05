package com.mycompany.poo_proyecto.view;

import com.mycompany.poo_proyecto.model.pedido.EstadoPedido;
import static com.mycompany.poo_proyecto.model.pedido.EstadoPedido.EN_PREPARACION;
import static com.mycompany.poo_proyecto.model.pedido.EstadoPedido.LISTO;
import static com.mycompany.poo_proyecto.model.pedido.EstadoPedido.PENDIENTE;
import static com.mycompany.poo_proyecto.model.pedido.EstadoPedido.RESERVADO_WEB;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.service.ClienteService;
import com.mycompany.poo_proyecto.service.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.beans.binding.Bindings;

public class PanelPedidosCajeroFX {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    private final ObservableList<PedidoViewModel> datosTablaActivos;
    private final ObservableList<PedidoViewModel> datosTablaHistorial;

    private Label lblValTotal;
    private Label lblValCocina;
    private Label lblValHoy;

    private Label lblDetCliente, lblDetDNI, lblDetTotal;
    private TextArea txtDetItems;
    private Button btnAvanzar, btnCancelar;
    private ImageView imgFotoPerfil;

    private TableView<PedidoViewModel> tablaActivos;

    public PanelPedidosCajeroFX() {
        pedidoService = new PedidoService();
        clienteService = new ClienteService();

        datosTablaActivos = FXCollections.observableArrayList();
        datosTablaHistorial = FXCollections.observableArrayList();
    }

    public Parent getVista() {
        lblValTotal = new Label("0");
        lblValTotal.getStyleClass().add("card-value");

        lblValCocina = new Label("0");
        lblValCocina.getStyleClass().add("card-value");

        lblValHoy = new Label("0");
        lblValHoy.getStyleClass().add("card-value");

        lblValTotal.textProperty().bind(
                Bindings.size(datosTablaActivos)
                        .add(Bindings.size(datosTablaHistorial))
                        .asString()
        );

        lblValCocina.textProperty().bind(
                Bindings.createStringBinding(
                        () -> String.valueOf(
                                datosTablaActivos.stream()
                                        .filter(p -> {
                                            String upper = p.getEstadoStr().toUpperCase();
                                            return upper.contains("PREPARA") || upper.contains("LISTO");
                                        })
                                        .count()
                        ),
                        datosTablaActivos
                )
        );
        lblValHoy.textProperty().bind(
                Bindings.createStringBinding(
                        () -> String.valueOf(
                                datosTablaHistorial.stream()
                                        .filter(p -> p.getEstadoStr().toUpperCase().contains("ENTREGADO"))
                                        .count()
                        ),
                        datosTablaHistorial
                )
        );
        Label lblTitulo = new Label("Gestión de Pedidos");
        lblTitulo.getStyleClass().add("header-title");

        Button btnNuevo = new Button("+ Nuevo Pedido");
        btnNuevo.getStyleClass().add("btn-nuevo");
        btnNuevo.setOnAction(e -> accionNuevoPedido());

        HBox topBar = new HBox(lblTitulo, new Region(), btnNuevo);
        HBox.setHgrow(topBar.getChildren().get(1), Priority.ALWAYS);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 25, 0));

        HBox cardsPanel = new HBox(30);
        cardsPanel.setAlignment(Pos.CENTER);
        cardsPanel.setPadding(new Insets(0, 0, 30, 0));

        VBox cardTotal = crearTarjetaModerna("Total Pedidos", lblValTotal);
        cardTotal.getStyleClass().add("card-total");

        VBox cardProceso = crearTarjetaModerna("En Proceso", lblValCocina);
        cardProceso.getStyleClass().add("card-proceso");

        VBox cardCompletados = crearTarjetaModerna("Completados Hoy", lblValHoy);
        cardCompletados.getStyleClass().add("card-completados");

        cardsPanel.getChildren().addAll(cardTotal, cardProceso, cardCompletados);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        tablaActivos = crearTablaBase();
        tablaActivos.setItems(datosTablaActivos);
        tablaActivos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> mostrarDetalle(newVal));

        VBox pnlDetalle = crearPanelDetalleModerno();

        ScrollPane scrollDetalle = new ScrollPane(pnlDetalle);
        scrollDetalle.setFitToWidth(true);
        scrollDetalle.setStyle("-fx-background-color: transparent; -fx-background: white;");
        scrollDetalle.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        SplitPane splitActivos = new SplitPane(tablaActivos, scrollDetalle);
        splitActivos.setDividerPositions(0.7);
        splitActivos.setStyle("-fx-background-color: transparent; -fx-padding: 10px 0 0 0;");

        Tab tabEnCurso = new Tab("🚀 Pedidos En Curso", splitActivos);

        TableView<PedidoViewModel> tablaHistorial = crearTablaBase();
        tablaHistorial.setItems(datosTablaHistorial);

        VBox containerHistorial = new VBox(tablaHistorial);
        containerHistorial.setPadding(new Insets(10, 0, 0, 0));
        Tab tabHistorial = new Tab("📜 Historial (Entregados/Cancelados)", containerHistorial);

        tabPane.getTabs().addAll(tabEnCurso, tabHistorial);

        VBox root = new VBox();
        root.getStyleClass().add("root-panel");
        root.setPadding(new Insets(30));
        root.getChildren().addAll(topBar, cardsPanel, tabPane);

        try {
            root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
          
        }

        cargarPedidosDesdeBD(); 

        return root;
    }

    public void cargarPedidosDesdeBD() {
        datosTablaActivos.clear();
        datosTablaHistorial.clear();

        List<Pedido> lista = pedidoService.listarPedidos();

        if (lista != null) {
            for (Pedido p : lista) {
                Cliente c = clienteService.buscarPorId(p.getIdCliente());
                String nombre = (c != null) ? c.getNombre() + " " + c.getApellido() : "Desconocido";
                String dni = (c != null) ? c.getCodigoUTP() : "---";
                String hora = p.getFechaHora().format(DateTimeFormatter.ofPattern("hh:mm a"));

                EstadoPedido estado = EstadoPedido.PENDIENTE;
                try {
                    estado = EstadoPedido.valueOf(p.getEstado());
                } catch (Exception e) {
                }

                PedidoViewModel viewModel = new PedidoViewModel(p, nombre, dni, hora, estado);

                if (estado == EstadoPedido.ENTREGADO || estado == EstadoPedido.CANCELADO) {
                    datosTablaHistorial.add(viewModel);
                } else {
                    datosTablaActivos.add(viewModel);
                }
            }
        }

        tablaActivos.refresh();
    }

    /**
     * private void actualizarEstadisticas() { int total =
     * datosTablaActivos.size() + datosTablaHistorial.size();
     *
     * long enPrep = datosTablaActivos.stream().filter(p ->
     * p.getEstadoStr().toUpperCase().contains("PREPARA") ||
     * p.getEstadoStr().toUpperCase().contains("LISTO") ).count();
     *
     * long completados = datosTablaHistorial.stream().filter(p ->
     * p.getEstadoStr().toUpperCase().contains("ENTREGADO") ).count();
     *
     * // Aquí actualizamos los labels que ahora SÍ existen if (lblValTotal !=
     * null) { lblValTotal.setText(String.valueOf(total)); } if (lblValCocina !=
     * null) { lblValCocina.setText(String.valueOf(enPrep)); } if (lblValHoy !=
     * null) { lblValHoy.setText(String.valueOf(completados)); } }
     *
     */
    private void mostrarDetalle(PedidoViewModel item) {
        if (item == null) {
            lblDetCliente.setText("Seleccione un pedido");
            lblDetDNI.setText("Código: -");
            txtDetItems.clear();
            lblDetTotal.setText("Total: S/. 0.00");
            btnAvanzar.setDisable(true);
            btnCancelar.setDisable(true);
            imagenSinPerfil();
            return;
        }

        lblDetCliente.setText(item.nombreCliente);
        lblDetDNI.setText("Código: " + item.dniCliente);
        txtDetItems.setText(item.pedidoReal.getTipo());
        lblDetTotal.setText(item.getTotalStr());

        if (item.getNombreCliente().contains("Ana") && item.getNombreCliente().contains("Lopez")) {
            cargarFotoEspecifica("/images/ana_lopez.png");
        } else {
            imagenSinPerfil();
        }

        boolean editable = (item.estado != EstadoPedido.ENTREGADO && item.estado != EstadoPedido.CANCELADO);
        btnAvanzar.setDisable(!editable);
        btnCancelar.setDisable(!editable);

        switch (item.estado) {
            case RESERVADO_WEB ->
                btnAvanzar.setText("Confirmar Llegada ->");
            case PENDIENTE ->
                btnAvanzar.setText("Enviar a Cocina ->");
            case EN_PREPARACION ->
                btnAvanzar.setText("Marcar Listo ->");
            case LISTO ->
                btnAvanzar.setText("Entregar Pedido ->");
            default ->
                btnAvanzar.setText("Completado");
        }
    }

    private void accionAvanzar() {
        PedidoViewModel item = tablaActivos.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        pedidoService.avanzarEstado(item.pedidoReal);
        cargarPedidosDesdeBD();
    }

    private void accionCancelar() {
        PedidoViewModel item = tablaActivos.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro de cancelar?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            pedidoService.cancelarPedido(item.pedidoReal);
            cargarPedidosDesdeBD();
        }
    }

    private void accionNuevoPedido() {
        FormularioPedidoFX formulario = new FormularioPedidoFX();
        formulario.showAndWait();
        if (formulario.isGuardadoExitoso()) {
            cargarPedidosDesdeBD();
        }
    }

    private void accionCambiarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            setFotoPerfilManual(selectedFile.toURI().toString());
        }
    }

    private VBox crearTarjetaModerna(String titulo, Label lblValor) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card-panel");
        Label lblTit = new Label(titulo);
        lblTit.getStyleClass().add("card-title");
        card.getChildren().addAll(lblTit, lblValor);
        return card;
    }

    private TableView<PedidoViewModel> crearTablaBase() {
        TableView<PedidoViewModel> t = new TableView<>();
        t.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        t.getStyleClass().add("table-view");

        TableColumn<PedidoViewModel, String> colId = new TableColumn<>("Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("idVisual"));

        colId.setMinWidth(80);
        colId.setMaxWidth(90);

        TableColumn<PedidoViewModel, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colCliente.setMinWidth(150);
        colCliente.setPrefWidth(180);

        TableColumn<PedidoViewModel, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoStr"));

        colEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    String upper = item.toUpperCase();
                    if (upper.contains("PENDIENTE")) {
                        setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;"); // Rojo
                    } else if (upper.contains("LISTO") || upper.contains("ENTREGADO")) {
                        setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;"); // Verde
                    } else if (upper.contains("PREPARA")) { // Busca palabra clave para Naranja
                        setStyle("-fx-text-fill: #f57c00; -fx-font-weight: bold;");
                    } else if (upper.contains("CANCELADO")) {
                        setStyle("-fx-text-fill: #b71c1c; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #333;");
                    }
                }
            }
        });
        colEstado.setMinWidth(150);
        colEstado.setPrefWidth(160);

        TableColumn<PedidoViewModel, String> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colHora.setMinWidth(110);
        colHora.setPrefWidth(120);

        TableColumn<PedidoViewModel, String> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalStr"));
        colTotal.setStyle("-fx-alignment: CENTER-RIGHT;");
        colTotal.setMinWidth(120);
        colTotal.setPrefWidth(130);

        t.getColumns().addAll(colId, colCliente, colEstado, colHora, colTotal);
        for (TableColumn<PedidoViewModel, ?> col : t.getColumns()) {
            col.setResizable(false);
            col.setReorderable(false);
        }
        return t;
    }

    private VBox crearPanelDetalleModerno() {
        VBox p = new VBox(15);
        p.getStyleClass().add("detail-panel");
        p.setPrefWidth(300);

        imgFotoPerfil = new ImageView();
        imgFotoPerfil.setFitWidth(104);
        imgFotoPerfil.setFitHeight(104);
        imgFotoPerfil.setPreserveRatio(false);
        imgFotoPerfil.setSmooth(true);

        Circle clip = new Circle(52, 52, 52);
        imgFotoPerfil.setClip(clip);

        StackPane imageContainer = new StackPane(imgFotoPerfil);
        imageContainer.getStyleClass().add("profile-image-container");
        imageContainer.setMinSize(104, 104);
        imageContainer.setMaxSize(104, 104);

        imagenSinPerfil();

        Button btnCambiarFoto = new Button("Cambiar foto");
        btnCambiarFoto.setStyle("-fx-font-size: 10px; -fx-background-color: #eee; -fx-cursor: hand;");
        btnCambiarFoto.setOnAction(e -> accionCambiarFoto());

        VBox photoSection = new VBox(5, imageContainer, btnCambiarFoto);
        photoSection.setAlignment(Pos.CENTER);

        lblDetCliente = new Label("Seleccione un pedido");
        lblDetCliente.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        lblDetCliente.setWrapText(true);

        lblDetDNI = new Label("Código: -");
        lblDetDNI.setStyle("-fx-text-fill: #666;");

        txtDetItems = new TextArea();
        txtDetItems.setEditable(false);
        txtDetItems.setWrapText(true);
        txtDetItems.setStyle("-fx-control-inner-background: #f9f9f9;");
        VBox.setVgrow(txtDetItems, Priority.ALWAYS);

        lblDetTotal = new Label("Total: S/. 0.00");
        lblDetTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        lblDetTotal.setAlignment(Pos.CENTER_RIGHT);
        lblDetTotal.setMaxWidth(Double.MAX_VALUE);

        btnAvanzar = new Button("Avanzar Estado ->");
        btnAvanzar.setMaxWidth(Double.MAX_VALUE);
        btnAvanzar.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnAvanzar.setOnAction(e -> accionAvanzar());

        btnCancelar = new Button("Cancelar Pedido");
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setStyle("-fx-background-color: #ffcdd2; -fx-text-fill: #c62828; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> accionCancelar());

        btnAvanzar.setDisable(true);
        btnCancelar.setDisable(true);

        p.getChildren().addAll(
                photoSection,
                lblDetCliente, lblDetDNI,
                new Separator(),
                new Label("Detalle:"), txtDetItems,
                new Separator(),
                lblDetTotal,
                new VBox(10, btnAvanzar, btnCancelar)
        );

        return p;
    }

    public void setFotoPerfilManual(String urlRutaAbsoluta) {
        try {
            imgFotoPerfil.setImage(new Image(urlRutaAbsoluta));
        } catch (Exception e) {
        }
    }

    private void cargarFotoEspecifica(String rutaRecurso) {
        try {
            String imagePath = getClass().getResource(rutaRecurso).toExternalForm();
            imgFotoPerfil.setImage(new Image(imagePath));
        } catch (Exception e) {
        }
    }

    private void imagenSinPerfil() {
        cargarFotoEspecifica("/images/imagen_sin_perfil.png");
    }

    public static class PedidoViewModel {

        Pedido pedidoReal;
        String nombreCliente, dniCliente, hora;
        EstadoPedido estado;

        public PedidoViewModel(Pedido p, String n, String d, String h, EstadoPedido e) {
            this.pedidoReal = p;
            this.nombreCliente = n;
            this.dniCliente = d;
            this.hora = h;
            this.estado = e;
        }

        public String getIdVisual() {
            return "P-" + pedidoReal.getIdPedido();
        }

        public String getNombreCliente() {
            return nombreCliente;
        }

        public String getEstadoStr() {
            return estado.toString();
        }

        public String getHora() {
            return hora;
        }

        public String getTotalStr() {
            return String.format("S/. %.2f", pedidoReal.getTotal());
        }
    }
}
