package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import java.util.*;
import javafx.scene.control.*;
import model.*;


public class GameController {
    // area del texto donde se muestra el historial
    @FXML 
    private TextArea txtLog;

    // referencia al controlador del tablero para actualizarlo cada turno
    @FXML
    private TableroController tableroController;


    // paneles y labels para mostrar la informacion de cada jugador
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

    @FXML private Label barraJ1;
    @FXML private Label barraJ2;
    @FXML private Label barraJ3;
    @FXML private Label barraJ4;

    @FXML private Label lblEstadoJ1;
    @FXML private Label lblEstadoJ2;
    @FXML private Label lblEstadoJ3;
    @FXML private Label lblEstadoJ4;

    // label para mostrar el resultado del dado
    @FXML private Label lblDado;

    // botones para lanzar el turno y mostrar precios/rentas
    @FXML private Button btnLanzarTurno;
    @FXML private Button btnPrecios;


    // referencia al juego para ejecutar los turnos y obtener la informacion actualizada
    private Juego juego;
    private boolean juegoFinalizado = false;


    // metodo para recibir la referencia al juego desde el main y actualizar la vista inicial
    public void setJuego(Juego juego) {
        this.juego = juego;
        tableroController.setJuego(juego);
        actualizarVista();
        log("Juego iniciado.");
        lblDado.setText("Dado: -");
    }

    // metodo para inicializar estilos y eventos de los botones
    @FXML
    public void initialize() {

        btnLanzarTurno.setStyle("-fx-background-color: #588157; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;");

        btnLanzarTurno.setOnMouseEntered(e ->
            btnLanzarTurno.setStyle("-fx-background-color: #3A5A40; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;")
        );

        btnLanzarTurno.setOnMouseExited(e ->
            btnLanzarTurno.setStyle("-fx-background-color: #588157; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;")
        );

        btnPrecios.setStyle("-fx-background-color: #588157; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;");

        btnPrecios.setOnMouseEntered(e ->
            btnPrecios.setStyle("-fx-background-color: #3A5A40; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;")
        );

        btnPrecios.setOnMouseExited(e ->
            btnPrecios.setStyle("-fx-background-color: #588157; -fx-text-fill: white; -fx-border-color: #344E41; -fx-border-radius: 8; -fx-background-radius: 8;")
        );
    }


