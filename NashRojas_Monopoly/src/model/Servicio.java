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

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        if (dueno == null) {
            
        } else if (dueno != jugador) {
            int renta = calcularRenta(juego.getServicios(), dueno);
            jugador.pagar(renta);
            dueno.recibir(renta);
        }
    }

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
