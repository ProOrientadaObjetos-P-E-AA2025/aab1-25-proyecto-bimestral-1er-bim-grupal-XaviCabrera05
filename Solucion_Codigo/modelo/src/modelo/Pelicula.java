package modelo;

import java.io.Serializable;

public class Pelicula implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String genero;
    private int duracion;
    private String clasificacion;

    // Constructor básico para crear una película
    public Pelicula(String titulo, String genero, int duracion, String clasificacion) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
    }

    // --- Getters (para obtener los datos de la película) ---
    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    // --- Setters (para cambiar los datos de la película) ---
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    // Método para imprimir la información de la película de forma legible
    @Override
    public String toString() {
        return "Pelicula: " + titulo + " (" + genero + ", " + duracion + "min, " + clasificacion + ")";
    }
}