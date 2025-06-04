package modelo;

import java.io.Serializable; 

public class Promocion implements Serializable { 
    private static final long serialVersionUID = 1L;

    private String dia;
    private double porcentaje;
    private Funcion funcion;

    public Promocion(String dia, double porcentaje, Funcion funcion) {
        this.dia = dia;
        this.porcentaje = porcentaje;
        this.funcion = funcion;
    }

    // --- Getters ---
    public String getDia() {
        return dia;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    // --- Setters ---
    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public double aplicarDescuento(double montoOriginal) {
        return montoOriginal - (montoOriginal * porcentaje);
    }
}