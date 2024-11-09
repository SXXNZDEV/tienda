package co.edu.uptc.tienda.negocio;

import co.edu.uptc.tienda.modelo.Sales;
import co.edu.uptc.tienda.modelo.Seller;

import java.util.ArrayList;
import java.util.Map;

public class CalculateSale {

    public String buildInformationSales (Map<String, Seller> mapSellers, ArrayList<Sales> listSales) {
        StringBuilder sb = new StringBuilder();
        for (Seller seller : mapSellers.values()) {
            double commisions = 0;
            sb.append(seller.getTipoID()).append(": ").append(seller.getNumeroID()).append(";").append(seller.getNombres()).append(seller.getApellidos()).append(";").append(seller.getComision()).append(seller.getNumeroCuentaBanc()).append(";").append(seller.getTipoCuentaBanc()).append(";");
            for (Sales sales : listSales) {
                if (sales.getCodSeller().equalsIgnoreCase(seller.getCodigo())) {
                    commisions++;
                }
            }
            sb.append(commisions).append("\n");
        }
        return sb.toString();
    }
}
