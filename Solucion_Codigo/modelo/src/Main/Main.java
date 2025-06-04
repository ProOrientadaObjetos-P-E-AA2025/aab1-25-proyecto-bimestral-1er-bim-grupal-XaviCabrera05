package main;

import modelo.*;
import persistencia.Persistencia;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException; // Para manejar errores de entrada
import java.util.List;
import java.util.Scanner; // Para la entrada del usuario

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- 1. Cargar datos iniciales ---
        // Se intenta cargar los datos desde los archivos.
        // Si no existen o hay un error, se inicializarán con datos de ejemplo.
        Cartelera cartelera = Persistencia.cargarCartelera();
        List<Promocion> promociones = Persistencia.cargarPromociones();
        List<Snack> inventarioSnacks = Persistencia.cargarSnacks();

        // --- 2. Inicializar datos si están vacíos (primera ejecución) ---
        if (cartelera.getFunciones().isEmpty()) {
            System.out.println("No se encontraron datos guardados. Creando datos iniciales del cine...");
            Pelicula peli1 = new Pelicula("El Padrino", "Drama", 175, "R");
            Pelicula peli2 = new Pelicula("Mi Vecino Totoro", "Animación", 86, "G");
            Pelicula peli3 = new Pelicula("Interstellar", "Ciencia Ficción", 169, "PG-13");

            cartelera.agregarFuncion(new Funcion(peli1, LocalTime.of(18, 30), "Sala A", 30, "Sábado"));
            cartelera.agregarFuncion(new Funcion(peli2, LocalTime.of(15, 0), "Sala B", 25, "Domingo"));
            cartelera.agregarFuncion(new Funcion(peli3, LocalTime.of(20, 0), "Sala A", 40, "Viernes"));
            System.out.println("Cartelera, promociones y snacks iniciales generados.");
        } else {
            System.out.println("Datos cargados exitosamente desde los archivos.");
        }

        if (promociones.isEmpty()) {
            if (!cartelera.getFunciones().isEmpty()) {
                // Asumiendo que la primera función es "El Padrino" del sábado
                promociones.add(new Promocion("Sábado", 0.15, cartelera.getFunciones().get(0)));
                // Asumiendo que la segunda función es "Mi Vecino Totoro" del domingo
                if (cartelera.getFunciones().size() > 1) {
                    promociones.add(new Promocion("Domingo", 0.10, cartelera.getFunciones().get(1)));
                }
            }
        }

        if (inventarioSnacks.isEmpty()) {
            inventarioSnacks.add(new Snack("Palomitas Grandes", 4.50, 10));
            inventarioSnacks.add(new Snack("Coca-Cola", 2.00, 20));
            inventarioSnacks.add(new Snack("Nachos", 5.00, 5));
        }

        // --- Bucle principal del menú interactivo ---
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL DEL CINE ---");
            System.out.println("1. Ver Cartelera");
            System.out.println("2. Comprar Boletos");
            System.out.println("3. Comprar Snacks");
            System.out.println("4. Ver Promociones");
            System.out.println("0. Salir y Guardar Datos");
            System.out.print("Elija una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        mostrarCartelera(cartelera);
                        break;
                    case 2:
                        comprarBoletos(scanner, cartelera, promociones);
                        break;
                    case 3:
                        comprarSnacks(scanner, inventarioSnacks);
                        break;
                    case 4:
                        mostrarPromociones(promociones);
                        break;
                    case 0:
                        System.out.println("Saliendo del programa. Guardando datos...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer de entrada para evitar un bucle infinito
                opcion = -1; // Para que el bucle continúe
            }

        } while (opcion != 0);

        // --- Guardar datos al finalizar ---
        Persistencia.guardarCartelera(cartelera);
        Persistencia.guardarPromociones(promociones);
        Persistencia.guardarSnacks(inventarioSnacks);
        scanner.close(); // Cerrar el scanner
        System.out.println("¡Hasta luego!");
    }

    // --- Métodos auxiliares para las opciones del menú ---

    private static void mostrarCartelera(Cartelera cartelera) {
        System.out.println("\n--- CARTELERA ACTUAL ---");
        if (cartelera.getFunciones().isEmpty()) {
            System.out.println("La cartelera está vacía en este momento.");
            return;
        }
        int i = 1;
        for (Funcion funcion : cartelera.getFunciones()) {
            System.out.println((i++) + ". " + funcion);
        }
    }

    private static void comprarBoletos(Scanner scanner, Cartelera cartelera, List<Promocion> promociones) {
        System.out.println("\n--- COMPRA DE BOLETOS ---");
        if (cartelera.getFunciones().isEmpty()) {
            System.out.println("No hay funciones disponibles en la cartelera.");
            return;
        }

        mostrarCartelera(cartelera);
        System.out.print("Ingrese el número de la función que desea (0 para cancelar): ");
        int funcionSeleccionada = -1;
        try {
            funcionSeleccionada = scanner.nextInt();
            scanner.nextLine();

            if (funcionSeleccionada == 0) {
                System.out.println("Compra de boletos cancelada.");
                return;
            }

            if (funcionSeleccionada < 1 || funcionSeleccionada > cartelera.getFunciones().size()) {
                System.out.println("Número de función inválido.");
                return;
            }

            Funcion funcionElegida = cartelera.getFunciones().get(funcionSeleccionada - 1);

            System.out.println("Ha seleccionado: " + funcionElegida.getPelicula().getTitulo() + " (" + funcionElegida.getDia() + " " + funcionElegida.getHora() + ")");
            System.out.println("Asientos disponibles: " + funcionElegida.getAsientosDisponibles());

            if (funcionElegida.getAsientosDisponibles() <= 0) {
                System.out.println("Lo sentimos, no hay asientos disponibles para esta función.");
                return;
            }

            System.out.print("¿Cuántos boletos desea comprar? ");
            int cantidadBoletos = scanner.nextInt();
            scanner.nextLine();

            if (cantidadBoletos <= 0 || cantidadBoletos > funcionElegida.getAsientosDisponibles()) {
                System.out.println("Cantidad de boletos inválida o excede los asientos disponibles.");
                return;
            }

            double precioBaseBoleto = 6.0; // Precio base por boleto

            Boleto nuevoBoleto = new Boleto(funcionElegida, cantidadBoletos, precioBaseBoleto);

            // Buscar promoción aplicable
            Promocion promocionAplicable = null;
            for (Promocion promo : promociones) {
                // Aquí, Funcion.equals() es CRÍTICO para que la promoción se aplique correctamente
                if (promo.getFuncion().equals(funcionElegida) && promo.getDia().equalsIgnoreCase(funcionElegida.getDia())) {
                    promocionAplicable = promo;
                    break;
                }
            }

            double totalConDescuento = nuevoBoleto.calcularTotalConDescuento(promocionAplicable);

            // Crear la venta y agregar el boleto
            Venta nuevaVenta = new Venta();

            // el Boleto necesita que su 'total' interno se actualice o se pase el totalConDescuento.
            // La forma más simple es que el Boleto.calcularTotalConDescuento() modifique el campo 'total' del boleto.
            // Si no, la Venta sumará el total base del boleto.
            nuevaVenta.agregarBoleto(nuevoBoleto); // Agrega el boleto con su total (ya actualizado por calcularTotalConDescuento si lo modificaste)

            System.out.println("\nResumen de Compra:");
            System.out.println("Función: " + funcionElegida.getPelicula().getTitulo());
            System.out.println("Cantidad de boletos: " + cantidadBoletos);
            System.out.println("Precio unitario: $" + String.format("%.2f", precioBaseBoleto));
            System.out.println("Subtotal sin descuento: $" + String.format("%.2f", (precioBaseBoleto * cantidadBoletos)));

            if (promocionAplicable != null) {
                System.out.println("Promoción aplicada: " + (promocionAplicable.getPorcentaje() * 100) + "% de descuento el " + promocionAplicable.getDia());
            } else {
                System.out.println("No se aplicó promoción para esta función.");
            }
            System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", totalConDescuento));

            // Actualizar asientos disponibles
            funcionElegida.setAsientosDisponibles(funcionElegida.getAsientosDisponibles() - cantidadBoletos);
            System.out.println("¡Compra de boletos realizada con éxito! Disfrute su función.");

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Número de función fuera de rango.");
        }
    }

    private static void comprarSnacks(Scanner scanner, List<Snack> inventarioSnacks) {
        System.out.println("\n--- COMPRA DE SNACKS ---");
        if (inventarioSnacks.isEmpty()) {
            System.out.println("No hay snacks disponibles en el inventario.");
            return;
        }

        Venta ventaSnacks = new Venta();
        boolean seguirComprando = true;

        while (seguirComprando) {
            System.out.println("\n--- INVENTARIO DE SNACKS ---");
            int i = 1;
            for (Snack snack : inventarioSnacks) {
                System.out.println((i++) + ". " + snack.getNombre() + " - $" + String.format("%.2f", snack.getPrecioUnitario()) + " (Stock: " + snack.getCantidad() + ")");
            }
            System.out.println("0. Terminar compra de snacks");
            System.out.print("Elija un snack por número (0 para terminar): ");

            try {
                int snackElegidoIndex = scanner.nextInt();
                scanner.nextLine();

                if (snackElegidoIndex == 0) {
                    seguirComprando = false;
                    break;
                }

                if (snackElegidoIndex < 1 || snackElegidoIndex > inventarioSnacks.size()) {
                    System.out.println("Número de snack inválido.");
                    continue;
                }

                Snack snackSeleccionado = inventarioSnacks.get(snackElegidoIndex - 1);

                System.out.print("¿Cuántas unidades de '" + snackSeleccionado.getNombre() + "' desea comprar? ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();

                if (cantidad <= 0 || cantidad > snackSeleccionado.getCantidad()) {
                    System.out.println("Cantidad inválida o excede el stock disponible.");
                    continue;
                }

                // Agrega el snack a la venta
                ventaSnacks.agregarSnack(new Snack(snackSeleccionado.getNombre(), snackSeleccionado.getPrecioUnitario(), cantidad));
                // Reduce el stock del inventario
                snackSeleccionado.setCantidad(snackSeleccionado.getCantidad() - cantidad);

                System.out.println(cantidad + " unidades de '" + snackSeleccionado.getNombre() + "' agregadas a su compra.");
                System.out.println("Total actual de snacks: $" + String.format("%.2f", ventaSnacks.getTotal()));

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }

        System.out.println("\n--- RESUMEN FINAL COMPRA DE SNACKS ---");
        if (ventaSnacks.getSnacks().isEmpty()) {
            System.out.println("No se compraron snacks.");
        } else {
            for (Snack s : ventaSnacks.getSnacks()) {
                System.out.println("- " + s.getNombre() + " x" + s.getCantidad() + " = $" + String.format("%.2f", s.getSubtotal()));
            }
            System.out.println("Total final de la compra de snacks: $" + String.format("%.2f", ventaSnacks.getTotal()));
        }
        System.out.println("Compra de snacks finalizada.");
    }

    private static void mostrarPromociones(List<Promocion> promociones) {
        System.out.println("\n--- PROMOCIONES DISPONIBLES ---");
        if (promociones.isEmpty()) {
            System.out.println("No hay promociones activas en este momento.");
            return;
        }
        for (Promocion promo : promociones) {
            String funcionInfo = (promo.getFuncion() != null) ?
                                  " para la función de '" + promo.getFuncion().getPelicula().getTitulo() + "' (" + promo.getFuncion().getDia() + " " + promo.getFuncion().getHora() + ")" :
                                  " (válido en funciones específicas)";
            System.out.println("- " + (promo.getPorcentaje() * 100) + "% de descuento los " + promo.getDia() + funcionInfo);
        }
    }
}