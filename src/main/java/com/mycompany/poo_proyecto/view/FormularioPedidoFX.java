package com.mycompany.poo_proyecto.view;
import com.mycompany.poo_proyecto.model.pedido.EstadoPedido;
import com.mycompany.poo_proyecto.model.pedido.Pedido;
import com.mycompany.poo_proyecto.model.usuario.Cliente;
import com.mycompany.poo_proyecto.service.ClienteService;
import com.mycompany.poo_proyecto.service.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.*;
import java.util.stream.Collectors;

public class FormularioPedidoFX extends Stage {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    
    private Cliente clienteSeleccionado = null;
    private final ObservableList<ItemCarrito> itemsCarrito = FXCollections.observableArrayList();
    private final ObservableList<ProductoDummy> catalogoProductos = FXCollections.observableArrayList();
    private boolean guardadoExitoso = false;

    private Label lblClienteEstado;
    private Label lblTotal;
    private ListView<ProductoDummy> listaProductos;
    private TextField txtBuscarProducto;
    
    private TextField txtNewCodigo, txtNewDni, txtNewNombre, txtNewApellido, txtNewCorreo, txtNewTel, txtNewDir;

    public FormularioPedidoFX() {
        this.pedidoService = new PedidoService();
        this.clienteService = new ClienteService();
        cargarCatalogo(); 
        initUI();
    }

    private void cargarCatalogo() {
        catalogoProductos.addAll(
            new ProductoDummy("Menú Estudiantil (Lentejas)", 8.50),
            new ProductoDummy("Hamburguesa Royal", 15.00),
            new ProductoDummy("Pizza Americana", 18.00),
            new ProductoDummy("Gaseosa 500ml", 3.50),
            new ProductoDummy("Agua Mineral", 2.50),
            new ProductoDummy("Café Pasado", 4.00),
            new ProductoDummy("Café Americano", 5.00),
            new ProductoDummy("Café Latte", 7.50),
            new ProductoDummy("Capuchino", 7.00),
            new ProductoDummy("Mocaccino", 8.00),
            new ProductoDummy("Chocolate Caliente", 6.00),
            new ProductoDummy("Té Verde", 4.00),
            new ProductoDummy("Frappé de Café", 9.50),
            new ProductoDummy("Sandwich de Pollo", 8.50),
            new ProductoDummy("Sandwich Triple", 10.00),
            new ProductoDummy("Empanada de Carne", 4.50),
            new ProductoDummy("Empanada de Pollo", 4.50),
            new ProductoDummy("Croissant Simple", 5.00),
            new ProductoDummy("Croissant de Jamón y Queso", 7.50),
            new ProductoDummy("Muffin de Arándanos", 6.00),
            new ProductoDummy("Brownie", 5.50),
            new ProductoDummy("Cheesecake", 9.00),
            new ProductoDummy("Galleta de Chocolate", 3.00),
            new ProductoDummy("Jugo de Naranja", 6.00),
            new ProductoDummy("Limonada",  5.00),
            new ProductoDummy("Agua Mineral 500ml", 3.00),
            new ProductoDummy("Inka Kola 500ml", 4.50),
            new ProductoDummy("Coca Cola 500ml", 4.50),
            new ProductoDummy("Ensalada César", 12.00),
            new ProductoDummy("Wrap de Pollo", 11.00),
            new ProductoDummy("Papa Rellena", 6.00)  
        );
    }

