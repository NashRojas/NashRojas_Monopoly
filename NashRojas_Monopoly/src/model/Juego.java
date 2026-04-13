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


    // metodo para agregar un jugador al juego, recibiendo el jugador como parametro y añadiendolo a la lista de jugadores
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

    // metodo para mover a un jugador una cantidad de pasos determinada, recibiendo el jugador y los pasos como parametros, calculando la nueva posicion del jugador sumando los pasos a su posicion actual y aplicando el modulo 40 para que no se pase del tablero, verificando si el jugador paso por la casilla de salida para recibir $200, actualizando la posicion del jugador, obteniendo la casilla correspondiente a la nueva posicion y ejecutando su accion, y luego verificando si algun jugador quedo en bancarrota para eliminarlo del juego
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

    // metodo para verificar si algun jugador quedo en bancarrota, recorriendo la lista de jugadores y comprobando si cada uno esta en bancarrota,
    //  y si es asi, liberando sus propiedades y servicios para que puedan ser comprados por otros jugadores, 
    // y luego eliminando al jugador de la lista de jugadores activos
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
        // metodo para obtener una lista de todas las propiedades del juego, recorriendo el arreglo de casillas del tablero y añadiendo a la lista las casillas que sean instancias de Propiedad, y devolviendo la lista de propiedades disponibles en el juego
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

    // metodo para verificar si algun jugador ha alcanzado o superado un capital de $7000, recorriendo la lista de jugadores y calculando su capital sumando su dinero en efectivo y el valor de sus propiedades y servicios, y devolviendo true si algun jugador cumple con esta condicion, o false en caso contrario
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