    // metodo para manejar el evento de lanzar el turno, ejecutar la logica del juego y actualizar la vista
    @FXML
    private void lanzarTurno() {

        if (juegoFinalizado) {
            log("El juego ha finalizado. Reinicia para jugar de nuevo.");
            return;
        }


        // obtener informacion del jugador antes de ejecutar el turno para mostrar cambios en el log
        Jugador antes = juego.getJugadorActual();
        String nombreJugador = antes.getNombre();
        int posicionAntes = antes.getPosicion();
        int dineroAntes = antes.getDinero();
        boolean estabaEnCarcel = antes.isEnCarcel();

        // ejecutar el turno y obtener el resultado del dado
        int dado = juego.ejecutarTurno();
        if (dado > 0) {
            lblDado.setText("Dado: " + dado);
        } else {
            lblDado.setText("Dado: -");
        }

        // obtener la casilla actual despues de mover al jugador para determinar que acciones tomar segun el tipo de casilla
        Casilla casillaActual = juego.getCasilla(antes.getPosicion());

        if (casillaActual instanceof CasillaEvento) {
            CasillaEvento evento = (CasillaEvento) casillaActual;

            log(nombreJugador + "cayo en una casilla de Evento");

            if (evento.getUltimoMensaje() != null) {
                log("El evento determino: " + evento.getUltimoMensaje());
            }

            if (!antes.isBot()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Evento");
                alert.setHeaderText(nombreJugador + ", has caido en una casilla de evento.");
                alert.setContentText(evento.getUltimoMensaje());
                alert.showAndWait();
            }
        }

        // dependiendo del tipo de casilla, manejar la logica de compra, pago de renta, mejoras, etc
        if (dado > 0 && casillaActual instanceof Propiedad) {
            Propiedad propiedadActual = (Propiedad) casillaActual;

            if (propiedadActual.getDueno() == null) {
                manejarPropiedad(antes, propiedadActual, dineroAntes);
            } else if (propiedadActual.getDueno() == antes) {
                manejarMejoraPropiedad(antes, propiedadActual);
            }
        }
        if (dado > 0 && casillaActual instanceof Servicio) {
            Servicio servicioActual = (Servicio) casillaActual;

            if (servicioActual.getDueno() == null) {
                manejarServicio(antes, servicioActual);
            }
        }
        if (dado > 0 && casillaActual instanceof OportunidadNegocio) {
            manejarOportunidadNegocio(antes);
        }

        actualizarVista();

        log("Turno de " + nombreJugador.toUpperCase() + "===");

        // mostrar en el log los cambios ocurridos durante el turno comparando la informacion del jugador antes y despues de ejecutar el turno
        if (dado > 0) {
            log("Dado: " + dado);
            log("Posicion: " + posicionAntes + " → " + antes.getPosicion());
            log("Dinero: $" + dineroAntes + " → $" + antes.getDinero());
        } else {
            log("No tiro dado.");
        }

        // mostrar mensajes relacionados con el estado de carcel y pagos de renta segun el tipo de casilla donde cayo el jugador
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

        // mostrar mensajes relacionados con el pago de renta si el jugador cayo en una propiedad o servicio de otro jugador
        if (casillaActual instanceof Propiedad) {
            Propiedad p = (Propiedad) casillaActual;

            if (p.getDueno() == antes) {
                log(nombreJugador + " es dueño de " + p.getNombre() + ".");
            } else if (p.getDueno() != null) {
                log(nombreJugador + "pago renta de $" + p.calcularRenta() + " a" + p.getDueno().getNombre());
            }
        }

        // mostrar mensajes relacionados con el pago de renta si el jugador cayo en un servicio de otro jugador, 
        // calculando la renta segun la cantidad de servicios que tenga el dueño
        if (casillaActual instanceof Servicio) {
            Servicio s = (Servicio) casillaActual;

            if (s.getDueno() == antes) {
                log(nombreJugador + " es dueño de " + s.getNombre() + ".");
            } else if (s.getDueno() != null) {
                log(nombreJugador + " pago renta de $" + s.calcularRenta(juego.getServicios(), antes) + " a " + s.getDueno().getNombre() + "por el servicio " + s.getNombre());
            }
        }
        log("-----------------------------------");

        // verificar si el juego ha terminado despues de ejecutar el turno y mostrar un mensaje con el ganador
        if (juego.hayGanador()) {
            juegoFinalizado = true;

            Jugador ganador = juego.getJugadores().get(0);
            log(" GANADOR: " + ganador.getNombre());

            btnLanzarTurno.setDisable(true);
        
        // mostrar alerta con el ganador
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Juego finalizado");
            alert.setHeaderText("¡Tenemos un ganador!");
            alert.setContentText("El ganador es: " + ganador.getNombre());
            alert.showAndWait();
        }
    }

    // metodo para actualizar toda la vista del juego, incluyendo el tablero y los paneles de informacion de los jugadores
    private void actualizarVista() {
        tableroController.actualizarJugadores(juego.getJugadores());
        actualizarPanelesJugadores();
        actualizarEstiloPaneles();
    }

    // metodos auxiliares para actualizar los paneles de informacion de los jugadores, mostrar mensajes en el log y manejar las acciones de compra, 
    // pago de renta y mejoras segun el tipo de casilla donde cayo el jugador
    private void actualizarPanelesJugadores() {
        List<Jugador> jugadores = juego.getJugadores();

        limpiarPanel(panelJugador1, lblNombreJ1, lblDineroJ1, lblCapitalJ1, lblPosicionJ1, lblEstadoJ1);
        limpiarPanel(panelJugador2, lblNombreJ2, lblDineroJ2, lblCapitalJ2, lblPosicionJ2, lblEstadoJ2);
        limpiarPanel(panelJugador3, lblNombreJ3, lblDineroJ3, lblCapitalJ3, lblPosicionJ3, lblEstadoJ3);
        limpiarPanel(panelJugador4, lblNombreJ4, lblDineroJ4, lblCapitalJ4, lblPosicionJ4, lblEstadoJ4);

        if (jugadores.size() > 0) {
            llenarPanel(jugadores.get(0), panelJugador1, lblNombreJ1, lblDineroJ1, lblCapitalJ1, lblPosicionJ1, lblEstadoJ1);
        }
        if (jugadores.size() > 1) {
            llenarPanel(jugadores.get(1), panelJugador2, lblNombreJ2, lblDineroJ2, lblCapitalJ2, lblPosicionJ2, lblEstadoJ2);
        }
        if (jugadores.size() > 2) {
            llenarPanel(jugadores.get(2), panelJugador3, lblNombreJ3, lblDineroJ3, lblCapitalJ3, lblPosicionJ3, lblEstadoJ3);
        }
        if (jugadores.size() > 3) {
            llenarPanel(jugadores.get(3), panelJugador4, lblNombreJ4, lblDineroJ4, lblCapitalJ4, lblPosicionJ4, lblEstadoJ4);
        }
    }

