package com.mycompany.poo_proyecto.model.notificacion;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private int idNotificacion;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "id_destinatario", nullable = false)
    private int idDestinatario;

    @Column(name = "id_remitente", nullable = false)
    private int idRemitente;

    public Notificacion() {
    }

    public Notificacion(String tipo, String mensaje, int idDestinatario, int idRemitente) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.idDestinatario = idDestinatario;
        this.idRemitente = idRemitente;
        this.fechaEnvio = LocalDateTime.now();
        this.estado = "No leída";
    }

    public void marcarLeida() {
        this.estado = "Leída";
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public int getIdRemitente() {
        return idRemitente;
    }
}
