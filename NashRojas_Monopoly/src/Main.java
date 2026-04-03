import model.*;

public class Main {

    public static void main(String[] args) {

        Juego juego = new Juego();

        
        Jugador j1 = new Jugador("Nash");
        Jugador j2 = new Jugador("CPU");

        juego.agregarJugador(j1);
        juego.agregarJugador(j2);

        
        inicializarTablero(juego);

        
        for (int i = 0; i < 20; i++) {
            System.out.println("\n--- TURNO " + (i + 1) + " ---");

            Jugador actual = juego.getJugadorActual();
            System.out.println("Turno de: " + actual.getNombre());

            juego.ejecutarTurno();

            System.out.println("Posicion: " + actual.getPosicion());
            System.out.println("Dinero: " + actual.getDinero());

            if (juego.hayGanador()) {
                System.out.println("Hay ganador!");
                break;
            }
        }
    }

    public static void inicializarTablero(Juego juego) {

    for (int i = 0; i < 40; i++) {
        juego.setCasilla(i, new CasillaEvento(i, "Evento"));
    }

    
    juego.setCasilla(0, new Salida(0, "Salida"));
    juego.setCasilla(4, new Impuesto(4, "Impuesto", 20));
    juego.setCasilla(30, new Carcel(30, "Carcel"));
    juego.setCasilla(38, new IrACarcel(38, "Ir a Carcel", 30));

    
    juego.setCasilla(1, new Propiedad(1, "Prop1", 100, 20, "Marron"));
    juego.setCasilla(3, new Propiedad(3, "Prop2", 100, 20, "Marron"));

    
    juego.setCasilla(5, new Servicio(5, "Luz", 150));
    juego.setCasilla(16, new Servicio(16, "Transporte", 150));
    }
}