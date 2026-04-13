package model;

public class Impuesto extends Casilla {
    
    private int monto;

    public Impuesto(int posicion, String nombre, int monto) {
        super(posicion, nombre);
        this.monto = monto;
    }

    // al caer en una casilla de impuesto, el jugador debe pagar el monto especificado al banco, 
    // y se muestra un mensaje indicando que el jugador ha pagado el impuesto
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.pagar(monto);
    }
    
}