    private void initUI() {
        setTitle("Nuevo Pedido - Cafetería UTP");
        initModality(Modality.APPLICATION_MODAL);
        setWidth(950);
        setHeight(680);

        VBox pnlIzquierda = new VBox(15);
        pnlIzquierda.setPadding(new Insets(15));
        pnlIzquierda.setStyle("-fx-background-color: #f4f4f4;");

        TabPane tabPaneCliente = new TabPane();
        tabPaneCliente.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");
        tabPaneCliente.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        VBox tabBuscar = new VBox(10);
        tabBuscar.setPadding(new Insets(15));
        TextField txtBuscarCodigo = new TextField();
        txtBuscarCodigo.setPromptText("Ingrese Código UTP del alumno");
        Button btnBuscar = new Button("🔍 Buscar");
        btnBuscar.setOnAction(e -> buscarClienteExistente(txtBuscarCodigo.getText()));
        
        lblClienteEstado = new Label("Ningún cliente seleccionado");
        lblClienteEstado.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        
        tabBuscar.getChildren().addAll(new Label("Buscar por Código:"), new HBox(10, txtBuscarCodigo, btnBuscar), lblClienteEstado);

        VBox tabNuevo = new VBox(10);
        tabNuevo.setPadding(new Insets(10));
        
        txtNewCodigo = new TextField(); txtNewCodigo.setPromptText("Código UTP (Usuario)");
        txtNewDni = new TextField(); txtNewDni.setPromptText("DNI");
        txtNewNombre = new TextField(); txtNewNombre.setPromptText("Nombres");
        txtNewApellido = new TextField(); txtNewApellido.setPromptText("Apellidos");
        txtNewCorreo = new TextField(); txtNewCorreo.setPromptText("Correo (@utp.edu.pe)");
        txtNewTel = new TextField(); txtNewTel.setPromptText("Teléfono");
        txtNewDir = new TextField(); txtNewDir.setPromptText("Dirección (Opcional)");
        
        GridPane gridNew = new GridGridPaneHelper().crearFormulario(
            txtNewCodigo, txtNewDni, txtNewNombre, txtNewApellido, txtNewCorreo, txtNewTel, txtNewDir
        );
        
        Button btnRegistrarCli = new Button("Guardar Cliente");
        btnRegistrarCli.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
        btnRegistrarCli.setOnAction(e -> registrarNuevoCliente());
        
        tabNuevo.getChildren().addAll(new Label("Datos del Estudiante:"), gridNew, btnRegistrarCli);

        tabPaneCliente.getTabs().add(new Tab("Buscar Existente", tabBuscar));
        tabPaneCliente.getTabs().add(new Tab("Nuevo Cliente", tabNuevo));

        txtBuscarProducto = new TextField();
        txtBuscarProducto.setPromptText("Filtrar producto...");
        txtBuscarProducto.textProperty().addListener((obs, oldVal, newVal) -> filtrarProductos(newVal));

        listaProductos = new ListView<>(catalogoProductos);
        VBox.setVgrow(listaProductos, Priority.ALWAYS);

        Button btnAdd = new Button("Agregar al Carrito →");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAdd.setOnAction(e -> agregarAlCarrito());

        pnlIzquierda.getChildren().addAll(tabPaneCliente, new Separator(), new Label("Catálogo:"), txtBuscarProducto, listaProductos, btnAdd);

        VBox pnlDerecha = new VBox(15);
        pnlDerecha.setPadding(new Insets(20));
        pnlDerecha.setStyle("-fx-background-color: white;");

        Label lblResumen = new Label("Resumen del Pedido");
        lblResumen.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<ItemCarrito> tablaCarrito = new TableView<>();
        tablaCarrito.setItems(itemsCarrito);
        tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<ItemCarrito, String> colProd = new TableColumn<>("Producto");
        colProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colProd.setReorderable(false); 

        TableColumn<ItemCarrito, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioStr"));
        colPrecio.setStyle("-fx-alignment: CENTER-RIGHT;");
        colPrecio.setReorderable(false);
        
        TableColumn<ItemCarrito, Void> colEliminar = new TableColumn<>("");
        colEliminar.setPrefWidth(50);
        colEliminar.setMaxWidth(50); 
        colEliminar.setReorderable(false);

        colEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btnQuitar = new Button("X");

            {
                btnQuitar.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #c62828; -fx-font-weight: bold; -fx-background-radius: 50; -fx-cursor: hand; -fx-font-size: 10px;");
                btnQuitar.setOnAction(event -> {
                    ItemCarrito item = getTableView().getItems().get(getIndex());
                    itemsCarrito.remove(item);
                    calcularTotal(); 
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnQuitar);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        
        tablaCarrito.getColumns().addAll(colProd, colPrecio, colEliminar);
        VBox.setVgrow(tablaCarrito, Priority.ALWAYS);

        HBox boxTotal = new HBox(10);
        boxTotal.setAlignment(Pos.CENTER_RIGHT);
        lblTotal = new Label("S/. 0.00");
        lblTotal.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        boxTotal.getChildren().addAll(new Label("TOTAL A PAGAR:"), lblTotal);

        Button btnConfirmar = new Button("CONFIRMAR PEDIDO");
        btnConfirmar.setPrefHeight(50);
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        btnConfirmar.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnConfirmar.setOnAction(e -> confirmarPedidoFinal());

        pnlDerecha.getChildren().addAll(lblResumen, tablaCarrito, new Separator(), boxTotal, btnConfirmar);

        SplitPane split = new SplitPane(pnlIzquierda, pnlDerecha);
        split.setDividerPositions(0.5);

        setScene(new Scene(split));
    }


    private void buscarClienteExistente(String codigo) {
        if (codigo.isEmpty()) return;
        
        
        Cliente c = null;
        try {
            List<Cliente> todos = clienteService.listarTodos(Cliente.class);
            c = todos.stream()
                     .filter(cli -> cli.getCodigoUTP().equalsIgnoreCase(codigo))
                     .findFirst().orElse(null);
        } catch (Exception ex) {
            System.err.println("Error buscando cliente: " + ex.getMessage());
        }

        if (c != null) {
            clienteSeleccionado = c;
            lblClienteEstado.setText("Encontrado: " + c.getNombre() + " " + c.getApellido());
            lblClienteEstado.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        } else {
            clienteSeleccionado = null;
            lblClienteEstado.setText("No encontrado");
            lblClienteEstado.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        }
    }

    private void registrarNuevoCliente() {
       
        if (txtNewCodigo.getText().isEmpty() || txtNewNombre.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "El código y el nombre son obligatorios.");
            return;
        }

        try {
            int tel = Integer.parseInt(txtNewTel.getText());
            
            Cliente nuevo = new Cliente(
                txtNewCodigo.getText(),
                "123456", 
                txtNewNombre.getText(),
                txtNewApellido.getText(),
                txtNewDni.getText(),
                txtNewCorreo.getText(),
                tel,
                txtNewDir.getText()
            );
            
            
            new com.mycompany.poo_proyecto.utils.GenericDAO<Cliente>().saveClass(nuevo);
            
            clienteSeleccionado = nuevo;
            lblClienteEstado.setText("NUEVO: " + nuevo.getNombre());
            mostrarAlerta("Éxito", "Cliente registrado correctamente.");
            
            txtNewCodigo.clear(); txtNewNombre.clear(); txtNewApellido.clear();
            
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El teléfono debe ser numérico.");
        } catch (Exception e) {
            mostrarAlerta("Error BD", "Error al guardar (¿Código duplicado?): " + e.getMessage());
        }
    }

    private void agregarAlCarrito() {
        ProductoDummy sel = listaProductos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            itemsCarrito.add(new ItemCarrito(sel.nombre, sel.precio));
            calcularTotal();
        }
    }

