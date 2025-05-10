package view;

import java.util.Scanner;

public class Vista {
    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n======= Menú Principal =======");
        System.out.println("1. Crear cliente");
        System.out.println("2. Crear registrador");
        System.out.println("3. Agregar registrador a cliente");
        System.out.println("4. Cargar consumos automáticos (todos los clientes)");
        System.out.println("5. Cargar consumos automáticos (un cliente)");
        System.out.println("6. Cambiar consumo específico");
        System.out.println("7. Cambiar datos de cliente");
        System.out.println("8. Cambiar datos de registrador");
        System.out.println("9. Mostrar todos los datos");
        System.out.println("10. Consultar consumo mínimo de un mes");
        System.out.println("11. Consultar consumo máximo de un mes");
        System.out.println("12. Consultar consumo por franjas");
        System.out.println("13. Consultar consumo por días");
        System.out.println("14. Calcular valor de la factura de un mes");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int leerInt(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensaje);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();  
        return valor;
    }

    public double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrada inválida. " + mensaje);
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();  
        return valor;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    
    public int obtenerMes() {
        return leerInt("Ingrese el mes (1-12): ");
    }

    public int obtenerAnio() {
        return leerInt("Ingrese el año: ");
    }

    
    public void mostrarConsumoMinimo(double minimo) {
        System.out.println("El consumo mínimo en el mes seleccionado es: " + minimo + " KWh");
    }

    public void mostrarConsumoMaximo(double maximo) {
        System.out.println("El consumo máximo en el mes seleccionado es: " + maximo + " KWh");
    }

    public void mostrarConsumoPorFranjas(double[] consumoPorFranjas) {
        System.out.println("Consumo por franjas:");
        System.out.println("Franja 1 (0-6 hrs): " + consumoPorFranjas[0] + " KWh");
        System.out.println("Franja 2 (7-17 hrs): " + consumoPorFranjas[1] + " KWh");
        System.out.println("Franja 3 (18-23 hrs): " + consumoPorFranjas[2] + " KWh");
    }

    public void mostrarConsumoPorDias(double[] consumoPorDias) {
        System.out.println("Consumo por días del mes:");
        for (int i = 0; i < consumoPorDias.length; i++) {
            System.out.println("Día " + (i + 1) + ": " + consumoPorDias[i] + " KWh");
        }
    }

    public void mostrarValorFactura(double totalFactura) {
        System.out.println("El valor total de la factura es: $" + totalFactura);
    }
}
