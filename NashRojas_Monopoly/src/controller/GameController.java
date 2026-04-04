package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.List;
import model.*;

public class GameController {
    
    @FXML 
    private TextArea txtLog;

    @FXML
    private TableroController tableroController;

    @FXML private VBox panelJugador1;
    @FXML private VBox panelJugador2;
    @FXML private VBox panelJugador3;
    @FXML private VBox panelJugador4;

    @FXML private Label lblNombreJ1;
    @FXML private Label lblDineroJ1;
    @FXML private Label lblCapitalJ1;
    @FXML private Label lblPosicionJ1;

    @FXML private Label lblNombreJ2;
    @FXML private Label lblDineroJ2;
    @FXML private Label lblCapitalJ2;
    @FXML private Label lblPosicionJ2;

    @FXML private Label lblNombreJ3;
    @FXML private Label lblDineroJ3;
    @FXML private Label lblCapitalJ3;
    @FXML private Label lblPosicionJ3;

    @FXML private Label lblNombreJ4;
    @FXML private Label lblDineroJ4;
    @FXML private Label lblCapitalJ4;
    @FXML private Label lblPosicionJ4;

    private Juego juego;

    public void setJuego(Juego juego) {
        this.juego = juego;
        tableroController.setJuego(juego);
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
        tableroController.actualizarJugadores(juego.getJugadores());
        actualizarPanelesJugadores();
    }

    private void actualizarPanelesJugadores() {
        List<Jugador> jugadores = juego.getJugadores();

        limpiarPanel(panelJugador1, lblNombreJ1, lblDineroJ1, lblCapitalJ1, lblPosicionJ1);
        limpiarPanel(panelJugador2, lblNombreJ2, lblDineroJ2, lblCapitalJ2, lblPosicionJ2);
        limpiarPanel(panelJugador3, lblNombreJ3, lblDineroJ3, lblCapitalJ3, lblPosicionJ3);
        limpiarPanel(panelJugador4, lblNombreJ4, lblDineroJ4, lblCapitalJ4, lblPosicionJ4);

        if (jugadores.size() > 0) {
            llenarPanel(jugadores.get(0), panelJugador1, lblNombreJ1, lblDineroJ1, lblCapitalJ1, lblPosicionJ1);
        }
        if (jugadores.size() > 1) {
            llenarPanel(jugadores.get(1), panelJugador2, lblNombreJ2, lblDineroJ2, lblCapitalJ2, lblPosicionJ2);
        }
        if (jugadores.size() > 2) {
            llenarPanel(jugadores.get(2), panelJugador3, lblNombreJ3, lblDineroJ3, lblCapitalJ3, lblPosicionJ3);
        }
        if (jugadores.size() > 3) {
            llenarPanel(jugadores.get(3), panelJugador4, lblNombreJ4, lblDineroJ4, lblCapitalJ4, lblPosicionJ4);
        }
    }


    private void llenarPanel(Jugador jugador, VBox panel, Label lblNombre, Label lblDinero,
        Label lblCapital, Label lblPosicion) {
        panel.setVisible(true);
        panel.setManaged(true);

        lblNombre.setText("Nombre: " + jugador.getNombre());
        lblDinero.setText("Dinero: $" + jugador.getDinero());
        lblCapital.setText("Capital: $" + juego.calcularCapital(jugador));
        lblPosicion.setText("Posicion: " + jugador.getPosicion());
    }

    private void limpiarPanel(VBox panel, Label lblNombre, Label lblDinero,
        Label lblCapital, Label lblPosicion) {
        panel.setVisible(false);
        panel.setManaged(false);

        lblNombre.setText("Nombre: -");
        lblDinero.setText("Dinero: -");
        lblCapital.setText("Capital: -");
        lblPosicion.setText("Posicion: -");
    }

    private void log(String mensaje) {
        txtLog.appendText(mensaje + "\n");
    }

}