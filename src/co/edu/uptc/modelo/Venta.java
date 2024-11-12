package co.edu.uptc.modelo;

public class Venta {
    private String codVendedor;
    private String codCell;
    private int cantidad;

    public Venta(String codSeller, String codCell, int amount) {
        this.codVendedor = codSeller;
        this.codCell = codCell;
        this.cantidad = amount;
    }

    public Venta() {}

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {
        this.codVendedor = codVendedor;
    }

    public String getCodCell() {
        return codCell;
    }

    public void setCodCell(String codCell) {
        this.codCell = codCell;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
