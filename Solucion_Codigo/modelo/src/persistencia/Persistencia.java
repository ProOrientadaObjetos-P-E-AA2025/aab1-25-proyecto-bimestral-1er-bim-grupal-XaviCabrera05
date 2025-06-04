package persistencia;

import modelo.Cartelera;
import modelo.Promocion;
import modelo.Snack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    private static final String CARTELERA_FILE = "cartelera.dat";
    private static final String PROMOCIONES_FILE = "promociones.dat";
    private static final String SNACKS_FILE = "snacks.dat";
    
    //Los metodos guardar y cargar sirven como una captura de pantalla en la cual, los objetos se cargan de manera que al abrir el programa de nuevo vuelve a estar todo tal cual a lo q se lo cerro

    public static void guardarCartelera(Cartelera cartelera) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CARTELERA_FILE))) {
            oos.writeObject(cartelera);
            System.out.println("✅ Cartelera guardada.");
        } catch (IOException e) {
            System.err.println("❌ Error al guardar la cartelera: " + e.getMessage());
        }
    }

    public static Cartelera cargarCartelera() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CARTELERA_FILE))) {
            return (Cartelera) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ No se pudo cargar la cartelera, se usará una nueva.");
            return new Cartelera();
        }
    }

    public static void guardarPromociones(List<Promocion> promociones) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROMOCIONES_FILE))) {
            oos.writeObject(promociones);
            System.out.println("✅ Promociones guardadas.");
        } catch (IOException e) {
            System.err.println("❌ Error al guardar las promociones: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Promocion> cargarPromociones() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PROMOCIONES_FILE))) {
            return (List<Promocion>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ No se pudieron cargar las promociones, se usará una lista vacía.");
            return new ArrayList<>();
        }
    }

    public static void guardarSnacks(List<Snack> snacks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SNACKS_FILE))) {
            oos.writeObject(snacks);
            System.out.println("✅ Snacks guardados.");
        } catch (IOException e) {
            System.err.println("❌ Error al guardar los snacks: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Snack> cargarSnacks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SNACKS_FILE))) {
            return (List<Snack>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ No se pudieron cargar los snacks, se usará una lista vacía.");
            return new ArrayList<>();
        }
    }
}
