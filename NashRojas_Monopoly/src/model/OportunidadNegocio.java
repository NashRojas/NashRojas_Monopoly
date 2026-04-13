package model;

public class OportunidadNegocio extends Casilla {
    
    public OportunidadNegocio(int posicion, String nombre) {
        super(posicion, nombre);
    }

    // al caer en una casilla de oportunidad de negocio, el jugador recibe una cantidad de dinero aleatoria entre $100 y $500, 
    // y se muestra un mensaje indicando que el jugador ha recibido una oportunidad de negocio y la cantidad recibida
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {

    }
}
