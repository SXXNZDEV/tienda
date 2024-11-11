package co.edu.uptc.negocio;

import java.util.ArrayList;
import java.util.Map;

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
        StringBuilder info = new StringBuilder();
        for (CalculateBestSelling sale : sellingMap.values()) {
            if (sale.sales > sales) {
                sales = sale.sales;//TODO revisar que quede bien
                line = sale.getLine() + ";" + sale.getSales();
            }
        }
        return line;
    }

    public String calculateBestBrand(Map<String, CalculateBestSelling> sellingMap) {
        int samsung = 0, huawei = 0, motorola = 0;
        StringBuilder info = new StringBuilder();
        //Quiero hacer el codigo para que se encuentre la marca que tenga mas ventas
        for (CalculateBestSelling sale : sellingMap.values()) {
            if (sale.getBrand().equalsIgnoreCase("Samsung")) {
                samsung++;
            } else if (sale.getBrand().equalsIgnoreCase("Huawei")) {
                huawei++;
            } else if (sale.getBrand().equalsIgnoreCase("Motorola")) {
                motorola++;
            }
        }

        if (samsung > huawei && samsung > motorola) {
            info.append("Samsung").append(";").append(samsung).append("\n");
        } else if (huawei > samsung && huawei > motorola) {
            info.append("Huawei").append(";").append(huawei).append("\n");
        } else if (motorola > samsung && motorola > huawei) {
            info.append("Motorola").append(";").append(motorola).append("\n");
        }
        return info.toString();
    }

}
