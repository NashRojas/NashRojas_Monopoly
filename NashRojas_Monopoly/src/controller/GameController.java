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

    @FXML private Label lblDado;

    private Juego juego;

    public void setJuego(Juego juego) {
        this.juego = juego;
        tableroController.setJuego(juego);
        actualizarVista();
        log("Juego iniciado.");
        lblDado.setText("Dado: -");
    }

    @FXML
    private void lanzarTurno() {

        Jugador antes = juego.getJugadorActual();

        String nombreJugador = antes.getNombre();
        int posicionAntes = antes.getPosicion();
        int dineroAntes = antes.getDinero();
        boolean estabaEnCarcel = antes.isEnCarcel();

        int dado = juego.ejecutarTurno();
        if (dado > 0) {
            lblDado.setText("Dado: " + dado);
        } else {
            lblDado.setText("Dado: -");
        }

        Casilla casillaActual = juego.getCasilla(antes.getPosicion());

        if (casillaActual instanceof CasillaEvento) {
            CasillaEvento evento = (CasillaEvento) casillaActual;

            log(nombreJugador + "cayo en Evento");

            if (evento.getUltimoMensaje() != null) {
                log("Evento: " + evento.getUltimoMensaje());
            }

            if (!antes.isBot()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Evento");
                alert.setHeaderText(nombreJugador + ", has caido en una casilla de evento.");
                alert.setContentText(evento.getUltimoMensaje());
                alert.showAndWait();
            }
        }

        if (dado > 0 && casillaActual instanceof Propiedad) {
            manejarPropiedad(antes, (Propiedad) casillaActual, dineroAntes);
        }
        if (dado > 0 && casillaActual instanceof OportunidadNegocio) {
            manejarOportunidadNegocio(antes);
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

    @FXML
    private void mostrarPreciosRentas() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Precios y rentas");
        dialog.setHeaderText("Consulta por grupo de color");

        ComboBox<String> comboColores = new ComboBox<>();
        comboColores.getItems().addAll(
            "marron", "azul", "rojo", "verde", "amarillo", "naranja"
        );
        comboColores.setValue("marron");

        TextArea txtInfo = new TextArea();
        txtInfo.setEditable(false);
        txtInfo.setWrapText(true);
        txtInfo.setPrefWidth(420);
        txtInfo.setPrefHeight(260);

        actualizarTextoGrupo(comboColores.getValue(), txtInfo);

        comboColores.setOnAction(e -> {
            actualizarTextoGrupo(comboColores.getValue(), txtInfo);
        });

        VBox contenido = new VBox(10);
        contenido.getChildren().addAll(comboColores, txtInfo);

        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        dialog.showAndWait();
    }


    private void actualizarTextoGrupo(String colorSeleccionado, TextArea txtInfo) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 40; i++) {
            Casilla casilla = juego.getCasilla(i);

            if (casilla instanceof Propiedad) {
                Propiedad p = (Propiedad) casilla;

                if (p.getColor().equalsIgnoreCase(colorSeleccionado)) {
                    int renta0 = p.calcularRenta();
                    int renta1 = (int) (renta0 * 1.5);
                    int renta2 = (int) (renta0 * 2.0);
                    int renta3 = (int) (renta0 * 2.5);

                    sb.append(p.getNombre()).append("\n");
                    sb.append("Precio: $").append(p.getPrecio()).append("\n");
                    sb.append("Renta nivel 0: $").append(renta0).append("\n");
                    sb.append("Renta nivel 1: $").append(renta1).append("\n");
                    sb.append("Renta nivel 2: $").append(renta2).append("\n");
                    sb.append("Renta nivel 3: $").append(renta3).append("\n");
                    sb.append("-----------------------------------\n");
                }
            }
        }

        txtInfo.setText(sb.toString());
    }

    private void manejarOportunidadNegocio(Jugador jugador) {

        if (jugador.getPropiedades().isEmpty()) {
            log(jugador.getNombre() + " cayo en Inapa, pero no tiene propiedades para mejorar.");
            return;
        }

        if (jugador.isBot()) {
            for (Propiedad p : jugador.getPropiedades()) {
                if (p.getNivelMejora() < 3) {
                    int costoOriginal = obtenerCostoMejoraSegunNivel(p.getNivelMejora());
                    int costoConDescuento = costoOriginal / 2;

                    if (jugador.getDinero() >= costoConDescuento) {
                        jugador.pagar(costoConDescuento);
                        aumentarNivelDirecto(p);
                        log(jugador.getNombre() + " aprovecho Inapa y mejoro " + p.getNombre() +
                            " pagando $" + costoConDescuento);
                        return;
                    }
                }
            }

            log(jugador.getNombre() + " cayo en Inapa, pero no pudo mejorar ninguna propiedad.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Oportunidad de negocio");
        dialog.setHeaderText(jugador.getNombre() + " cayo en Inapa");
        dialog.setContentText("Elige una propiedad para mejorar con 50% de descuento:");

        for (Propiedad p : jugador.getPropiedades()) {
            if (p.getNivelMejora() < 3) {
                int costoOriginal = obtenerCostoMejoraSegunNivel(p.getNivelMejora());
                int costoConDescuento = costoOriginal / 2;
                dialog.getItems().add(p.getNombre() + " - Costo: $" + costoConDescuento);
            }
        }

        if (dialog.getItems().isEmpty()) {
            log(jugador.getNombre() + " cayo en Inapa, pero ya todas sus propiedades estan al maximo.");
            return;
        }

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent()) {
            String seleccion = resultado.get();

            for (Propiedad p : jugador.getPropiedades()) {
                if (seleccion.startsWith(p.getNombre())) {
                    int costoOriginal = obtenerCostoMejoraSegunNivel(p.getNivelMejora());
                    int costoConDescuento = costoOriginal / 2;

                    if (jugador.getDinero() >= costoConDescuento) {
                        jugador.pagar(costoConDescuento);
                        aumentarNivelDirecto(p);
                        log(jugador.getNombre() + " mejoro " + p.getNombre() +
                            " con descuento de Inapa por $" + costoConDescuento);
                    } else {
                        log(jugador.getNombre() + " no tiene dinero suficiente para mejorar " + p.getNombre());
                    }
                    break;
                }
            }
        } else {
            log(jugador.getNombre() + " decidio no usar la oportunidad de negocio.");
        }
    }

    private int obtenerCostoMejoraSegunNivel(int nivelActual) {
        switch (nivelActual) {
            case 0:
                return 100;
            case 1:
                return 150;
            case 2:
                return 200;
            default:
                return 0;
        }
    }

    private void aumentarNivelDirecto(Propiedad propiedad) {
        try {
            java.lang.reflect.Field field = Propiedad.class.getDeclaredField("nivelMejora");
            field.setAccessible(true);
            int nivelActual = (int) field.get(propiedad);

            if (nivelActual < 3) {
                field.set(propiedad, nivelActual + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}