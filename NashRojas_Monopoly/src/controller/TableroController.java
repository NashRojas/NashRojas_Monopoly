package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.*;


public class TableroController {
    
    //  controlador para dibujar el tablero de juego, mostrando las casillas con su nombre, precio y renta segun el tipo de casilla, 
    // y actualizando la posicion de los jugadores en el tablero cada vez que se mueven, 
    // utilizando un GridPane para organizar las casillas y un StackPane para mostrar las fichas de los jugadores en cada casilla, 
    // con estilos personalizados para cada tipo de casilla y para los jugadores
    @FXML
    private GridPane gridTablero;

    private Juego juego;

    // metodo para establecer el juego en el controlador, asignando el juego recibido al campo privado y llamando al metodo para dibujar el tablero 
    // con la informacion del juego
    public void setJuego(Juego juego) {
        this.juego = juego;
        dibujarTablero();
    }

    // metodo para dibujar el tablero de juego, recorriendo las casillas del juego en el orden correcto para colocarlas en el GridPane, 
    // y llamando a un metodo auxiliar para agregar cada casilla al GridPane con su informacion y estilo correspondiente, 
    // y luego llamando a otro metodo para dibujar el centro del tablero con el titulo del juego
    private void dibujarTablero() {

        gridTablero.getChildren().clear();

        int index = 0;

        // se recorre el tablero en sentido antihorario, empezando por la casilla 0 en la esquina inferior derecha, 
        // y se llama al metodo agregarCasilla para cada casilla del juego,
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

        dibujarCentroTablero();
    }

    // metodo auxiliar para dibujar el centro del tablero con un StackPane que contiene un VBox con el titulo y subtitulo del juego, 
    // aplicando estilos personalizados para el fondo, borde y texto del centro del tablero, y agregando el StackPane al GridPane en la posicion central del tablero
    private void dibujarCentroTablero() {
        StackPane centro = new StackPane();

        centro.setStyle("-fx-backgroud-color:linear-gradient(to bottom, #fafafa, #ececec); " + "-fx-border-color: #555555; " + "-fx-border-width: 2;" + "-fx-border-radius: 14; " + "-fx-background-radius: 14;");

        VBox cajaTitulo = new VBox(6);
        cajaTitulo.setAlignment(javafx.geometry.Pos.CENTER);

        Label titulo = new Label("Thu Real Monopoly");
        titulo.setStyle("-fx-font-size: 25x; -fx-font-weight: bold; -fx-text-fill: #222222; -fx-text-alignment: center;");

        Label subtitulo = new Label("Edicion Vacana");
        subtitulo.setStyle("-fx-font-size: 12px; " + "-fx-text-fill: #666666; " + "-fx-font-style: italic;");

        cajaTitulo.getChildren().addAll(titulo, subtitulo);
        centro.getChildren().add(cajaTitulo);

        gridTablero.add(centro, 1, 1, 9, 9);
    }

    // metodo auxiliar para agregar una casilla al GridPane en la posicion especificada, obteniendo la informacion de la casilla del juego segun su posicion, 
    // creando un VBox con el nombre, precio y renta de la casilla segun su tipo, aplicando estilos personalizados para cada tipo de casilla 
    // y para el propietario de la casilla si es una propiedad o servicio, y luego agregando el VBox a un StackPane con un borde y fondo personalizado,
    //  y finalmente agregando el StackPane al GridPane en la fila y columna especificadas
    private void agregarCasilla(int posicion, int fila, int columna) {

        Casilla casilla = juego.getCasilla(posicion);

        String nombre = (casilla != null) ? casilla.getNombre() : "Casilla " + posicion;

        Label lblNombre = new Label(nombre);
        lblNombre.setWrapText(true);
        lblNombre.setMaxWidth(60);
        lblNombre.setStyle("-fx-font-size: 8.5px; -fx-font-weight: bold; -fx-text-fill: #222222;");

        VBox contenido = new VBox(2);
        contenido.setAlignment(javafx.geometry.Pos.CENTER);

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
            lblPrecio.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #111111;");

            HBox nivelBox = new HBox(2);
            nivelBox.setAlignment(Pos.CENTER);

            for (int i = 0; i < p.getNivelMejora(); i++) {
                Label casita = new Label("🏠");
                casita.setStyle("-fx-text-fill:" + p.getDueno().getColor() + "; -fx-font-size: 8px;");
                nivelBox.getChildren().add(casita);
            }
            contenido.getChildren().add(nivelBox);

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
            contenido.getChildren().addAll(barraDueno, lblNombre, lblPrecio);
            contenido.setStyle("-fx-background-color: " + colorFX + "; -fx-padding: 3;");
            contenido.setAlignment(Pos.CENTER);
        } 

