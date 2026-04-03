package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.*;


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

    public void actualizarJugadores(java.util.List<Jugador> jugadores){

        gridTablero.getChildren().clear();

        dibujarTablero();

        int[][] posiciones = obtenerPosiciones();

        for (Jugador j : jugadores) {

            int pos = j.getPosicion();

            int fila = posiciones[pos][0];
            int col = posiciones[pos][1];

            Label jugadorLabel = new Label(j.getNombre().substring(0,1));

            gridTablero.add(jugadorLabel, col , fila);
        }
    }

    private int[][] obtenerPosiciones(){
        return new int[][] {
            {10,0}, {10,1}, {10,2}, {10,3}, {10,4}, {10,5}, {10,6}, {10,7}, {10,8}, {10,9}, {10,10},

            {9,10}, {8,10}, {7,10}, {6,10}, {5,10}, {4,10}, {3,10}, {2,10}, {1,10},{0,10},

            {0,9}, {0,8}, {0,7}, {0,6}, {0,5}, {0,4}, {0,3}, {0,2}, {0,1},{0,0},

            {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}, {8,0}, {9,0}
        };
    }
}
