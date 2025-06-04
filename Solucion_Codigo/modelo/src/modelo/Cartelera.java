package modelo;

import java.io.Serializable;
import java.time.LocalTime; // Necesario para buscar por hora
import java.util.ArrayList;
import java.util.List;

public class Cartelera implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Funcion> funciones; // Una lista de todas las funciones disponibles

    public Cartelera() {
        this.funciones = new ArrayList<>();
    }

    // Método para añadir una función a la cartelera
    public void agregarFuncion(Funcion funcion) {
        funciones.add(funcion);
    }

    // Método para obtener todas las funciones
    public List<Funcion> getFunciones() {
        return funciones;
    }

    // Método para buscar una función específica
    // IMPORTANTE: Con la simplificación de Funcion y Pelicula,
    // esta búsqueda compara los campos manualmente.
    public Funcion buscarFuncion(String tituloPelicula, LocalTime hora, String sala, String dia) {
        for (Funcion funcion : funciones) {
            // Comparamos el título de la película (ignorando mayúsculas/minúsculas)
            // la hora, la sala (ignorando mayúsculas/minúsculas) y el día (ignorando mayúsculas/minúsculas).
            // ¡OJO! Esto asume que Pelicula.getTitulo() devuelve el título correcto.
            if (funcion.getPelicula().getTitulo().equalsIgnoreCase(tituloPelicula)
                    && funcion.getHora().equals(hora)
                    && funcion.getSala().equalsIgnoreCase(sala)
                    && funcion.getDia().equalsIgnoreCase(dia)) {
                return funcion; // Encontramos la función, la devolvemos
            }
        }
        return null; // No encontramos la función
    }
}
