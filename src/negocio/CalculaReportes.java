package co.edu.uptc.tienda.negocio;

import co.edu.uptc.tienda.modelo.Inventory;

import java.util.Map;

public class CalculaReportes {

    public double calcularPrecioBase(Map<String, Inventory> inventoryMap) {
        double basePrice = 0;
        for (String key : inventoryMap.keySet()) {
            basePrice += inventoryMap.get(key).getPrecioBase();
        }
        return basePrice;
    }

    public double calcularPrecioVenta(Map<String, Inventory> inventoryMap) {
        double salesPrice = 0, totalSalesPrice = 0;
        for (String key : inventoryMap.keySet()) {
            if (inventoryMap.get(key).getPrecioBase() > 600000) {
                salesPrice += inventoryMap.get(key).getPrecioBase() * 0.19;
            } else {
                salesPrice += inventoryMap.get(key).getPrecioBase() * 0.05;
            }
        }
        return salesPrice;
    }

    public double calcularIVAMayor(Map<String, Inventory> inventory) {
        double worth = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            if (inventor.getPrecioBase() > 600000) {
                worth += inventor.getPrecioBase() * 0.19;
            }
        }
        return worth;
    }

    public double calcularIVAMenor(Map<String, Inventory> inventory) {
        double worth = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            if (inventor.getPrecioBase() <= 600000) {
                worth += inventor.getPrecioBase() * 0.05;
            }
        }
        return worth;
    }

    public double calculateCommissions(Map<String, Inventory> inventory) {
        double commission = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            commission += inventor.getPrecioBase() * 0.05;
        }
        return commission;
    }

    public int calculateTotalCell(Map<String, Inventory> inventory) {
        int totalCellPhones = 0;
        for (String key : inventory.keySet()) {
            totalCellPhones += inventory.get(key).getCantidad();
        }
        return totalCellPhones;
    }

    public long calculateProfits(Map<String, Inventory> inventory) {
        return (long) (calcularPrecioBase(inventory) - (calcularIVAMayor(inventory) + calcularIVAMenor(inventory)));
    }
}
