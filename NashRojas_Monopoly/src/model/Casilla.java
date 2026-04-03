package model;

public abstract class Casilla {
    
    protected int posicion;
    protected String nombre;

    public Casilla(int posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
    }

    public abstract void ejecutarAccion(Jugador jugador, Juego juego);

    public int getPosicion() {
        return posicion;
    }

    public String getNombre(){
        return nombre;
    }
}
