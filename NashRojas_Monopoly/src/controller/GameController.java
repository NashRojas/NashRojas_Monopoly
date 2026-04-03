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
    private TableroController tableroController;

    private Juego juego;

    public void setJuego(Juego juego) {
        this.juego = juego;
        actualizarVista();
        log("Juego iniciado.");
        tableroController.setJuego(juego);
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

    private int[][] obtenerPosiciones(){
        return new int[][] {
            {10,0}, {10,1}, {10,2}, {10,3}, {10,4}, {10,5}, {10,6}, {10,7}, {10,8}, {10,9}, {10,10},

            {9,10}, {8,10}, {7,10}, {6,10}, {5,10}, {4,10}, {3,10}, {2,10}, {1,10},{0,10},

            {0,9}, {0,8}, {0,7}, {0,6}, {0,5}, {0,4}, {0,3}, {0,2}, {0,1},{0,0},

            {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}, {8,0}, {9,0}
        };
    }

    private String[] nombresCasillas = {
    "Salida","Mediterráneo","Comunidad","Báltica","Impuesto","Reading RR",
    "Oriental","Suerte","Vermont","Connecticut","Cárcel",
    "St. Charles","Electricidad","States","Virginia","Penn RR",
    "St. James","Comunidad","Tennessee","New York","Libre",
    "Kentucky","Suerte","Indiana","Illinois","B&O RR",
    "Atlantic","Ventnor","Agua","Marvin","Ir a Cárcel",
    "Pacific","Carolina Norte","Comunidad","Pennsylvania",
    "Short Line","Suerte","Park Place","Impuesto Lujo","Boardwalk"
    };


}