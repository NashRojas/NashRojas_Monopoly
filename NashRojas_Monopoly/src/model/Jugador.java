package model;

import java.util.*;

public class Jugador {
    
    private String nombre;
    private int dinero;
    private int posicion;
    private boolean enCarcel;
    private int turnosEnCarcel;
    private boolean esBot;
    private String color;

    private List<Propiedad> propiedades;
    private List<Servicio> servicios;

    public Jugador(String nombre, boolean esBot, String color) {
        this.nombre = nombre;
        this.dinero = 1000; 
        this.posicion = 0; 
        this.enCarcel = false;
        this.turnosEnCarcel = 0;
        this.esBot = esBot;
        this.color = color;
        this.propiedades = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    public void mover(int pasos) {
    posicion = (posicion + pasos) % 40;
    if (posicion < 0) {
        posicion += 40;
        }
    }

    public void pagar(int monto) {
        this.dinero -= monto;
    }

    public void recibir(int monto) {
        this.dinero += monto;
    }

    public void agregarPropiedad(Propiedad p) {
        propiedades.add(p);
    }

    public void agregarServicio(Servicio s) {
        servicios.add(s);
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getDinero() {
        return dinero;
    }
    public int getPosicion() {
        return posicion;
    }
    public boolean isEnCarcel() {
        return enCarcel;
    }
    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public void setTurnosEnCarcel(int turnos) {
        this.turnosEnCarcel = turnos;
    }

    public boolean estaEnBancarrota() {
        return dinero < -500;
    }

    public void setPosicion(int posicion) {
    this.posicion = posicion;
    }

    public boolean isBot() {
        return esBot;
    }

    public String getColor() {
        return color;
    }
} 
