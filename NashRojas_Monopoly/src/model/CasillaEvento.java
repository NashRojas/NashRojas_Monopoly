package model;

import java.util.Random;

public class CasillaEvento extends Casilla {

    private Random random;

    public CasillaEvento(int posicion, String nombre) {
        super(posicion, nombre);
        this.random = new Random();
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {

        int evento = random.nextInt(8);

        switch (evento) {

            case 0:
                jugador.recibir(100);
                break;

            case 1:
                jugador.pagar(50);
                break;

            case 2:
                jugador.mover(3);
                break;

            case 3:
                jugador.mover(-2);
                break;

            case 4:
                juego.moverJugadorA(jugador, 15);
                break;

            case 5:
                juego.moverJugadorA(jugador, 24);
                break;

            case 6:
                juego.moverJugadorA(jugador, 29);
                break;

            case 7:
                jugador.setTurnosEnCarcel(1);
                break;
        }
    }
}