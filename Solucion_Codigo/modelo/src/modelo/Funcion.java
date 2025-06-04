package modelo;

import java.io.Serializable;
import java.time.LocalTime; // Necesario para la hora

public class Funcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pelicula pelicula;      // La película que se mostrará
    private LocalTime hora;         // La hora en que empieza la función
    private String sala;            // La sala donde se proyecta (ej. "Sala 1")
    private int asientosDisponibles; // Cuántos asientos quedan
    private String dia;             // El día de la semana (ej. "Lunes", "Sábado")

    // Constructor para crear una función
    public Funcion(Pelicula pelicula, LocalTime hora, String sala, int asientosDisponibles, String dia) {
        this.pelicula = pelicula;
        this.hora = hora;
        this.sala = sala;
        this.asientosDisponibles = asientosDisponibles;
        this.dia = dia;
    }

    // --- Getters ---
    public Pelicula getPelicula() {
        return pelicula;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getSala() {
        return sala;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public String getDia() {
        return dia;
    }

    // --- Setters ---
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    // Método para imprimir la información de la función
    @Override
    public String toString() {
        // Usa el toString de Pelicula para mostrar su título
        return "Función de: " + pelicula.getTitulo() + " | Hora: " + hora + " | Sala: " + sala + " | Día: " + dia + " | Asientos: " + asientosDisponibles;
    }
}