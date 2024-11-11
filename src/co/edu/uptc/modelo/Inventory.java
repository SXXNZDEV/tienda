package co.edu.uptc.modelo;

public class Inventory {
    private String marca;
    private String linea;
    private String codigo;
    private long precioBase;
    private int cantidad;

    public Inventory(String marca, String linea, String codigo, long precioBase, int cantidad) {
        this.marca = marca;
        this.linea = linea;
        this.codigo = codigo;
        this.precioBase = precioBase;
        this.cantidad = cantidad;
    }

    public Inventory() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public long getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(long precioBase) {
        this.precioBase = precioBase;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
