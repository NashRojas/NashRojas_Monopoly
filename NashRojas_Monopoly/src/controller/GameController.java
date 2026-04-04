package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.*;
import javafx.scene.control.*;
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

        String nombreJugador = antes.getNombre();
        int posicionAntes = antes.getPosicion();
        int dineroAntes = antes.getDinero();
        boolean estabaEnCarcel = antes.isEnCarcel();

        int dado = juego.ejecutarTurno();

        Casilla casillaActual = juego.getCasilla(antes.getPosicion());

        if (dado > 0 && casillaActual instanceof Propiedad) {
            manejarPropiedad(antes, (Propiedad) casillaActual, dineroAntes);
        }

        actualizarVista();

        log("Turno de " + nombreJugador);

        if (dado > 0) {
            log("Dado: " + dado);
            log("Posicion: " + posicionAntes + " → " + antes.getPosicion());
            log("Dinero: $" + dineroAntes + " → $" + antes.getDinero());
        } else {
            log("No tiro dado.");
        }

        if (!estabaEnCarcel && antes.isEnCarcel()) {
            log(nombreJugador + " fue enviado a la carcel.");
            log("Turnos restantes en carcel: " + antes.getTurnosEnCarcel());
        }
        else if (estabaEnCarcel && antes.isEnCarcel()) {
            log(nombreJugador + " esta en carcel.");
            log("Turnos restantes: " + antes.getTurnosEnCarcel());
        }
        else if (estabaEnCarcel && !antes.isEnCarcel()) {
            log(nombreJugador + " sale de la carcel.");
        }

        if (casillaActual instanceof Propiedad) {
            Propiedad p = (Propiedad) casillaActual;

            if (p.getDueno() == antes) {
                log(nombreJugador + " es dueno de " + p.getNombre() + ".");
            } else if (p.getDueno() != null) {
                log(nombreJugador + "pago renta de $" + p.calcularRenta() + " a" + p.getDueno().getNombre());
            }
        }
        log("-----------------------------------");

        if (juego.hayGanador()) {
            log(" GANADOR: " + nombreJugador);
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

    private void manejarPropiedad(Jugador jugador, Propiedad propiedad, int dineroAntes) {

    if (propiedad.getDueno() == null) {

        if (jugador.isBot()) {
            if (jugador.getDinero() >= propiedad.getPrecio()) {
                propiedad.comprar(jugador);
                log(jugador.getNombre() + " compro " + propiedad.getNombre() + " por $" + propiedad.getPrecio());
            }
        } else {
            if (jugador.getDinero() >= propiedad.getPrecio()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Comprar propiedad");
                alert.setHeaderText(jugador.getNombre() + ", has caido en " + propiedad.getNombre());
                alert.setContentText(
                    "Precio: $" + propiedad.getPrecio() + "\n" +
                    "Renta actual: $" + propiedad.calcularRenta() + "\n" +
                    "Nivel: " + propiedad.getNivelMejora() + "\n\n" +
                    "Deseas comprar esta propiedad?"
                );

                ButtonType btnComprar = new ButtonType("Comprar");
                ButtonType btnNoComprar = new ButtonType("No comprar", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(btnComprar, btnNoComprar);

                Optional<ButtonType> resultado = alert.showAndWait();

                if (resultado.isPresent() && resultado.get() == btnComprar) {
                    propiedad.comprar(jugador);
                    log(jugador.getNombre() + " compro " + propiedad.getNombre() + " por $" + propiedad.getPrecio());
                } else {
                    log(jugador.getNombre() + " decidio no comprar " + propiedad.getNombre());
                }
            } else {
                log(jugador.getNombre() + " no tiene dinero suficiente para comprar " + propiedad.getNombre());
            }
        }
    }
}
}