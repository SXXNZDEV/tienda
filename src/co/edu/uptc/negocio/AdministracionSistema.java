package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Inventory;
import co.edu.uptc.modelo.Seller;
import co.edu.uptc.modelo.Sales;
import co.edu.uptc.modelo.Taxes;

import javax.swing.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdministracionSistema {
    private Map<String, Seller> mapSellers = new HashMap<>();
    private Map<String, Inventory> mapInventory = new HashMap<>();
    private ArrayList<Sales> listSales = new ArrayList<>();
    private CalculaReportes calcular = new CalculaReportes();
    private CalculateSale sales = new CalculateSale();
    private Map<String, CalculateBestSelling> bestSellings = new HashMap<>();
    private Taxes taxes = new Taxes();
    private int cod = 1;

    public Map<String, Seller> getMapSellers() {
        return mapSellers;
    }

    public void setMapSellers(Map<String, Seller> mapSellers) {
        this.mapSellers = mapSellers;
    }

    public Map<String, Inventory> getMapInventory() {
        return mapInventory;
    }

    public void setMapInventory(Map<String, Inventory> mapInventory) {
        this.mapInventory = mapInventory;
    }

    public CalculaReportes getCalcular() {
        return calcular;
    }

    public void setCalcular(CalculaReportes calcular) {
        this.calcular = calcular;
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
                        mapSellers.put(cod, new Seller(data2[0], data2[1], Long.parseLong(data2[2]), Long.parseLong(data2[3]), data2[4], Long.parseLong(data2[5]), data2[6], cod));
                    }
                } else {
                    String[] datos = data.split(";");
                    String cod = codSellers();
                    mapSellers.put(cod, new Seller(datos[0], datos[1], Long.parseLong(datos[2]), Long.parseLong(datos[3]), datos[4], Long.parseLong(datos[5]), datos[6], cod));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error error en LoadSellers");//TODO cambiar los mensajes de error de los try/catch
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
                        mapInventory.put(data2[2], new Inventory(data2[0], data2[1], data2[2], Long.parseLong(data2[3]), Integer.parseInt(data2[4])));
                    } else {
                        //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                        Inventory cel = mapInventory.get(data2[0].toUpperCase());
                        if (cel != null) {
                            cel.setCantidad(cel.getCantidad() + Integer.parseInt(data2[4]));
                        }
                    }
                }

            } else {
                datos = data.split(";");
                //Si mapInventory esta vacio, se crea un objeto Inventory con los datos de la linea
                if (!mapInventory.containsKey(datos[2])) {
                    mapInventory.put(datos[2], new Inventory(datos[0], datos[1], datos[2], Long.parseLong(datos[3].trim()), Integer.parseInt(datos[4].trim())));
                } else {
                    //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                    Inventory cel = mapInventory.get(datos[2].toUpperCase());
                    if (cel != null) {
                        cel.setCantidad(cel.getCantidad() + Integer.parseInt(datos[4].trim()));
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());//TODO cambiar los mensajes de error de los try/catch
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
                    listSales.add(new Sales(data2[0].trim(), data2[1].trim(), Integer.parseInt(data2[2].trim())));
                    if (mapInventory.get(data2[1].trim()).getPrecioBase() > 600000) {
                        taxes.setTaxBaseHigher(mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim()));
                    } else {
                        taxes.setTaxBaseMinor(mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim()));
                    }
                    mapInventory.get(data2[1].trim()).setCantidad(mapInventory.get(data2[1].trim()).getCantidad() - Integer.parseInt(data2[2].trim()));
                    mapSellers.get(data2[0].trim()).setComision((long) (mapInventory.get(data2[1].trim()).getPrecioBase() * Long.parseLong(data2[2].trim())* 0.05));
                    mapSellers.get(data2[0].trim()).setSalesCells(Integer.parseInt(data2[2].trim()));
                }
            } else {
                datos = data.split(";");
                listSales.add(new Sales(datos[0], datos[1], Integer.parseInt(datos[2])));
                if (datos.length != 3) {
                    JOptionPane.showMessageDialog(null, "No ha ingresado correctamente los datos de venta");
                    return;
                }
                if (mapInventory.get(datos[1]) == null) {
                    JOptionPane.showMessageDialog(null, "No existe el objeto " + datos[1]);
                    return;
                }
                listSales.add(new Sales(datos[0].trim(), datos[1].trim(), Integer.parseInt(datos[2].trim())));
                if (mapInventory.get(datos[1].trim()).getPrecioBase() > 600000) {
                    taxes.setTaxBaseHigher(mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim()));
                } else {
                    taxes.setTaxBaseMinor(mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim()));
                }
                mapInventory.get(datos[1].trim()).setCantidad(mapInventory.get(datos[1].trim()).getCantidad() - Integer.parseInt(datos[2].trim()));
                mapSellers.get(datos[0]).setComision((long) (mapInventory.get(datos[1].trim()).getPrecioBase() * Long.parseLong(datos[2].trim())* 0.05));
                mapSellers.get(datos[0].trim()).setSalesCells(Integer.parseInt(datos[2].trim()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en LoadSales");//TODO cambiar los mensajes de error de los try/catch
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
                sb.append(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
            }
        } else {
            report = info.split("\n");
            sb.append(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
        }
        return sb.toString();
    }

    public String reportBestSelling() {
        CalculateBestSelling bestSelling = new CalculateBestSelling();
        String[] info = bestSelling.calculateBestLine(bestSellings).split(";");
        String[] info2 = bestSelling.calculateBestBrand(bestSellings).split(";");
        StringBuilder sb = new StringBuilder(String.format("%-40s | %-10s\n", "Concepto", "Valor"));
        sb.append(String.format("%-40s | %-10s\n", "Marca de Celular más Vendida", info[0]));
        sb.append(String.format("%-40s | %-10s\n", "Total de ventas del Marca de Celular más\n" + "Vendida\n", info[1]));
        sb.append(String.format("%-40s | %-10s\n", "Linea de Celular mas vendida", info2[0]));
        sb.append(String.format("%-40s | %-10s\n", "Total de ventas de la linea de Celular\n" + "con mayor ventas", info2[1]));
        return sb.toString();
    }

    public String reportTaxes() {
        StringBuilder sb = new StringBuilder(String.format("%-20s | %-20s | %-15s \n", "Impuestos", "Total Bases Gravables", "Total impuestos"));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 5%", taxes.getTaxBaseMinor(), calcular.calculateTaxesMinor(taxes)));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 19%", taxes.getTaxBaseHigher(), calcular.calculateTaxesHigher(taxes)));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Total", taxes.getTaxBaseMinor() + taxes.getTaxBaseHigher(), calcular.calculateTaxesMinor(taxes) + calcular.calculateTaxesHigher(taxes)));
        return sb.toString();
    }


    /*public int contarFilas(String ruta) {
        int i = 0;
        try (BufferedReader leer = new BufferedReader(new FileReader(ruta))) {
            while ((leer.readLine()) != null) {
                i++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se puede leer el archivo");
        }
        return i;
    }

    public String[][] cargarInfoInventario() {

        String[][] datos = new String[13][5];
        try (BufferedReader leer = new BufferedReader(new FileReader("C:\\Java\\SegundoSemestre\\src\\co\\edu\\uptc\\tienda\\modelo\\Inventario.csv"))) {
            int i = 0;
            String linea;
            while ((linea = leer.readLine()) != null) {
                datos[i] = linea.split(";");
                i++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo");
        }
        return datos;
    }

    public String[][] cargarStock() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String[][] datos = new String[3][7];
        int i = 0;
        try {
            File file = new File("C:\\Java\\SegundoSemestre\\src\\co\\edu\\uptc\\tienda\\modelo\\Stock.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter escribir = new BufferedWriter(new FileWriter("C:\\Java\\SegundoSemestre\\src\\co\\edu\\uptc\\tienda\\modelo\\Stock.csv"));
            escribir.write("Celulares ; Total Precio Base ; Total Precio de Venta ; Total de Impuestos a pagar ; Total Comisiones ; Total Ganancias; |");
            escribir.newLine();
            //escribir.write(calcular.calcularTotalCelulares() + " ; " + nf.format(calcular.calcularPrecioBase()) + " ; " + nf.format(calcular.calcularPrecioVenta()) + " ; " + nf.format((calcular.calcularIVAMayor() + calcular.calcularIVAMenor())) + " ; " + nf.format(calcular.calcularComisiones()) + " ; " + nf.format((calcular.calcularPrecioBase() - calcular.calcularComisiones())));

            escribir.close();
            BufferedReader leer = new BufferedReader(new FileReader("C:\\Java\\SegundoSemestre\\src\\co\\edu\\uptc\\tienda\\modelo\\Stock.csv"));
            String linea;
            while ((linea = leer.readLine()) != null) {
                datos[i] = linea.split(";");
                i++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return datos;
    }

    public String generarStock() {
        String[][] datos = cargarStock();
        StringBuilder sb = new StringBuilder();
        for (String[] dato : datos) {

            sb.append(String.format("%-9s | %-13s | %-7s | %-10s | %-8s\n", dato[0], dato[1], dato[2], dato[3], dato[4]));
            sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            sb.append("\n");
        }
        return sb.toString();
    }

    public void calcularStock() {

    }*/
}
