package co.edu.uptc.negocio;
import co.edu.uptc.modelo.Sales;
import co.edu.uptc.modelo.Seller;

import java.util.ArrayList;
import java.util.Map;

public class CalculateSale {

    public String buildInformationSales (Map<String, Seller> mapSellers, ArrayList<Sales> listSales) {
        StringBuilder sb = new StringBuilder();
        for (Seller seller : mapSellers.values()) {
            double commisions = 0;
            sb.append(seller.getTipoID()).append(" -> ").append(seller.getNumeroID()).append(";").append(seller.getNombres()).append(seller.getApellidos()).append(";").append(seller.getComision()).append(seller.getNumeroCuentaBanc()).append(";").append(seller.getTipoCuentaBanc()).append(";").append(seller.getSalesCells()).append("\n");
        }
        return sb.toString();
    }
}
