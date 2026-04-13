package model;

public class IrACarcel extends Casilla {
    private int posicionCarcel;
    
    public IrACarcel(int posicion, String nombre, int posicionCarcel) {
        super(posicion, nombre);
        this.posicionCarcel = posicionCarcel;

    }

    // al caer en una casilla de ir a la carcel, el jugador es movido a la posicion de la carcel, se le marca como en carcel,
    //  y se le asignan 3 turnos en carcel para que pueda salir, y se muestra un mensaje indicando que el jugador ha sido enviado a la carcel
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.setPosicion(posicionCarcel);
        jugador.setEnCarcel(true);
        jugador.setTurnosEnCarcel(3);
    }
    
}