    // metodo para llenar un panel de informacion de un jugador con su nombre, dinero, capital, posicion y estado actual
    private void llenarPanel(Jugador jugador, VBox panel, Label lblNombre, Label lblDinero,
        Label lblCapital, Label lblPosicion, Label lblEstado ) {
        panel.setVisible(true);
        panel.setManaged(true);

        lblNombre.setText("Nombre: " + jugador.getNombre());
        lblDinero.setText("Dinero: $" + jugador.getDinero());
        lblCapital.setText("Capital: $" + juego.calcularCapital(jugador));
        lblPosicion.setText("Posicion: " + jugador.getPosicion());
        lblEstado.setText("Estado: " + obtenerEstadoJugador(jugador));
    }

    // metodo para limpiar un panel de informacion de un jugador ocultandolo y reseteando sus labels a valores por defecto
    private void limpiarPanel(VBox panel, Label lblNombre, Label lblDinero,
        Label lblCapital, Label lblPosicion, Label lblEstado) {
        panel.setVisible(false);
        panel.setManaged(false);

        lblNombre.setText("Nombre: -");
        lblDinero.setText("Dinero: -");
        lblCapital.setText("Capital: -");
        lblPosicion.setText("Posicion: -");
        lblEstado.setText("Estado: -");
    }

    // metodo para mostrar un mensaje en el area de log agregando una nueva linea al final
    private void log(String mensaje) {
        txtLog.appendText(mensaje + "\n");
    }

