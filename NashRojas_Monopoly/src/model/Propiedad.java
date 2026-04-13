package model;

public class Propiedad extends Casilla {
    
    private int precio;
    private int rentaBase;
    private String color;
    private Jugador dueno;
    private int nivelMejora;

    public Propiedad(int posicion, String nombre, int precio, int rentaBase, String color) {
        super(posicion, nombre);
        this.precio = precio;
        this.rentaBase = rentaBase;
        this.color = color;
        this.dueno = null;
        this.nivelMejora = 0;
    }

    // al caer en una casilla de propiedad, si la propiedad no tiene dueño, el jugador puede comprarla pagando su precio, 
    // y se muestra un mensaje indicando que el jugador ha comprado la propiedad, si la propiedad tiene dueño y no es el jugador que cayó, 
    // el jugador debe pagar la renta calculada al dueño, y se muestra un mensaje indicando que el jugador ha pagado la renta al dueño, 
    // y si la propiedad tiene dueño y es el jugador que cayó, no se hace nada
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        if (dueno == null) {
            
        } else if (dueno != jugador) {
            int renta = calcularRenta();
            jugador.pagar(renta);
            dueno.recibir(renta);
        }
    }

    public int calcularRenta() {
        return (int) (rentaBase * (1 + 0.5 * nivelMejora));
    }

    public void comprar(Jugador jugador) {
        this.dueno = jugador;
        jugador.pagar(precio);
        jugador.agregarPropiedad(this);
    }

    public void mejorar(){
        if (nivelMejora < 3) {
            int costo = obtenerCostoMejora();
            dueno.pagar(costo);
            nivelMejora++;
        }
    }

    private int obtenerCostoMejora(){
        switch (nivelMejora) {
            case 0:
                return 100;
                
            case 1:
                return 150;
                
            case 2:
                return 200;
                
            default:
                return 0;
        }
    }

    public Jugador getDueno() {
        return dueno;
    }

    public String getColor(){
        return color;
    }

    public int getNivelMejora() {
        return nivelMejora;
    }

    public void liberar() {
    this.dueno = null;
    this.nivelMejora = 0;
    }

    public int getPrecio() {
    return precio;
    }

    public void mejorarConCostoPersonalizado(Jugador jugador, int costo) {
        if (nivelMejora < 3 && jugador.getDinero() >= costo) {
            jugador.pagar(costo);
            nivelMejora++;
        }
    }
}
