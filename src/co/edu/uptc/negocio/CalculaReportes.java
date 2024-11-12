package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Impuesto;

import java.util.Map;

public class CalculaReportes {

    public long calcularPrecioBase(Map<String, Inventario> inventoryMap) {
        long basePrice = 0;
        for (String key : inventoryMap.keySet()) {
            basePrice += inventoryMap.get(key).getPrecioBase() * inventoryMap.get(key).getCantidad();
        }
        return basePrice;
    }

    public long calcularPrecioVenta(Map<String, Inventario> inventoryMap) {
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

    public long calcularIVAMayor(Map<String, Inventario> inventory) {
        long worth = 0;
        for (Inventario inventor: inventory.values()) {
            if (inventor.getPrecioBase() > 600000) {
                worth += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.19);
            }
        }
        return worth;
    }

    public long calcularIVAMenor(Map<String, Inventario> inventory) {
        long worth = 0;
        for (Inventario inventor : inventory.values()) {
            if (inventor.getPrecioBase() <= 600000 && inventor.getCantidad() > 0) {
                worth += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.05);
            }
        }
        return worth;
    }

    public long calculateCommissions(Map<String, Inventario> inventory) {
        long commission = 0;
        for (Inventario inventor : inventory.values()) {
            commission += (long) (inventor.getPrecioBase() * inventor.getCantidad() * 0.05);
        }
        return commission;
    }

    public int calculateTotalCell(Map<String, Inventario> inventory) {
        int totalCellPhones = 0;
        for (Inventario inventor : inventory.values()) {
            totalCellPhones += inventor.getCantidad();
        }
        return totalCellPhones;
    }

    public long calculateProfits(Map<String, Inventario> inventory) {
        return (calcularPrecioBase(inventory) - calculateCommissions(inventory));
    }

    public double calculateTaxesMinor(Impuesto impuesto) {
        return impuesto.getTaxBaseMinor() * 0.05;
    }

    public double calculateTaxesHigher(Impuesto impuesto) {
        return impuesto.getTaxBaseMinor() * 0.19;
    }
}
