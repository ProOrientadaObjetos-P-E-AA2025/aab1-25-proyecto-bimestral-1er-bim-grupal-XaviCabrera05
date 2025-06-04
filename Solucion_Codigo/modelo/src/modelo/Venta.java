package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Venta {

    private String id;
    private List<Boleto> boletos;
    private List<Snack> snacks;
    private double total;

    public Venta() {
        this.id = UUID.randomUUID().toString();
        this.boletos = new ArrayList<>();
        this.snacks = new ArrayList<>();
        this.total = 0.0;
    }

    public String getId() {
        return id;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public List<Snack> getSnacks() {
        return snacks;
    }

    public double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void agregarBoleto(Boleto boleto) {
        this.boletos.add(boleto);
        calcularTotal();
    }

    public void agregarSnack(Snack snack) {
        this.snacks.add(snack);
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = 0.0;
        for (Boleto boleto : boletos) {
            this.total += boleto.getTotal();
        }
        for (Snack snack : snacks) {
            this.total += snack.getSubtotal();
        }
    }
}
