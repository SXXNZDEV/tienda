package co.edu.uptc.modelo;

public class Seller {
    private String nombres;
    private String apellidos;
    private long telefono;
    private long numeroID;
    private String tipoID;
    private long numeroCuentaBanc;
    private String tipoCuentaBanc;
    private long comision;
    private String codigo;

    public Seller(String nombres, String apellidos, long telefono, long numeroID, String tipoID, long numeroCuentaBanc, String tipoCuentaBanc,int comision,String codigo) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.numeroID = numeroID;
        this.tipoID = tipoID;
        this.numeroCuentaBanc = numeroCuentaBanc;
        this.tipoCuentaBanc = tipoCuentaBanc;
        this.comision = comision;
        this.codigo = codigo;
    }

    public Seller() {
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getNumeroID() {
        return numeroID;
    }

    public void setNumeroID(long numeroID) {
        this.numeroID = numeroID;
    }

    public String getTipoID() {
        return tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    public long getNumeroCuentaBanc() {
        return numeroCuentaBanc;
    }

    public void setNumeroCuentaBanc(long numeroCuentaBanc) {
        this.numeroCuentaBanc = numeroCuentaBanc;
    }

    public String getTipoCuentaBanc() {
        return tipoCuentaBanc;
    }

    public void setTipoCuentaBanc(String tipoCuentaBanc) {
        this.tipoCuentaBanc = tipoCuentaBanc;
    }

    public long getComision() {
        return comision;
    }

    public void setComision(long comision) {
        this.comision = comision;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
