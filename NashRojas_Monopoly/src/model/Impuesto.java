package model;

public class Impuesto extends Casilla {
    
    private int monto;

    public Impuesto(int posicion, String nombre, int monto) {
        super(posicion, nombre);
        this.monto = monto;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.pagar(monto);
    }
    
}
