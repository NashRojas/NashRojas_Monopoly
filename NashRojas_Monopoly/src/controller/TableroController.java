package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        String nombre = (casilla != null) ? casilla.getNombre() : "Casilla " + posicion;

        Label lblNombre = new Label(nombre);
        lblNombre.setWrapText(true);
        lblNombre.setMaxWidth(60);
        lblNombre.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");

        VBox contenido = new VBox(2);

        if (casilla instanceof Propiedad) {
            Propiedad p = (Propiedad) casilla;

            Label barraDueno = new Label();
            barraDueno.setMinHeight(6);
            barraDueno.setMaxWidth(Double.MAX_VALUE);

            if (p.getDueno() != null) {
                String colorDueno = p.getDueno().getColor();
                barraDueno.setStyle("-fx-background-color: " + colorDueno + ";");
            } else {
                barraDueno.setStyle("-fx-background-color: transparent;");
            }

            Label lblPrecio;
            if (p.getDueno() == null) {
                lblPrecio = new Label("Compra: $" + p.getPrecio());
            } else {
                lblPrecio = new Label("Renta: $" + p.calcularRenta());
            }
            lblPrecio.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");

            Label lblNivel = new Label("Nivel: " + p.getNivelMejora());
            lblNivel.setStyle("-fx-font-size: 8px;");

            String color = p.getColor();
            String colorFX;

            switch (color) {
                case "marron":
                    colorFX = "#a1887f";
                    break;
                case "celeste":
                    colorFX = "#81d4fa";
                    break;
                case "rosado":
                    colorFX = "#f48fb1";
                    break;
                case "naranja":
                    colorFX = "#ffb74d";
                    break;
                case "rojo":
                    colorFX = "#da4f4f";
                    break;
                case "amarillo":
                    colorFX = "#fff176";
                    break;
                case "verde":
                    colorFX = "#81c784";
                    break;
                case "azul":
                    colorFX = "#64b5f6";
                    break;
                default:
                    colorFX = "#e0e0e0";
            }
            contenido.getChildren().addAll(barraDueno, lblNombre, lblPrecio, lblNivel);
            contenido.setStyle("-fx-background-color: " + colorFX + "; -fx-padding: 3;");
            contenido.setAlignment(Pos.CENTER);
        } 

        else if (casilla instanceof Impuesto) {
            Label lblTipo = new Label("Impuesto");
            lblTipo.setStyle("-fx-font-size: 9px;");
            contenido.getChildren().add(lblTipo);
            contenido.setStyle("-fx-background-color: #ffebee;");
        } 
        else if (casilla instanceof Salida) {
            Label lblTipo = new Label("Salida");
            lblTipo.setStyle("-fx-font-size: 9px;");
            contenido.getChildren().add(lblTipo);
            contenido.setStyle("-fx-background-color: #e3f2fd;");
        } 
        else if (casilla instanceof Carcel) {
            Label lblTipo = new Label("Carcel");
            lblTipo.setStyle("-fx-font-size: 9px;");
            contenido.getChildren().add(lblTipo);
            contenido.setStyle("-fx-background-color: #fff8e1;");
        } 
        else if (casilla instanceof IrACarcel) {
            Label lblTipo = new Label("Ir a carcel");
            lblTipo.setStyle("-fx-font-size: 9px;");
            contenido.getChildren().add(lblTipo);
            contenido.setStyle("-fx-background-color: #fbe9e7;");
        }
        else if (casilla instanceof OportunidadNegocio) {
            Label lblTipo = new Label("📈Negocio");
            lblTipo.setStyle("-fx-font-size: 9px;");
            contenido.getChildren().add(lblTipo);
            contenido.setStyle("-fx-background-color: #d1c3e9; -fx-padding: 3;");
        }
        else {
            contenido.getChildren().add(lblNombre);
            contenido.setStyle("-fx-padding: 3;");
        }

        StackPane celda = new StackPane(contenido);
        celda.setPrefSize(70, 70);
        celda.setStyle("-fx-border-color: black; -fx-aligment: center;");
    
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
