package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;

public class MenuController {
    @FXML
    private Spinner<Integer> spnHumanos;
    @FXML
    private Spinner<Integer> spnBots;
    @FXML
    private Label lblError;

    @FXML
    private void initialize() {
        spnHumanos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 2));
        spnBots.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, 0));
    }

    @FXML
    private void iniciarJuego() {

        int humanos = spnHumanos.getValue();
        int bots = spnBots.getValue();

        int total = humanos + bots;

        if (total < 2) {
            lblError.setText("Debe haber al menos 2 jugadores.");
            return;
        }

        if (total > 4) {
            lblError.setText("Maximo 4 jugadores.");
            return;
        }

        try {
            
            Juego juego = new Juego();
            
            for (int i = 1; i <= humanos; i++) {
                juego.agregarJugador(new Jugador("Jugador " + i, false));
            }

            
            for (int i = 1; i <= bots; i++) {
                juego.agregarJugador(new Jugador("Bot " + i, true));
            }

            iniciarTablero(juego);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Scene scene = new Scene(loader.load());

            
            GameController controller = loader.getController();
            controller.setJuego(juego);

            
            Stage stage = (Stage) spnHumanos.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            lblError.setText("Error al iniciar el juego.");
        }
    }

    @FXML
    private void mostrarReglas() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reglas del Monopoly");
        alert.setHeaderText("Reglas básicas");

        alert.setContentText(
                "- Cada jugador lanza el dado en su turno\n" +
                "- Al caer en una propiedad puedes comprarla\n" +
                "- Si tiene dueño, debes pagar renta\n" +
                "- Pasar por salida da $200\n" +
                "- Gana el ultimo jugador en pie o quien tenga mayor capital"
        );

        alert.showAndWait();
    }

    private void iniciarTablero(Juego juego) {
        juego.setCasilla(0, new Salida(0, "Salida"));

        juego.setCasilla(1, new Propiedad(1, "Villa Duarte", 200, 50, "marron"));
        juego.setCasilla(2, new CasillaEvento(2, "Evento"));
        juego.setCasilla(3, new Propiedad(3, "Avenida Papolo", 100, 20, "marron"));

        juego.setCasilla(4, new Impuesto(4, "DGII", 200));
        juego.setCasilla(5, new Servicio(5, "Estacion Edenorte", 150));

        juego.setCasilla(6, new Propiedad(6, "Gazcue", 200, 40, "azul"));
        juego.setCasilla(7, new CasillaEvento(7, "Evento"));

        juego.setCasilla(8, new Propiedad(8, "Naco", 200, 40, "azul"));
        juego.setCasilla(9, new Propiedad(9, "Piantini", 200, 40, "azul"));

        juego.setCasilla(10, new Carcel(10, "Carcel"));

        juego.setCasilla(11, new Propiedad(11, "Bella Vista", 250, 60, "rojo"));

        juego.setCasilla(12, new Servicio(12, "EdeSur", 150));
        juego.setCasilla(13, new Propiedad(13, "Ensanche Ozama", 250, 60, "rojo"));

        juego.setCasilla(14, new Propiedad(14, "Los Mina", 300, 70, "rojo"));
        juego.setCasilla(15, new Propiedad(15, "Villa Mella", 300, 70, "rojo"));

        juego.setCasilla(16, new Propiedad(16, "Santiago", 350, 85, "verde"));

        juego.setCasilla(17, new Propiedad(17, "Puerto Plata", 350, 85, "verde"));
        juego.setCasilla(18, new Propiedad(18, "La Vega", 360, 90, "verde"));

        juego.setCasilla(19, new Impuesto(19, "DGII", 80));

        juego.setCasilla(20, new Servicio(20, "Inapa", 150));

        juego.setCasilla(21, new CasillaEvento(21, "Evento"));
        
        juego.setCasilla(22, new Propiedad(22, "San Francisco", 380, 95, "verde"));
        juego.setCasilla(23, new Propiedad(23, "Moca", 420, 110, "amarillo"));

        juego.setCasilla(24, new Propiedad(24, "salcedo", 430, 115, "amarillo"));

        juego.setCasilla(25, new Propiedad(25, "Tres Fuego", 450, 125, "amarillo"));


        juego.setCasilla(26, new Propiedad(26, "Punta Cana", 450, 125, "amarillo"));

        juego.setCasilla(27, new Propiedad(27, "Evento", 460, 130, "amarillo"));

        juego.setCasilla(28, new CasillaEvento(28, "Evento"));

        juego.setCasilla(29, new Propiedad(29, "La Romana", 470, 130, "amarillo"));

        juego.setCasilla(30, new IrACarcel(30, "Ir a Carcel", 10));

        juego.setCasilla(31, new CasillaEvento(31, "evento"));

        juego.setCasilla(32, new Propiedad(32, "Bonao", 480, 140, "naranja"));
        juego.setCasilla(33, new Propiedad(33, "Comunidad", 500, 160, "naranja"));

        juego.setCasilla(34, new Impuesto(34, "DGII", 100));

        juego.setCasilla(35, new Servicio(35, "Goldo ORO", 150));

        juego.setCasilla(36, new CasillaEvento(36, "Evento"));
        
        juego.setCasilla(37, new Propiedad(37, "Cap Cana", 500, 160, "naranja"));

        juego.setCasilla(38, new Impuesto(38, "DGII", 120));

        juego.setCasilla(39, new Propiedad(39, "Zona Colonial", 650, 220, "azul"));
    }
}