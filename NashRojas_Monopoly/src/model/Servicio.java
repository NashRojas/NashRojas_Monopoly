package model;

import java.util.*;

public class Servicio extends Casilla {
    
    public int precio;
    public Jugador dueno;

    public Servicio(int posicion, String nombre, int precio) {
        super(posicion, nombre);
        this.precio = precio;
        this.dueno = null;
    }

    // al caer en una casilla de servicio, si la casilla no tiene dueño, el jugador puede comprarla pagando su precio, y se muestra un mensaje indicando que el jugador ha comprado el servicio, si la casilla tiene dueño y no es el jugador que cayó, el jugador debe pagar la renta calculada al dueño, y se muestra un mensaje indicando que el jugador ha pagado la renta al dueño, y si la casilla tiene dueño y es el jugador que cayó, no se hace nada
    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        if (dueno == null) {
            
        } else if (dueno != jugador) {
            int renta = calcularRenta(juego.getServicios(), dueno);
            jugador.pagar(renta);
            dueno.recibir(renta);
        }
    }

    // metodo para calcular la renta de un servicio, recibiendo la lista de servicios del juego y el dueño actual del servicio como parametros, contando cuantas casillas de servicio tiene el mismo dueño, y devolviendo la renta correspondiente segun la cantidad de servicios que tenga el dueño
    public int calcularRenta(List<Servicio> servicios, Jugador duenoActual) {
        int cantidad = 0;
        for (Servicio s : servicios) {
            if (s.dueno == duenoActual) {
                cantidad++;
            }
        }

        switch (cantidad) {
            case 1:
                return 30;
            case 2:
                return 70;
            case 3:
                return 110;
            case 4:
                return 160;
            default:
                return 0;
        }
    }

    // metodo para comprar un servicio, asignando el dueño del servicio al jugador que lo compra, pagando el precio del servicio, 
    // y agregando el servicio a la lista de servicios del jugador
    public void comprar(Jugador jugador){
        this.dueno = jugador;
        jugador.pagar(precio);
        jugador.agregarServicio(this);
    }

    public Jugador getDueno() {
        return dueno;
    }

    public void liberar() {
    this.dueno = null;
    }

    public int getPrecio() {
    return precio;
    }
}
