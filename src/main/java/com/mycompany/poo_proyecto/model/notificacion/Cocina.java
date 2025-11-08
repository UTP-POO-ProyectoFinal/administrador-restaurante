package com.mycompany.poo_proyecto.model.notificacion;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Cocina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cocina")
    private int idCocina;
    private int pedidosEnProceso;

    public Cocina() {
    }
}
