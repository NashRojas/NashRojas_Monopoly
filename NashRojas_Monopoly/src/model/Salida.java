package model;

public class Salida extends Casilla {
    
    public Salida(int posicion, String nombre) {
        super(posicion, nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.recibir(200); 
    }
    
}
