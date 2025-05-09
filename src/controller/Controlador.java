package controller;

import model.Modelo;
import view.Vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controlador {
    private Vista vista;
    private List<Modelo.Cliente> clientes;

    public Controlador(Vista vista) {
        this.vista = vista;
        this.clientes = new ArrayList<>();
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenu();
            int opcion = vista.leerInt("");

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> crearRegistrador();
                case 3 -> agregarRegistradorACliente();
                case 4 -> cargarConsumosTodosClientes();
                case 5 -> cargarConsumosUnCliente();
                case 6 -> cambiarConsumoEspecifico();
                case 7 -> cambiarDatosCliente();
                case 8 -> cambiarDatosRegistrador();
                case 9 -> mostrarDatos();
                case 0 -> {
                    vista.mostrarMensaje("¡Hasta luego!");
                    continuar = false;
                }
                default -> vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void crearCliente() {
        String id = vista.leerTexto("Número único de identificación: ");
        String tipoId = vista.leerTexto("Tipo de identificación: ");
        String correo = vista.leerTexto("Correo electrónico: ");
        String direccion = vista.leerTexto("Dirección física: ");
        Modelo.Cliente cliente = new Modelo.Cliente(id, tipoId, correo, direccion);
        clientes.add(cliente);
        vista.mostrarMensaje("Cliente creado exitosamente.");
    }

    private void crearRegistrador() {
        String id = vista.leerTexto("Número de identificación del registrador: ");
        String direccion = vista.leerTexto("Dirección del registrador: ");
        String ciudad = vista.leerTexto("Ciudad: ");
        Modelo.Registrador registrador = new Modelo.Registrador(id, direccion, ciudad);
        vista.mostrarMensaje("Registrador creado. Ahora agréguelo a un cliente desde el menú.");
    }

    private void agregarRegistradorACliente() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            String idRegistrador = vista.leerTexto("Número de identificación del registrador: ");
            String direccion = vista.leerTexto("Dirección del registrador: ");
            String ciudad = vista.leerTexto("Ciudad: ");
            Modelo.Registrador registrador = new Modelo.Registrador(idRegistrador, direccion, ciudad);
            clienteOpt.get().agregarRegistrador(registrador);
            vista.mostrarMensaje("Registrador agregado exitosamente.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cargarConsumosTodosClientes() {
        int mes = vista.leerInt("Ingrese el mes (1-12): ");
        int anio = vista.leerInt("Ingrese el año: ");
        for (Modelo.Cliente cliente : clientes) {
            cliente.cargarConsumosAutomaticos(mes, anio);
        }
        vista.mostrarMensaje("Consumos cargados para todos los clientes.");
    }

    private void cargarConsumosUnCliente() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            int mes = vista.leerInt("Ingrese el mes (1-12): ");
            int anio = vista.leerInt("Ingrese el año: ");
            clienteOpt.get().cargarConsumosAutomaticos(mes, anio);
            vista.mostrarMensaje("Consumos cargados para el cliente.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cambiarConsumoEspecifico() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            String idRegistrador = vista.leerTexto("Número de identificación del registrador: ");
            Optional<Modelo.Registrador> regOpt = buscarRegistrador(clienteOpt.get(), idRegistrador);
            if (regOpt.isPresent()) {
                int anio = vista.leerInt("Año del consumo: ");
                int mes = vista.leerInt("Mes del consumo: ");
                int dia = vista.leerInt("Día del consumo: ");
                int hora = vista.leerInt("Hora (0-23): ");
                double nuevaCantidad = vista.leerDouble("Nueva cantidad de KWH: ");

                LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, 0);
                boolean exito = regOpt.get().cambiarConsumo(fecha, nuevaCantidad);
                if (exito) {
                    vista.mostrarMensaje("Consumo actualizado correctamente.");
                } else {
                    vista.mostrarMensaje("No se encontró un consumo con esa fecha y hora.");
                }
            } else {
                vista.mostrarMensaje("Registrador no encontrado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cambiarDatosCliente() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            Modelo.Cliente cliente = clienteOpt.get();
            String nuevoTipo = vista.leerTexto("Nuevo tipo de identificación: ");
            String nuevoCorreo = vista.leerTexto("Nuevo correo electrónico: ");
            String nuevaDireccion = vista.leerTexto("Nueva dirección física: ");
            cliente.setTipoIdentificacion(nuevoTipo);
            cliente.setCorreoElectronico(nuevoCorreo);
            cliente.setDireccionFisica(nuevaDireccion);
            vista.mostrarMensaje("Datos del cliente actualizados.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cambiarDatosRegistrador() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            String idRegistrador = vista.leerTexto("Número de identificación del registrador: ");
            Optional<Modelo.Registrador> regOpt = buscarRegistrador(clienteOpt.get(), idRegistrador);
            if (regOpt.isPresent()) {
                Modelo.Registrador registrador = regOpt.get();
                String nuevaDireccion = vista.leerTexto("Nueva dirección: ");
                String nuevaCiudad = vista.leerTexto("Nueva ciudad: ");
                registrador.setDireccion(nuevaDireccion);
                registrador.setCiudad(nuevaCiudad);
                vista.mostrarMensaje("Datos del registrador actualizados.");
            } else {
                vista.mostrarMensaje("Registrador no encontrado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void mostrarDatos() {
        for (Modelo.Cliente cliente : clientes) {
            vista.mostrarMensaje("Cliente: " + cliente.getNumeroUnicoIdentificacion() +
                    ", Tipo: " + cliente.getTipoIdentificacion() +
                    ", Correo: " + cliente.getCorreoElectronico() +
                    ", Dirección: " + cliente.getDireccionFisica());

            for (Modelo.Registrador registrador : cliente.getRegistradores()) {
                vista.mostrarMensaje("\tRegistrador: " + registrador.getNumeroIdentificacion() +
                        ", Dirección: " + registrador.getDireccion() +
                        ", Ciudad: " + registrador.getCiudad());

                for (Modelo.Consumo consumo : registrador.getConsumos()) {
                    vista.mostrarMensaje("\t\tConsumo - Fecha: " + consumo.getFechaHora() +
                            ", KWH: " + consumo.getCantidadKWH() +
                            ", Franja: " + consumo.getFranja() +
                            ", Valor: " + consumo.calcularValor());
                }
            }
        }
    }

    // Métodos auxiliares
    private Optional<Modelo.Cliente> buscarCliente(String id) {
        return clientes.stream().filter(c -> c.getNumeroUnicoIdentificacion().equals(id)).findFirst();
    }

    private Optional<Modelo.Registrador> buscarRegistrador(Modelo.Cliente cliente, String idReg) {
        return cliente.getRegistradores().stream().filter(r -> r.getNumeroIdentificacion().equals(idReg)).findFirst();
    }
}
