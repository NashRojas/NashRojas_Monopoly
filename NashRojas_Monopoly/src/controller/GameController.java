package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import model.*;

public class GameController {
    
@FXML private Label lblJugador;
    @FXML 
    private Label lblDinero;
    @FXML 
    private Label lblPosicion;
    @FXML 
    private TextArea txtLog;
    @FXML
    private GridPane tableroController; 

    private Juego juego;

    public void setJuego(Juego juego) {
        System.out.println(tableroController);
        this.juego = juego;
        actualizarVista();
        log("Juego iniciado.");
    }

    @FXML
    private void lanzarTurno() {

        Jugador antes = juego.getJugadorActual();

        int posicionAntes = antes.getPosicion();
        int dineroAntes = antes.getDinero();

        juego.ejecutarTurno();

        Jugador despues = juego.getJugadorActual(); // ya cambió turno

        actualizarVista();

        log("Turno de " + antes.getNombre());
        log("Posicion: " + posicionAntes + " → " + antes.getPosicion());
        log("Dinero: $" + dineroAntes + " → $" + antes.getDinero());
        log("-----------------------------------");

        if (juego.hayGanador()) {
            log(" GANADOR: " + despues.getNombre());
        }
    }

    private void actualizarVista() {
        Jugador actual = juego.getJugadorActual();

        lblJugador.setText("Jugador: " + actual.getNombre());
        lblDinero.setText("Dinero: $" + actual.getDinero());
        lblPosicion.setText("Posicion: " + actual.getPosicion());
    }

    private void log(String mensaje) {
        txtLog.appendText(mensaje + "\n");
    }
}