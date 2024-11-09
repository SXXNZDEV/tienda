package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Inventory;
import co.edu.uptc.modelo.Seller;
import co.edu.uptc.modelo.Sales;

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
    private ArrayList<CalculateBestSelling> bestSellings = new ArrayList<>();
    private int cod = 0;

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
                        mapSellers.put(codSellers(), new Seller(codSellers(), data2[2], Long.parseLong(data2[3]), Long.parseLong(data2[4]), data2[5], Long.parseLong(data2[6]), data2[7], Integer.parseInt(data2[8]), codSellers()));
                    }
                } else {
                    String[] datos = data.split(";");
                    mapSellers.put(codSellers(), new Seller(codSellers(), datos[2], Long.parseLong(datos[3]), Long.parseLong(datos[4]), datos[5], Long.parseLong(datos[6]), datos[7], Integer.parseInt(datos[8]), codSellers()));
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

        try {
            if (data.contains("\n")) {
                String[] datos = data.split("\n");
                for (String dato : datos) {
                    //Itera sobre cada linea de los datos ingresados
                    String[] data2 = dato.split(";");
                    //Si mapInventory esta vacio, se crea un objeto Inventory con los datos de la linea
                    if (mapInventory.get(data2[0]) == null) {
                        mapInventory.put(data2[2], new Inventory(data2[0], data2[1], data2[2], Double.parseDouble(data2[3]), Integer.parseInt(data2[4])));
                    } else {
                        //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                        Inventory cel = mapInventory.get(data2[0].toUpperCase());
                        if (cel != null) {
                            cel.setCantidad(cel.getCantidad() + Integer.parseInt(data2[4]));
                        }
                    }
                }
            } else {
                String[] datos = data.split(";");
                //Si mapInventory esta vacio, se crea un objeto Inventory con los datos de la linea
                if (mapInventory.containsKey(datos[2])) {
                    mapInventory.put(datos[2], new Inventory(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), Integer.parseInt(datos[4])));
                } else {
                    //Si el objeto existe, la referencia es obtenida y se actualizan los datos
                    Inventory cel = mapInventory.get(datos[2].toUpperCase());
                    if (cel != null) {
                        cel.setCantidad(cel.getCantidad() + Integer.parseInt(datos[4]));
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error error en LoadInventory");//TODO cambiar los mensajes de error de los try/catch
        }
    }

    public void loadSales(String data) {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No ha ingresado ninguna venta");
            return;
        }
        String[] datos;
        try {
            if (data.contains("\n")) {
                datos = data.split("\n");
                for (String dato : datos) {
                    String[] data2 = dato.split(";");
                    listSales.add(new Sales(data2[0], data2[1], Integer.parseInt(data2[2])));
                }
            } else {
                datos = data.split(";");
                listSales.add(new Sales(datos[0], datos[1], Integer.parseInt(datos[2])));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en LoadSales");//TODO cambiar los mensajes de error de los try/catch
        }

    }

    public String codSellers() {//generar codigo de los vendedores con la estructura VENOO1, VENOO2, etc
        if (cod < 10) {
            return "VENOO" + (cod++);
        } else if (cod < 100) {
            return "VENO" + (cod++);
        } else if (cod < 1000) {
            return "VEN" + (cod++);
        }
        return "";
    }

    public String reportInventoy() {
        StringBuilder sb = new StringBuilder(String.format("%-15s | %-15s | %-15s | %-15s | %-15s | %-15s ", "Tota Celulares", "Total Precio Base", "Total Precio Ventas ", "Total impuestos", "Total Comisiones", "Total ganancias\n"));
        int totalCell = calcular.calculateTotalCell(mapInventory);
        double basePrice = calcular.calcularPrecioBase(mapInventory);
        double salesPrice = calcular.calcularPrecioVenta(mapInventory);
        double taxes = calcular.calcularIVAMayor(mapInventory) + calcular.calcularIVAMenor(mapInventory);
        double commissions = calcular.calculateCommissions(mapInventory);
        long profits = calcular.calculateProfits(mapInventory);
        sb.append(String.format("%-15d | %-15s | %-15s | %-15s | %-15s | %-15s", totalCell, basePrice, salesPrice, taxes, commissions, profits));
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
                sb.append(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
            }
        } else {
            report = info.split("\n");
            sb.append(String.format("%-20s | %-15s | %-15s | %-15s | %-15s | %-15s\n ", report[0], report[1], report[2], report[3], report[4], report[5]));
        }
        return sb.toString();
    }

    public String reportBestSelling() {
        return null;
    }


    public int contarFilas(String ruta) {
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

    }
}
