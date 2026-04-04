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
        juego.setCasilla(1, new CasillaBasica(1, "Villa Duarte"));
        juego.setCasilla(2, new CasillaBasica(2, "Villa Agricola"));
        juego.setCasilla(3, new CasillaBasica(3, "Avenida Papolo"));
        juego.setCasilla(4, new Impuesto(4, "Impuesto", 200));
        juego.setCasilla(5, new CasillaBasica(5, "Estacion Norte"));
        juego.setCasilla(6, new CasillaBasica(6, "Gazcue"));
        juego.setCasilla(7, new CasillaBasica(7, "Suerte"));
        juego.setCasilla(8, new CasillaBasica(8, "Naco"));
        juego.setCasilla(9, new CasillaBasica(9, "Piantini"));
        juego.setCasilla(10, new Carcel(10, "Carcel"));

        juego.setCasilla(11, new CasillaBasica(11, "Los Mina"));
        juego.setCasilla(12, new CasillaBasica(12, "Edesur"));
        juego.setCasilla(13, new CasillaBasica(13, "Ensanche Ozama"));
        juego.setCasilla(14, new CasillaBasica(14, "Bella Vista"));
        juego.setCasilla(15, new CasillaBasica(15, "Estacion Sur"));
        juego.setCasilla(16, new CasillaBasica(16, "Santiago"));
        juego.setCasilla(17, new CasillaBasica(17, "Comunidad"));
        juego.setCasilla(18, new CasillaBasica(18, "La Vega"));
        juego.setCasilla(19, new CasillaBasica(19, "Puerto Plata"));
        juego.setCasilla(20, new CasillaBasica(20, "Parqueo Libre"));

        juego.setCasilla(21, new CasillaBasica(21, "San Francisco"));
        juego.setCasilla(22, new CasillaBasica(22, "Suerte"));
        juego.setCasilla(23, new CasillaBasica(23, "Moca"));
        juego.setCasilla(24, new CasillaBasica(24, "Salcedo"));
        juego.setCasilla(25, new CasillaBasica(25, "Estacion Este"));
        juego.setCasilla(26, new CasillaBasica(26, "Bavaro"));
        juego.setCasilla(27, new CasillaBasica(27, "Punta Cana"));
        juego.setCasilla(28, new CasillaBasica(28, "Coraa"));
        juego.setCasilla(29, new CasillaBasica(29, "Romana"));
        juego.setCasilla(30, new IrACarcel(30, "Ir a Carcel", 10));

        juego.setCasilla(31, new CasillaBasica(31, "Higuey"));
        juego.setCasilla(32, new CasillaBasica(32, "Bonao"));
        juego.setCasilla(33, new CasillaBasica(33, "Comunidad"));
        juego.setCasilla(34, new CasillaBasica(34, "Samana"));
        juego.setCasilla(35, new CasillaBasica(35, "Estacion Central"));
        juego.setCasilla(36, new CasillaBasica(36, "Suerte"));
        juego.setCasilla(37, new CasillaBasica(37, "Cap Cana"));
        juego.setCasilla(38, new CasillaBasica(38, "Impuesto Lujo"));
        juego.setCasilla(39, new CasillaBasica(39, "Zona Colonial"));
    }
}