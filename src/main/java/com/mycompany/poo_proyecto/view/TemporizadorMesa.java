package com.mycompany.poo_proyecto.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.function.Consumer;

public class TemporizadorMesa {
    private final int tiempoTotalMinutos;
    private int segundosRestantes;
    private Timeline timeline;
    private final Consumer<String> onActualizarTiempo; 
    private final Runnable onTiempoTerminado;          

    public TemporizadorMesa(int minutos, Consumer<String> onActualizarTiempo, Runnable onTiempoTerminado) {
        this.tiempoTotalMinutos = minutos;
        this.segundosRestantes = minutos * 30; //modificar a 60 cuanto termines de testear
        this.onActualizarTiempo = onActualizarTiempo;
        this.onTiempoTerminado = onTiempoTerminado;
    }
    public void iniciar() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundosRestantes--;
            if (onActualizarTiempo != null) {
                onActualizarTiempo.accept(formatearTiempo(segundosRestantes));
            }
            if (segundosRestantes <= 0) {
                detener();
                if (onTiempoTerminado != null) {
                    onTiempoTerminado.run();
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void detener() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    private String formatearTiempo(int totalSegundos) {
        int minutos = totalSegundos / 60;
        int segundos = totalSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
    
    public boolean estaCorriendo() {
        return timeline != null && timeline.getStatus() == javafx.animation.Animation.Status.RUNNING;
    }
}
