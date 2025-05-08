package view;

import java.util.Scanner;

public class Vista {

    private Scanner scanner;

    public Vista() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Agregar registrador a cliente");
        System.out.println("3. Agregar consumo a registrador");
        System.out.println("4. Ver todos los datos");
        System.out.println("5. Editar cliente");
        System.out.println("6. Editar registrador");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextDouble();
    }

    public int leerInt(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrarScanner() {
        scanner.close();
    }
}
