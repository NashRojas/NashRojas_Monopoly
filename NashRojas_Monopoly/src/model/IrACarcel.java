package model;

public class IrACarcel extends Casilla {
    private int posicionCarcel;
    
    public IrACarcel(int posicion, String nombre, int posicionCarcel) {
        super(posicion, nombre);
        this.posicionCarcel = posicionCarcel;

    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.setPosicion(posicionCarcel);
        jugador.setEnCarcel(true);
        jugador.setTurnosEnCarcel(3);
    }
    
}