    private void calcularTotal() {
        double total = itemsCarrito.stream().mapToDouble(i -> i.precio).sum();
        lblTotal.setText(String.format("S/. %.2f", total));
    }

    private void confirmarPedidoFinal() {
        if (itemsCarrito.isEmpty()) {
            mostrarAlerta("Carrito vacío", "No puedes crear un pedido sin productos.");
            return;
        }
        
        int idCliente = (clienteSeleccionado != null) ? clienteSeleccionado.getIdCliente() : 0;
        if (idCliente == 0) {
        }

        StringBuilder resumen = new StringBuilder();
        for (ItemCarrito item : itemsCarrito) {
            resumen.append("1x ").append(item.nombre).append("\n");
        }
        
        double totalFinal = itemsCarrito.stream().mapToDouble(i -> i.precio).sum();

        pedidoService.registrarPedido(
            resumen.toString(), 
            idCliente,          
            1,                  
            totalFinal          
        );

        guardadoExitoso = true;
        close();
    }

    private void filtrarProductos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            listaProductos.setItems(catalogoProductos);
        } else {
            List<ProductoDummy> filtrados = catalogoProductos.stream()
                .filter(p -> p.toString().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
            listaProductos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    public boolean isGuardadoExitoso() { return guardadoExitoso; }

    private static class GridGridPaneHelper {
        public GridPane crearFormulario(Control... controles) {
            GridPane gp = new GridPane();
            gp.setHgap(10); gp.setVgap(10);
            int row = 0;
            gp.add(new Label("Código:"), 0, 0); gp.add(controles[0], 1, 0);
            gp.add(new Label("DNI:"), 0, 1);    gp.add(controles[1], 1, 1);
            gp.add(new Label("Nombre:"), 0, 2); gp.add(controles[2], 1, 2);
            gp.add(new Label("Apellido:"), 0, 3);gp.add(controles[3], 1, 3);
            gp.add(new Label("Correo:"), 0, 4); gp.add(controles[4], 1, 4);
            gp.add(new Label("Teléfono:"), 0, 5);gp.add(controles[5], 1, 5);
            gp.add(new Label("Dirección:"), 0, 6);gp.add(controles[6], 1, 6);
            return gp;
        }
    }

    public static class ProductoDummy {
        String nombre; double precio;
        public ProductoDummy(String n, double p) { this.nombre = n; this.precio = p; }
        @Override public String toString() { return nombre + " - S/. " + String.format("%.2f", precio); }
    }
    public static class ItemCarrito {
        String nombre; double precio;
        public ItemCarrito(String n, double p) { this.nombre = n; this.precio = p; }
        public String getNombre() { return nombre; }
        public String getPrecioStr() { return String.format("S/. %.2f", precio); }
    }
}
