package com.mycompany.poo_proyecto.model.inventario;

import jakarta.persistence.*;
import java.util.*;

public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta", nullable = false)
    private int idReceta;
    private String nombre;
    private List<Producto> productos;

}
