package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.Juego;
import model.Casilla;

public class TableroController {
    
    @FXML
    private GridPane gridTablero;

    private Juego juego;

    public void setJuego(Juego juego) {
        this.juego = juego;
        dibujarTablero();
    }

    private void dibujarTablero() {

        gridTablero.getChildren().clear();

        int index = 0;


        for (int col = 0; col < 11; col++) {
            agregarCasilla(index++, 10, col);
        }

        
        for (int row = 9; row >= 1; row--) {
            agregarCasilla(index++, row, 10);
        }

        
        for (int col = 10; col >= 0; col--) {
            agregarCasilla(index++, 0, col);
        }

        
        for (int row = 1; row <= 9; row++) {
            agregarCasilla(index++, row, 0);
        }
    }

    private void agregarCasilla(int posicion, int fila, int columna) {

        Casilla casilla = juego.getCasilla(posicion);

        String texto = (casilla != null) ? casilla.getNombre() : "Casilla " + posicion;

        Label label = new Label(texto);
        label.setWrapText(true);

        StackPane celda = new StackPane(label);
        celda.setPrefSize(70, 70);
        celda.setStyle("-fx-border-color: black; -fx-alignment: center;");

        gridTablero.add(celda, columna, fila);
    }
}
