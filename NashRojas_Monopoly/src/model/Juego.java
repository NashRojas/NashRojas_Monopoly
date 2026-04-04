package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {

    private List<Jugador> jugadores;
    private Casilla[] tablero;
    private int turnoActual;
    private Random random;

    public Juego() {
        jugadores = new ArrayList<>();
        tablero = new Casilla[40];
        turnoActual = 0;
        random = new Random();
    }



    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }



    public void setCasilla(int posicion, Casilla casilla) {
        tablero[posicion] = casilla;
    }

    public Casilla getCasilla(int posicion) {
        return tablero[posicion];
    }

        public int lanzarDado() {
        return random.nextInt(6) + 1;
    }

    public void moverJugador(Jugador jugador, int pasos) {

        int posicionAnterior = jugador.getPosicion();
        int nuevaPosicion = (posicionAnterior + pasos) % 40;


        if (posicionAnterior + pasos >= 40) {
            jugador.recibir(200);
        }

        jugador.setPosicion(nuevaPosicion);

        Casilla casilla = tablero[nuevaPosicion];
        casilla.ejecutarAccion(jugador, this);
    }

        public void moverJugadorA(Jugador jugador, int posicion) {
        jugador.setPosicion(posicion);
        tablero[posicion].ejecutarAccion(jugador, this);
    }

        public int ejecutarTurno() {

        Jugador jugador = getJugadorActual();

        if (jugador.isEnCarcel()) {

            if (jugador.getTurnosEnCarcel() > 0) {
                jugador.setTurnosEnCarcel(jugador.getTurnosEnCarcel() - 1);

                System.out.println(jugador.getNombre() + " esta en la carcel. Turnos restantes: " 
                    + jugador.getTurnosEnCarcel());

                siguienteTurno();
                return 0;

            } else {
                jugador.setEnCarcel(false);
                System.out.println(jugador.getNombre() + " sale de la carcel.");
            }
        }

        int dado = lanzarDado();
        moverJugador(jugador, dado);

        verificarBancarrota();

        siguienteTurno();
        return dado;
    }

        public void siguienteTurno() {

        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } while (jugadores.get(turnoActual).estaEnBancarrota());
    }

        public void verificarBancarrota() {

        List<Jugador> eliminados = new ArrayList<>();

        for (Jugador j : jugadores) {
            if (j.estaEnBancarrota()) {

                
                for (Propiedad p : j.getPropiedades()) {
                    p.liberar();
                }

                
                for (Servicio s : j.getServicios()) {
                    s.liberar();
                }

                eliminados.add(j);
            }
        }

        jugadores.removeAll(eliminados);
    }

        public List<Servicio> getServicios() {
        List<Servicio> servicios = new ArrayList<>();

        for (Casilla c : tablero) {
            if (c instanceof Servicio) {
                servicios.add((Servicio) c);
            }
        }

        return servicios;
    }

        public boolean hayGanador() {
        return jugadores.size() == 1 || alguienLlegoA7000();
    }

    private boolean alguienLlegoA7000() {
        for (Jugador j : jugadores) {
            if (calcularCapital(j) >= 7000) {
                return true;
            }
        }
        return false;
    }

        public int calcularCapital(Jugador j) {

        int total = j.getDinero();

        for (Propiedad p : j.getPropiedades()) {
            total += p.getPrecio();
        }

        for (Servicio s : j.getServicios()) {
            total += s.getPrecio();
        }

        return total;
    }
}