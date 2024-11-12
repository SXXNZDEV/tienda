package co.edu.uptc.negocio;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Vendedor;

import java.util.ArrayList;
import java.util.Map;

public class CalculateSale {

    public String buildInformationSales (Map<String, Vendedor> mapSellers, ArrayList<Venta> listSales) {
        StringBuilder sb = new StringBuilder();
        for (Vendedor seller : mapSellers.values()) {
            sb.append(seller.getTipoID()).append(" -> ").append(seller.getNumeroID()).append(";").append(seller.getNombres()).append(seller.getApellidos()).append(";").append(seller.getComision()).append(";").append(seller.getNumeroCuentaBanc()).append(";").append(seller.getTipoCuentaBanc()).append(";").append(seller.getSalesCells()).append("\n");
        }
        return sb.toString();
    }
}
