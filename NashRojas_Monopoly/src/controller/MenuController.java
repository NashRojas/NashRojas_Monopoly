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
}