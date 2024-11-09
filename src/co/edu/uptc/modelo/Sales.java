package co.edu.uptc.modelo;

public class Sales {
    private String codSeller;
    private String codCell;
    private int amount;

    public Sales(String codSeller, String codCell, int amount) {
        this.codSeller = codSeller;
        this.codCell = codCell;
        this.amount = amount;
    }

    public Sales() {}

    public String getCodSeller() {
        return codSeller;
    }

    public void setCodSeller(String codSeller) {
        this.codSeller = codSeller;
    }

    public String getCodCell() {
        return codCell;
    }

    public void setCodCell(String codCell) {
        this.codCell = codCell;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
