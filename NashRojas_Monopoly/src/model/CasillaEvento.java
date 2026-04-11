package model;

import java.util.Random;

public class CasillaEvento extends Casilla {

    private Random random;
    private String ultimoMensaje;

    public CasillaEvento(int posicion, String nombre) {
        super(posicion, nombre);
        this.random = new Random();
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {

        int evento = random.nextInt(8);

        switch (evento) {

            case 0:
                jugador.recibir(100);
                ultimoMensaje = jugador.getNombre() + " recibió $100.";
                break;

            case 1:
                jugador.pagar(50);
                ultimoMensaje = jugador.getNombre() + " pagó $50.";
                break;

            case 2:
                jugador.mover(3);
                ultimoMensaje = jugador.getNombre() + " se movió 3 casillas.";

                break;

            case 3:
                jugador.mover(-2);
                ultimoMensaje = jugador.getNombre() + " retrocedió 2 casillas.";
                break;

            case 4:
                juego.moverJugadorA(jugador, 15);
                ultimoMensaje = jugador.getNombre() + " fue movido a la casilla 15.";
                break;

            case 5:
                juego.moverJugadorA(jugador, 24);
                ultimoMensaje = jugador.getNombre() + " fue movido a la casilla 24.";

                break;

            case 6:
                juego.moverJugadorA(jugador, 29);
                ultimoMensaje = jugador.getNombre() + " fue movido a la casilla 29.";
                break;

            case 7:
                jugador.setPosicion(10);
                jugador.setEnCarcel(true);
                jugador.setTurnosEnCarcel(1);
                ultimoMensaje = jugador.getNombre() + " fue enviado a la cárcel por 1 turno.";
                break;
        }
    }
}