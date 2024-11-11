package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Inventory;
import co.edu.uptc.modelo.Taxes;

import java.util.Map;

public class CalculaReportes {

    public long calcularPrecioBase(Map<String, Inventory> inventoryMap) {
        long basePrice = 0;
        for (String key : inventoryMap.keySet()) {
            basePrice += inventoryMap.get(key).getPrecioBase() * inventoryMap.get(key).getCantidad();
        }
        return basePrice;
    }

    public long calcularPrecioVenta(Map<String, Inventory> inventoryMap) {
        long salesPrice = 0;
        for (String key : inventoryMap.keySet()) {
            if (inventoryMap.get(key).getPrecioBase() > 600000) {
                salesPrice += (long) (inventoryMap.get(key).getPrecioBase()* inventoryMap.get(key).getCantidad() * 0.19);
            } else {
                salesPrice += (long) (inventoryMap.get(key).getPrecioBase() * inventoryMap.get(key).getCantidad() * 0.05);
            }
        }
        return salesPrice;
    }

    public long calcularIVAMayor(Map<String, Inventory> inventory) {
        long worth = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            if (inventor.getPrecioBase() > 600000) {
                worth += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.19);
            }
        }
        return worth;
    }

    public long calcularIVAMenor(Map<String, Inventory> inventory) {
        long worth = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            if (inventor.getPrecioBase() <= 600000) {
                worth += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.05);
            }
        }
        return worth;
    }

    public long calculateCommissions(Map<String, Inventory> inventory) {
        long commission = 0;
        for (String key : inventory.keySet()) {
            Inventory inventor = inventory.get(key);
            commission += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.05);
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
        return (calcularPrecioBase(inventory) - calculateCommissions(inventory));
    }

    public double calculateTaxesMinor(Taxes taxes) {
        return taxes.getTaxBaseMinor() * 0.05;
    }

    public double calculateTaxesHigher(Taxes taxes) {
        return taxes.getTaxBaseMinor() * 0.19;
    }
}
