package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Impuesto;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdministracionSistema {
    private Map<String, Vendedor> mapSellers;
    private Map<String, Inventario> mapInventory;
    private ArrayList<Venta> listSales;
    private CalculaReportes calcular;
    private CalculateSale sales;
    private Map<String, CalculateBestSelling> mapBestSellings;
    private Impuesto impuesto;
    private int cod;

    public AdministracionSistema() {
        mapSellers = new HashMap<>();
        mapInventory = new HashMap<>();
        listSales = new ArrayList<>();
        calcular = new CalculaReportes();
        sales = new CalculateSale();
        mapBestSellings = new HashMap<>();
        impuesto = new Impuesto();
        cod = 1;
    }
    //-------------------------------------------------------------------------------------
    public void loadSellers(String data) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Noo ha ingresado ningun vendedor");
            return;
        }
        if (data.contains(";")) {
            try {
                if (data.contains("\n")) {
                    String[] datos = data.split("\n");
                    for (String dato : datos) {
                        String[] data2 = dato.split(";");
                        String cod = codSellers();
                        mapSellers.put(cod, new Vendedor(data2[0], data2[1], Long.parseLong(data2[2]), Long.parseLong(data2[3]), data2[4], Long.parseLong(data2[5]), data2[6], cod));
                    }
                } else {
                    String[] datos = data.split(";");
                    String cod = codSellers();
                    mapSellers.put(cod, new Vendedor(datos[0], datos[1], Long.parseLong(datos[2]), Long.parseLong(datos[3]), datos[4], Long.parseLong(datos[5]), datos[6], cod));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error en los datos numericos");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en los datos");
            }
        }
    }

    public void loadInventory(String data) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Noo ha ingresado ningun inventario");
            return;
        }
        String[] datos;
        try {
            if (data.contains("\n")) {
                datos = data.split("\n");
                for (String dato : datos) {
                    //Itera sobre cada linea de los datos ingresados
                    String[] data2 = dato.split(";");
                    //Si mapInventory esta vacio, se crea un objeto Inventory con los datos de la linea
                    if (mapInventory.get(data2[2]) == null) {
                        mapInventory.put(data2[2], new Inventario(data2[0], data2[1], data2[2], Long.parseLong(data2[3]), Integer.parseInt(data2[4])));
                    } else {
                        //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                        Inventario cel = mapInventory.get(data2[2].toUpperCase());
                        if (cel != null) {
                            cel.setCantidad(cel.getCantidad() + Integer.parseInt(data2[4].trim()));
                        }
                    }
                }

            } else {
                datos = data.split(";");
                //Si mapInventory esta vacio, se crea un objeto Inventory con los datos de la linea
                if (!mapInventory.containsKey(datos[2])) {
                    mapInventory.put(datos[2], new Inventario(datos[0], datos[1], datos[2], Long.parseLong(datos[3].trim()), Integer.parseInt(datos[4].trim())));
                } else {
                    //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                    Inventario cel = mapInventory.get(datos[2].toUpperCase());
                    if (cel != null) {
                        cel.setCantidad(cel.getCantidad() + Integer.parseInt(datos[4].trim()));
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en los datos numericos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en los datos");
        }
    }

    public void loadSales(String data) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No ha ingresado ninguna venta");
            return;
        }
        if (!validateCreate()) {
            return;
        }
        String[] datos;
        try {
            if (data.contains("\n")) {
                datos = data.split("\n");
                for (String dato : datos) {
                    String[] data2 = dato.split(";");
                    if (data2.length != 3) {
                        continue;
                    }
                    if (mapInventory.get(data2[1].trim()) == null) {
                        JOptionPane.showMessageDialog(null, "No existe el objeto " + data2[1] + ", se continuara con la ejecución ignorando esta supuesta venta");
                        continue;
                    }
                    listSales.add(new Venta(data2[0].trim(), data2[1].trim(), Integer.parseInt(data2[2].trim())));
                    
                    if (mapBestSellings.get(mapInventory.get(data2[1].trim()).getCodigo()) == null) {
                        mapBestSellings.put(mapInventory.get(data2[1].trim()).getCodigo(), new CalculateBestSelling(mapInventory.get(data2[1].trim()).getMarca(), mapInventory.get(data2[1].trim()).getLinea(), Integer.parseInt(data2[2].trim())));
                    } else {
                        mapBestSellings.get(mapInventory.get(data2[1].trim()).getCodigo()).setSales(Integer.parseInt(data2[2].trim()));
                    }

                    if (mapInventory.get(data2[1].trim()).getPrecioBase() > 600000) {
                        impuesto.setTaxBaseHigher(mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim()));
                    } else {
                        impuesto.setTaxBaseMinor(mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim()));
                    }
                    mapInventory.get(data2[1].trim()).setCantidad(mapInventory.get(data2[1].trim()).getCantidad() - Integer.parseInt(data2[2].trim()));
                    mapSellers.get(data2[0].trim()).setComision((long) (mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim())* 0.05));
                    mapSellers.get(data2[0].trim()).setSalesCells(Integer.parseInt(data2[2].trim()));
                }
            } else {
                datos = data.split(";");
                listSales.add(new Venta(datos[0], datos[1], Integer.parseInt(datos[2])));
                if (datos.length != 3) {
                    JOptionPane.showMessageDialog(null, "No ha ingresado correctamente los datos de venta");
                    return;
                }
                if (mapInventory.get(datos[1]) == null) {
                    JOptionPane.showMessageDialog(null, "No existe el objeto " + datos[1]);
                    return;
                }
                listSales.add(new Venta(datos[0].trim(), datos[1].trim(), Integer.parseInt(datos[2].trim())));
                if (mapBestSellings.get(mapInventory.get(datos[1].trim()).getCodigo()) == null) {
                    mapBestSellings.put(mapInventory.get(datos[1].trim()).getCodigo(), new CalculateBestSelling(datos[0].trim(), datos[1].trim(), Integer.parseInt(datos[2].trim())));
                } else {
                    mapBestSellings.get(mapInventory.get(datos[1].trim()).getCodigo()).setSales(Integer.parseInt(datos[2].trim()));
                }
                
                if (mapInventory.get(datos[1].trim()).getPrecioBase() > 600000) {
                    impuesto.setTaxBaseHigher(mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim()));
                } else {
                    impuesto.setTaxBaseMinor(mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim()));
                }
                mapInventory.get(datos[1].trim()).setCantidad(mapInventory.get(datos[1].trim()).getCantidad() - Integer.parseInt(datos[2].trim()));
                mapSellers.get(datos[0]).setComision((long) (mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim())* 0.05));
                mapSellers.get(datos[0].trim()).setSalesCells(Integer.parseInt(datos[2].trim()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en los datos numericos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en los datos");
        }
    }

    public boolean validateCreate() {
        if (mapInventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ha registrado nada en el inventario");
            return false;
        } else if (mapSellers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vendedores registrados en el momento, ingrese vendedores primero");
            return false;
        } else {
            return true;
        }
    }

    public String codSellers() {//generar codigo de los vendedores con la estructura VENOO1, VENOO2, etc
        if (cod < 10) {
            return "VEN00" + (cod++);
        } else if (cod < 100) {
            return "VEN0" + (cod++);
        } else if (cod < 1000) {
            return "VEN" + (cod++);
        }
        return "";
    }

    public String reportInventoy() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        StringBuilder sb = new StringBuilder(String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n", "Total Celulares", "Total Precio Base", "Total Precio Ventas ", "Total impuestos", "Total Comisiones", "Total ganancias\n"));

        int totalCell = calcular.calculateTotalCell(mapInventory);
        long basePrice = calcular.calcularPrecioBase(mapInventory);
        long salesPrice = basePrice + calcular.calcularPrecioVenta(mapInventory);
        long taxes = calcular.calcularIVAMayor(mapInventory) + calcular.calcularIVAMenor(mapInventory);
        long commissions = calcular.calculateCommissions(mapInventory);
        long profits = calcular.calculateProfits(mapInventory);
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
        sb.append(String.format("%-20d | %-20s | %-20s | %-20s | %-20s | %-20s", totalCell, format.format(basePrice), format.format(salesPrice), format.format(taxes), format.format(commissions), format.format(profits)));
        return sb.toString();
    }

    public String reportSales() {
        StringBuilder sb = new StringBuilder(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n", "Tipo documento y numero", "Nombre Vendedor", "Total Comisiones", "Numero cuenta Bancaria", "Tipo Cuenta Bancaria", "# Ventas"));

        String info = sales.buildInformationSales(mapSellers, listSales);
        String[] datesn;
        String[] report;
        if (info.contains("\n")) {
            datesn = info.split("\n");
            for (String date : datesn) {
                report = date.split(";");
                sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                sb.append(String.format("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
            }
        } else {
            report = info.split("\n");
            sb.append(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
        }
        return sb.toString();
    }

    public String reportBestSelling() {
        CalculateBestSelling bestSelling = new CalculateBestSelling();
        String[] info = bestSelling.calculateBestLine(mapBestSellings).split(";");
        String[] info2 = bestSelling.calculateBestBrand(mapBestSellings).split(";");
        StringBuilder sb = new StringBuilder(String.format("%-40s | %-10s\n", "Concepto", "Valor"));
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
        sb.append(String.format("%-40s | %-10s\n", "Marca de Celular más Vendida", info2[0]));
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
        sb.append(String.format("%-40s | %-10s\n", "Total de marca de celular mas vendida", info2[1]));
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
        sb.append(String.format("%-40s | %-10s\n", "Linea de Celular mas vendida", info[0]));
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
        sb.append(String.format("%-40s | %-10s\n", "Total ventas de linea de celular +vendida", info[1]));
        return sb.toString();
    }

    public String reportTaxes() {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        StringBuilder sb = new StringBuilder(String.format("%-20s | %-20s | %-15s \n", "Impuestos", "Total Bases Gravables", "Total Impuestos"));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 5%", format.format(impuesto.getTaxBaseMinor()), format.format(calcular.calculateTaxesMinor(impuesto))));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 19%", format.format(impuesto.getTaxBaseHigher()), format.format(calcular.calculateTaxesHigher(impuesto))));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Total", format.format(impuesto.getTaxBaseMinor() + impuesto.getTaxBaseHigher()), format.format(calcular.calculateTaxesMinor(impuesto) + calcular.calculateTaxesHigher(impuesto))));
        return sb.toString();
    }
}
