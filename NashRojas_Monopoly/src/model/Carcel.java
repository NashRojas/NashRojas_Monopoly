package model;

public class Carcel extends Casilla {
    
    public Carcel(int posicion, String nombre) {
        super(posicion, nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        // No se hace nada al caer en la cárcel, solo se muestra un mensaje, ya que la lógica de enviar a la cárcel se maneja en el controlador del juego
    }
    
}
