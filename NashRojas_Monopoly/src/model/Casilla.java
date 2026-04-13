package model;

public abstract class Casilla {
    
    protected int posicion;
    protected String nombre;

    public Casilla(int posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
    }

    // metodo abstracto para ejecutar la accion correspondiente al tipo de casilla, recibiendo el jugador que cayo en la casilla y el juego como parametros, 
    // y que sera implementado por cada subclase de Casilla para definir su comportamiento especifico
    public abstract void ejecutarAccion(Jugador jugador, Juego juego);

    public int getPosicion() {
        return posicion;
    }

    public String getNombre(){
        return nombre;
    }
}
