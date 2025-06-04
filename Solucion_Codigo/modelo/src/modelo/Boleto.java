package modelo;

import java.io.Serializable;

public class Boleto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int contadorBoletos = 1;

    private String id;
    private Funcion funcion;
    private int cantidad;
    private double precioUnitario;
    private double total;

    public Boleto(Funcion funcion, int cantidad, double precioUnitario) {
        this.id = "BOLETO" + contadorBoletos;
        contadorBoletos++;
        this.funcion = funcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = calcularTotal();
    }

    public String getId() {
        return id;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.total = calcularTotal();
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return this.cantidad * this.precioUnitario;
    }

    public double calcularTotalConDescuento(Promocion promocion) {
        if (promocion == null || this.funcion == null) {
            return this.total;
        }

        Funcion promocionFuncion = promocion.getFuncion();
        String promocionDia = promocion.getDia();
        String funcionDia = this.funcion.getDia();

        if (promocionFuncion != null
                && promocionFuncion.equals(this.funcion)
                && promocionDia != null
                && funcionDia != null
                && promocionDia.equalsIgnoreCase(funcionDia)) {
            return promocion.aplicarDescuento(this.total);
        }
        return this.total;
    }

}
