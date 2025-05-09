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
}
