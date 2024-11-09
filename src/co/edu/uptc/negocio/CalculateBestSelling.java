package co.edu.uptc.negocio;

import java.util.ArrayList;

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

    public String calculateBestBrand(ArrayList<CalculateBestSelling> bestSellings) {
        sales = 0;
        StringBuilder info = new StringBuilder();
        for (CalculateBestSelling sale : bestSellings) {
            if (sale.sales > sales) {

            }
        }
        return null;

    }
}
