package co.edu.uptc.negocio;

import javax.swing.*;
import java.util.*;

public class CalculateBestSelling {
    private String brand;
    private String line;
    private int sales;



    public CalculateBestSelling() {}

    public CalculateBestSelling(String brand, String line, int bestSellingBrand) {
        this.brand = brand;
        this.line = line;
        this.sales = bestSellingBrand;

    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales += sales;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String calculateBestLine(Map<String, CalculateBestSelling> sellingMap) {
        sales = 0;
        String line = "";
        for (CalculateBestSelling sale : sellingMap.values()) {
            if (sale.sales > sales) {
                sales = sale.sales;
                line = sale.getLine() + ";" + sale.getSales();
            }
        }
        return line;
    }

    public String calculateBestBrand(Map<String, CalculateBestSelling> sellingMap) {
        StringBuilder info = new StringBuilder();

        Map<String, Integer> ventasPorMarca = new HashMap<>();
        for (CalculateBestSelling sale : sellingMap.values()) {
            String marca = sale.getBrand(); // Obtiene el nombre de la marca actual
            int ventasActuales = ventasPorMarca.getOrDefault(marca, 0); // Obtiene las ventas acumuladas para esta marca (o 0 si no existe)
            ventasPorMarca.put(marca, ventasActuales + sale.getSales()); // Actualiza el total de ventas para esta marca
        }

        int max = Collections.max(ventasPorMarca.values());

        for (Map.Entry<String, Integer> entry : ventasPorMarca.entrySet()) {
            if (entry.getValue() == max) {
                info.append(entry.getKey()).append(";").append(entry.getValue()).append("\n");
            }
        }

        return info.toString();
    }

}
