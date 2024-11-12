package co.edu.uptc.modelo;

public class Impuesto {
    private long taxBaseMinor;
    private long taxBaseHigher;
    private long taxMinor;
    private long taxHigher;

    //Clase creada para calcular directamente el total de impuestos a pagar ya sea del 19% o del 5%

    public Impuesto() {}

    public Impuesto(long taxBaseMinor, long taxBaseHigher, long taxMinor, long taxHigher) {
        this.taxBaseMinor = taxBaseMinor;
        this.taxBaseHigher = taxBaseHigher;
        this.taxMinor = taxMinor;
        this.taxHigher = taxHigher;
    }

    public long getTaxBaseMinor() {
        return taxBaseMinor;
    }

    public void setTaxBaseMinor(long taxBaseMinor) {
        this.taxBaseMinor += taxBaseMinor;
    }

    public long getTaxBaseHigher() {
        return taxBaseHigher;
    }

    public void setTaxBaseHigher(long taxBaseHigher) {
        this.taxBaseHigher += taxBaseHigher;
    }

    public long getTaxMinor() {
        return taxMinor;
    }

    public void setTaxMinor(long taxMinor) {
        this.taxMinor = taxMinor;
    }

    public long getTaxHigher() {
        return taxHigher;
    }

    public void setTaxHigher(long taxHigher) {
        this.taxHigher = taxHigher;
    }

}
