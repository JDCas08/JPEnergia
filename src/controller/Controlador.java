package controller;

import model.Modelo;
import view.Vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controlador {

    private Vista vista;
    private List<Modelo.Cliente> listaClientes;

    public Controlador(Vista vista) {
        this.vista = vista;
        this.listaClientes = new ArrayList<>();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu();
            int opcion = vista.leerInt("");

            switch (opcion) {
                case 1 -> agregarCliente();
                case 2 -> agregarRegistrador();
                case 3 -> agregarConsumo();
                case 4 -> Modelo.VisualizadorDatos.mostrarDatosClientes(listaClientes);
                case 5 -> editarCliente();
                case 6 -> editarRegistrador();
                case 0 -> {
                    salir = true;
                    vista.mostrarMensaje("¡Gracias por usar la aplicación!");
                }
                default -> vista.mostrarMensaje("Opción inválida.");
            }
        }
    }

    private void agregarCliente() {
        vista.leerTexto("");  
        String id = vista.leerTexto("Ingrese número único de identificación: ");
        String tipo = vista.leerTexto("Ingrese tipo de identificación: ");
        String correo = vista.leerTexto("Ingrese correo electrónico: ");
        String direccion = vista.leerTexto("Ingrese dirección física: ");

        Modelo.Cliente cliente = new Modelo.Cliente(id, tipo, correo, direccion);
        listaClientes.add(cliente);
        vista.mostrarMensaje("Cliente agregado exitosamente.");
    }

    private void agregarRegistrador() {
        vista.leerTexto("");  
        String idCliente = vista.leerTexto("Ingrese ID del cliente al que desea agregar el registrador: ");
        Modelo.Cliente cliente = buscarCliente(idCliente);

        if (cliente != null) {
            String idReg = vista.leerTexto("Ingrese número de identificación del registrador: ");
            String direccion = vista.leerTexto("Ingrese dirección del registrador: ");
            String ciudad = vista.leerTexto("Ingrese ciudad del registrador: ");

            Modelo.Registrador registrador = new Modelo.Registrador(idReg, direccion, ciudad);
            cliente.agregarRegistrador(registrador);
            vista.mostrarMensaje("Registrador agregado exitosamente.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void agregarConsumo() {
        vista.leerTexto("");  
        String idCliente = vista.leerTexto("Ingrese ID del cliente: ");
        Modelo.Cliente cliente = buscarCliente(idCliente);

        if (cliente != null) {
            String idReg = vista.leerTexto("Ingrese ID del registrador: ");
            Modelo.Registrador registrador = buscarRegistrador(cliente, idReg);

            if (registrador != null) {
                double cantidad = vista.leerDouble("Ingrese cantidad de KWH: ");
                vista.leerTexto(""); 
                LocalDateTime ahora = LocalDateTime.now();
                Modelo.Consumo consumo = new Modelo.Consumo(ahora, cantidad);
                registrador.agregarConsumo(consumo);
                vista.mostrarMensaje("Consumo agregado exitosamente.");
            } else {
                vista.mostrarMensaje("Registrador no encontrado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void editarCliente() {
        vista.leerTexto("");  
        String id = vista.leerTexto("Ingrese ID del cliente a editar: ");
        Modelo.Cliente cliente = buscarCliente(id);

        if (cliente != null) {
            String nuevoTipo = vista.leerTexto("Nuevo tipo de identificación: ");
            String nuevoCorreo = vista.leerTexto("Nuevo correo electrónico: ");
            String nuevaDireccion = vista.leerTexto("Nueva dirección física: ");

            Modelo.EditorCliente.cambiarTipoIdentificacion(cliente, nuevoTipo);
            Modelo.EditorCliente.cambiarCorreoElectronico(cliente, nuevoCorreo);
            Modelo.EditorCliente.cambiarDireccionFisica(cliente, nuevaDireccion);
            vista.mostrarMensaje("Cliente actualizado exitosamente.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void editarRegistrador() {
        vista.leerTexto("");  
        String idCliente = vista.leerTexto("Ingrese ID del cliente: ");
        Modelo.Cliente cliente = buscarCliente(idCliente);

        if (cliente != null) {
            String idReg = vista.leerTexto("Ingrese ID del registrador: ");
            Modelo.Registrador registrador = buscarRegistrador(cliente, idReg);

            if (registrador != null) {
                String nuevaDireccion = vista.leerTexto("Nueva dirección del registrador: ");
                String nuevaCiudad = vista.leerTexto("Nueva ciudad del registrador: ");

                Modelo.EditorRegistrador.cambiarDireccion(registrador, nuevaDireccion);
                Modelo.EditorRegistrador.cambiarCiudad(registrador, nuevaCiudad);
                vista.mostrarMensaje("Registrador actualizado exitosamente.");
            } else {
                vista.mostrarMensaje("Registrador no encontrado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private Modelo.Cliente buscarCliente(String id) {
        for (Modelo.Cliente c : listaClientes) {
            if (c.getNumeroUnicoIdentificacion().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private Modelo.Registrador buscarRegistrador(Modelo.Cliente cliente, String idReg) {
        for (Modelo.Registrador r : cliente.getRegistradores()) {
            if (r.getNumeroIdentificacion().equals(idReg)) {
                return r;
            }
        }
        return null;
    }
}