        // para las casillas de servicio, se muestra el precio de compra si no tiene dueño, o la renta calculada segun el numero de servicios que tenga 
        // el dueño si ya tiene dueño, y se aplica el color del dueño a la barra superior del panel, o se deja transparente si no tiene dueño
        else if (casilla instanceof Servicio) {
            Servicio s = (Servicio) casilla;

            Label barraDueno = new Label();
            barraDueno.setMinHeight(6);
            barraDueno.setMaxWidth(Double.MAX_VALUE);

            if (s.getDueno() != null) {
                String colorDueno = s.getDueno().getColor();
                barraDueno.setStyle("-fx-background-color: " + colorDueno + ";");
            } else {
                barraDueno.setStyle("-fx-background-color: transparent;");
            }

            Label lblValor;
            if (s.getDueno() == null) {
                lblValor = new Label("Compra: $" + s.getPrecio());
            } else {
                int rentaServicio = s.calcularRenta(juego.getServicios(), s.getDueno());
                lblValor = new Label("Renta: $" + rentaServicio);
            }
            lblValor.setStyle("-fx-font-size: 9px; -fx-font-weight: bold;");

            contenido.getChildren().addAll(barraDueno, lblNombre, lblValor);
            contenido.setStyle("-fx-background-color: #b0bec5; -fx-padding: 3;");
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
        celda.setStyle("-fx-border-color: #444444; -fx-border-width: 1; -fx-alignment: center; -fx-background-radius: 4; -fx-border-radius: 4;");
    
        gridTablero.add(celda, columna, fila);
    }

    // metodo para actualizar la posicion de los jugadores en el tablero, recibiendo la lista de jugadores actualizada, limpiando las fichas de los jugadores del GridPane, 
    // volviendo a dibujar el tablero para refrescar la informacion de las casillas, obteniendo las posiciones de cada casilla en el GridPane segun el orden del tablero,
    //  y luego recorriendo la lista de jugadores para crear una ficha para cada jugador y agregarla al GridPane en la posicion correspondiente segun su posicion en el juego,
    //  utilizando un StackPane para mostrar la ficha del jugador con su color y una etiqueta con su inicial, 
    // y agrupando las fichas de los jugadores que estan en la misma casilla en un HBox para evitar que se superpongan
    public void actualizarJugadores(java.util.List<Jugador> jugadores){

        gridTablero.getChildren().clear();

        dibujarTablero();

        int[][] posiciones = obtenerPosiciones();

        java.util.Map<String, HBox> fichasPorCasilla = new java.util.HashMap<>();

        for (Jugador j : jugadores) {

            int pos = j.getPosicion();

            int fila = posiciones[pos][0];
            int col = posiciones[pos][1];

            String clave = fila + "-" + col;

            HBox contenedorFichas;

            if (fichasPorCasilla.containsKey(clave)) {
                contenedorFichas = fichasPorCasilla.get(clave);
            } else {
                contenedorFichas = new HBox(2);
                contenedorFichas.setAlignment(Pos.CENTER);
                fichasPorCasilla.put(clave, contenedorFichas);
                gridTablero.add(contenedorFichas, col, fila);
            }

            StackPane ficha = crearFichaJugador(j);
            contenedorFichas.getChildren().add(ficha);
            Label jugadorLabel = new Label(j.getNombre().substring(0,1));

            gridTablero.add(jugadorLabel, col , fila);
        }
    }

    // metodo auxiliar para obtener las posiciones de cada casilla en el GridPane segun el orden del tablero, devolviendo un arreglo de enteros con las filas y 
    // columnas correspondientes a cada posicion del juego, empezando por la casilla 0 en la esquina inferior derecha y recorriendo el tablero en sentido antihorario
    private int[][] obtenerPosiciones(){
        return new int[][] {
            {10,0}, {10,1}, {10,2}, {10,3}, {10,4}, {10,5}, {10,6}, {10,7}, {10,8}, {10,9}, {10,10},

            {9,10}, {8,10}, {7,10}, {6,10}, {5,10}, {4,10}, {3,10}, {2,10}, {1,10},{0,10},

            {0,9}, {0,8}, {0,7}, {0,6}, {0,5}, {0,4}, {0,3}, {0,2}, {0,1},{0,0},

            {1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0}, {8,0}, {9,0}
        };
    }

    // metodo auxiliar para crear una ficha para un jugador, recibiendo el jugador como parametro, creando un circulo con el color del jugador y una etiqueta con 
    // la inicial de su nombre, aplicando estilos personalizados para la ficha del jugador, y devolviendo un StackPane que contiene el circulo y la etiqueta superpuestos
    private StackPane crearFichaJugador(Jugador jugador) {
        Circle circulo = new Circle(10);

        try {
            circulo.setFill(Color.web(jugador.getColor()));
        } catch (Exception e) {
            circulo.setFill(Color.GRAY);
        }

        circulo.setStroke(Color.BLACK);
        circulo.setStrokeWidth(1.2);

        Label inicial = new Label(jugador.getNombre().substring(0,1).toUpperCase());
        inicial.setStyle("-fx-font-size: 9px; -fx-text-fill: white; -fx-font-weight: bold;");

        StackPane ficha = new StackPane();
        ficha.getChildren().addAll(circulo, inicial);
        ficha.setMinSize(22, 22);
        ficha.setPrefSize(22, 22);
        ficha.setMaxSize(22, 22);
        return ficha;
    }
}