    // metodos para manejar las acciones de compra, pago de renta y mejoras segun el tipo de casilla donde cayo el jugador, 
    // mostrando alertas de confirmacion para jugadores humanos y tomando decisiones automaticas para bots
    private void manejarPropiedad(Jugador jugador, Propiedad propiedad, int dineroAntes) {

    // si la propiedad no tiene dueño, ofrecer la compra al jugador, mostrando una alerta de confirmacion con la informacion de la propiedad 
    // y la renta actual segun el nivel de mejora, y tomando una decision automatica para bots basada en si tienen suficiente dinero 
    // y un valor aleatorio para simular diferentes comportamientos de compra
    if (propiedad.getDueno() == null) {
        if (jugador.isBot()) {
            if (jugador.getDinero() >= propiedad.getPrecio()) {
                propiedad.comprar(jugador);
                log(jugador.getNombre() + " compro " + propiedad.getNombre() + " por $" + propiedad.getPrecio());
            }
        } else {
            if (jugador.getDinero() >= propiedad.getPrecio()) {

                // mostrar alerta de confirmacion con la informacion de la propiedad y la renta actual segun el nivel de mejora, 
                // y preguntar si desea comprarla
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

                // si el jugador confirma la compra, ejecutar la logica de compra y mostrar un mensaje en el log, 
                // sino mostrar un mensaje de que decidio no comprar
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
    // si la propiedad tiene dueño y no es el jugador actual, calcular la renta segun el nivel de mejora, 
    // ejecutar el pago de renta del jugador al dueño y mostrar un mensaje en el log con el monto pagado y el nombre del dueño
    private void manejarServicio(Jugador jugador, Servicio servicio) {
        if (servicio.getDueno() == null) {

            if (jugador.isBot()) {
                if (jugador.getDinero() >= servicio.getPrecio()) {
                    servicio.comprar(jugador);
                    log(jugador.getNombre() + "compro el servicio" + servicio.getNombre() + "por $" + servicio.getPrecio());
                }
            } else {
                if (jugador.getDinero() >= servicio.getPrecio()) {

                    // mostrar alerta de confirmacion con la informacion del servicio y la renta actual segun la cantidad de servicios
                    // que tenga el dueño, y preguntar si desea comprarlo
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Comprar servicio");;
                    alert.setHeaderText(jugador.getNombre() + ", has caido en " + servicio.getNombre());

                    int rentaActual = servicio.calcularRenta(juego.getServicios(), jugador);

                    alert.setContentText(
                        "Precio: $" + servicio.getPrecio() + "\n" + "Renta estimada inicial: $" + rentaActual + "\n\n" + "Deseas comprar este servicio?"
                    );

                    ButtonType btnComprar = new ButtonType("Comprar");
                    ButtonType btnNoComprar = new ButtonType("No comprar", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(btnComprar, btnNoComprar);

                    Optional<ButtonType> resultado = alert.showAndWait();

                    // si el jugador confirma la compra, ejecutar la logica de compra y mostrar un mensaje en el log, 
                    // sino mostrar un mensaje de que decidio no comprar
                    if (resultado.isPresent() && resultado.get() == btnComprar) {
                        servicio.comprar(jugador);
                        log(jugador.getNombre() + " compro el servicio " + servicio.getNombre() + " por $" + servicio.getPrecio());
                    } else {
                        log(jugador.getNombre() + " decidio no comprar el servicio " + servicio.getNombre());
                    }
                } else {
                    log(jugador.getNombre() + " no tiene dinero suficiente para comprar el servicio " + servicio.getNombre());
                }
            }
        }
    }   

    // si la propiedad tiene dueño y es el jugador actual, ofrecer la mejora de la propiedad, mostrando una alerta de confirmacion 
    // con la informacion de la propiedad, el costo de mejora segun el nivel actual y preguntando si desea mejorarla,
    //  y tomando una decision automatica para bots basada en si tienen suficiente dinero, el nivel actual de mejora 
    // y un valor aleatorio para simular diferentes comportamientos de mejora
    private void manejarMejoraPropiedad(Jugador jugador, Propiedad propiedad) {
        if (propiedad.getDueno() != jugador) {
            return;
        }

        if (propiedad.getNivelMejora() >= 3) {
            log(propiedad.getNombre() + " ya esta al maximo.");
            return;
        }

        int costo = obtenerCostoMejoraSegunNivel(propiedad.getNivelMejora());

        if (jugador.getDinero() < costo) {
            log(jugador.getNombre() + " no tiene dinero suficiente para mejorar " + propiedad.getNombre());
            return;
        }

        // para bots, tomar una decision automatica basada en si tienen suficiente dinero, el nivel actual de mejora y un valor aleatorio
        //  para simular diferentes comportamientos de mejora, donde si el bot tiene suficiente dinero para mejorar y el nivel actual es bajo, 
        // tiene mas probabilidad de mejorar, y si el nivel actual es alto, tiene menos probabilidad de mejorar
        if (jugador.isBot()) {
            if (Math.random() < 0.5) {
                propiedad.mejorar();
                log(jugador.getNombre() + " mejoro " + propiedad.getNombre() + " por $" + costo);
            }
            return;
        }

        // mostrar alerta de confirmacion con la informacion de la propiedad, el costo de mejora segun el nivel actual y preguntando 
        // si desea mejorarla
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mejorar propiedad");
        alert.setHeaderText(jugador.getNombre() + ", caiste en tu propiedad");
        alert.setContentText(
            "Propiedad: " + propiedad.getNombre() + "\n" +
            "Nivel actual: " + propiedad.getNivelMejora() + "\n" +
            "Costo de mejora: $" + costo + "\n\n" +
            "Deseas mejorar esta propiedad?"
        );

        ButtonType btnSi = new ButtonType("Mejorar");
        ButtonType btnNo = new ButtonType("No mejorar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == btnSi) {
            propiedad.mejorar();
            log(jugador.getNombre() + " mejoro " + propiedad.getNombre() + " por $" + costo);
        } else {
            log(jugador.getNombre() + " decidio no mejorar " + propiedad.getNombre());
        }
    }


    // metodo para mostrar una ventana de dialogo con un combo box para seleccionar un grupo de color y un area de texto para mostrar la informacion de las propiedades 
    // de ese grupo, incluyendo el precio y la renta segun el nivel de mejora, actualizando la informacion cada vez que se selecciona un grupo diferente
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

    // metodo auxiliar para actualizar el texto del area de informacion de las propiedades segun el grupo de color seleccionado, 
    // recorriendo todas las casillas del juego y filtrando las propiedades que pertenecen al grupo seleccionado, mostrando su nombre, 
    // precio y renta segun el nivel de mejora
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

    // metodo para manejar la casilla de oportunidad de negocio, ofreciendo al jugador la posibilidad de mejorar una de sus propiedades con un 50% de descuento 
    // en el costo de mejora, mostrando una alerta de confirmacion con la informacion de las propiedades que pueden ser mejoradas y el costo con descuento,
    //  y tomando una decision automatica para bots basada en si tienen suficiente dinero, el nivel actual de mejora y 
    // un valor aleatorio para simular diferentes comportamientos de mejora
    private void manejarOportunidadNegocio(Jugador jugador) {

        if (jugador.getPropiedades().isEmpty()) {
            log(jugador.getNombre() + " cayo en Inapa, pero no tiene propiedades para mejorar.");
            return;
        }   

        // para bots, tomar una decision automatica basada en si tienen suficiente dinero, el nivel actual de mejora y un valor aleatorio para simular diferentes
        //  comportamientos de mejora, donde si el bot tiene suficiente dinero para mejorar y el nivel actual es bajo, tiene mas probabilidad de mejorar, 
        // y si el nivel actual es alto, tiene menos probabilidad de mejorar, aplicando la mejora directamente sin mostrar una alerta
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
        // mostrar alerta de confirmacion con la informacion de las propiedades que pueden ser mejoradas y el costo con descuento, y preguntar si desea mejorar alguna
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

    // metodo auxiliar para obtener el costo de mejora segun el nivel actual de mejora de una propiedad, retornando un valor fijo para cada nivel
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

    // metodo auxiliar para aumentar el nivel de mejora de una propiedad directamente sin pasar por el metodo mejorar(), utilizado para aplicar la mejora de Oportunidad Negocio
    //  con descuento, accediendo al campo privado nivelMejora mediante reflexion para modificar su valor
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

    // metodo para actualizar el estilo de los paneles de informacion de los jugadores, aplicando el color del jugador a la barra lateral del panel, 
    // resaltando el panel del jugador actual con un borde mas grueso y un fondo diferente, y reseteando el estilo de los paneles de jugadores 
    // que no estan en juego o que no son el jugador actual
    private void actualizarEstiloPaneles() {
        java.util.List<Jugador> jugadores = juego.getJugadores();
        
        resetearPanel(panelJugador1, barraJ1);
        resetearPanel(panelJugador2, barraJ2);
        resetearPanel(panelJugador3, barraJ3);
        resetearPanel(panelJugador4, barraJ4);

        if (jugadores.size() > 0) aplicarColorPanel(jugadores.get(0), panelJugador1, barraJ1);
        if (jugadores.size() > 1) aplicarColorPanel(jugadores.get(1), panelJugador2, barraJ2);
        if (jugadores.size() > 2) aplicarColorPanel(jugadores.get(2), panelJugador3, barraJ3);
        if (jugadores.size() > 3) aplicarColorPanel(jugadores.get(3), panelJugador4, barraJ4);

        Jugador actual = juego.getJugadorActual();

        if (jugadores.size() > 0 && jugadores.get(0) == actual) resaltarTurno(panelJugador1);
        if (jugadores.size() > 1 && jugadores.get(1) == actual) resaltarTurno(panelJugador2);
        if (jugadores.size() > 2 && jugadores.get(2) == actual) resaltarTurno(panelJugador3);
        if (jugadores.size() > 3 && jugadores.get(3) == actual) resaltarTurno(panelJugador4);
    }

    private void aplicarColorPanel(Jugador jugador, VBox panel, Label barra) {
        barra.setStyle("-fx-background-color: " + jugador.getColor() + ";");
        panel.setStyle("-fx-border-color: black; -fx-padding: 10;");
    }

    private void resetearPanel(VBox panel, Label barra) {
        barra.setStyle("-fx-background-color: transparent;");
        panel.setStyle("-fx-border-color: #555555; " + "-fx-border-width: 1; " + "-fx-border-radius: 10; " + "-fx-background-radius: 10; " + "-fx-background-color:linear-gradient(to bottom, #fafafa, #efefef); " + "-fx-padding: 10;");
    }

    private void resaltarTurno(VBox panel) {
        panel.setStyle("-fx-border-color: #d4a200; " + "-fx-border-width: 3; " + "-fx-border-radius: 10; " + "-fx-background-radius: 10; " + "-fx-background-color: #fffdf2; " + "-fx-padding: 10; " + "-fx-effect: dropshadow(gaussian, rgba(212,162,0,0.45), 15, 0.6, 0, 0);");
    }

    private String obtenerEstadoJugador(Jugador jugador) {
        if (jugador.estaEnBancarrota()) {
            return "En bancarrota";
        }
        if (jugador.isEnCarcel()) {
            return "En carcel";
        }
        return "Activo";
    }
}