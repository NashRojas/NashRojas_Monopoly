package model;

public class Salida extends Casilla {
    
    public Salida(int posicion, String nombre) {
        super(posicion, nombre);
    }

    // al caer en la casilla de salida, el jugador recibe $200, y se muestra un mensaje indicando que el jugador ha pasado por la casilla de salida 
    // y ha recibido $200
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.recibir(200); 
    }
    
}
