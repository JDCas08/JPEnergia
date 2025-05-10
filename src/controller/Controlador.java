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
                case 10 -> consultarConsumoMinimo();
                case 11 -> consultarConsumoMaximo();
                case 12 -> consultarConsumoPorFranjas();
                case 13 -> consultarConsumoPorDias();
                case 14 -> calcularValorFactura();
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
            String idReg = vista.leerTexto("Número de identificación del registrador: ");
            String dir = vista.leerTexto("Dirección del registrador: ");
            String ciu = vista.leerTexto("Ciudad: ");
            Modelo.Registrador reg = new Modelo.Registrador(idReg, dir, ciu);
            clienteOpt.get().agregarRegistrador(reg);
            vista.mostrarMensaje("Registrador agregado exitosamente.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cargarConsumosTodosClientes() {
        int mes = vista.obtenerMes();
        int anio = vista.obtenerAnio();
        for (Modelo.Cliente c : clientes) {
            c.cargarConsumosAutomaticos(mes, anio);
        }
        vista.mostrarMensaje("Consumos cargados para todos los clientes.");
    }

    private void cargarConsumosUnCliente() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            int mes = vista.obtenerMes();
            int anio = vista.obtenerAnio();
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
            String idReg = vista.leerTexto("Número de identificación del registrador: ");
            Optional<Modelo.Registrador> regOpt = buscarRegistrador(clienteOpt.get(), idReg);
            if (regOpt.isPresent()) {
                int anio = vista.obtenerAnio();
                int mes = vista.obtenerMes();
                int dia = vista.leerInt("Día del consumo: ");
                int hora = vista.leerInt("Hora (0-23): ");
                double nuevaCantidad = vista.leerDouble("Nueva cantidad de KWH: ");

                LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, 0);
                boolean exito = regOpt.get().cambiarConsumo(fecha, nuevaCantidad);
                vista.mostrarMensaje(exito
                    ? "Consumo actualizado correctamente."
                    : "No se encontró un consumo con esa fecha y hora.");
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
            Modelo.Cliente c = clienteOpt.get();
            c.setTipoIdentificacion(vista.leerTexto("Nuevo tipo de identificación: "));
            c.setCorreoElectronico(vista.leerTexto("Nuevo correo electrónico: "));
            c.setDireccionFisica(vista.leerTexto("Nueva dirección física: "));
            vista.mostrarMensaje("Datos del cliente actualizados.");
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void cambiarDatosRegistrador() {
        String idCliente = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> clienteOpt = buscarCliente(idCliente);
        if (clienteOpt.isPresent()) {
            String idReg = vista.leerTexto("Número de identificación del registrador: ");
            Optional<Modelo.Registrador> regOpt = buscarRegistrador(clienteOpt.get(), idReg);
            if (regOpt.isPresent()) {
                Modelo.Registrador r = regOpt.get();
                r.setDireccion(vista.leerTexto("Nueva dirección: "));
                r.setCiudad(vista.leerTexto("Nueva ciudad: "));
                vista.mostrarMensaje("Datos del registrador actualizados.");
            } else {
                vista.mostrarMensaje("Registrador no encontrado.");
            }
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void mostrarDatos() {
        for (Modelo.Cliente c : clientes) {
            vista.mostrarMensaje("Cliente: " + c.getNumeroUnicoIdentificacion()
                + ", Tipo: " + c.getTipoIdentificacion()
                + ", Correo: " + c.getCorreoElectronico()
                + ", Dirección: " + c.getDireccionFisica());
            for (Modelo.Registrador r : c.getRegistradores()) {
                vista.mostrarMensaje("\tRegistrador: " + r.getNumeroIdentificacion()
                    + ", Dirección: " + r.getDireccion()
                    + ", Ciudad: " + r.getCiudad());
                for (Modelo.Consumo con : r.getConsumos()) {
                    vista.mostrarMensaje("\t\tConsumo - Fecha: " + con.getFechaHora()
                        + ", KWh: " + con.getCantidadKWH()
                        + ", Franja: " + con.getFranja()
                        + ", Valor: " + con.calcularValor());
                }
            }
        }
    }

    private void consultarConsumoMinimo() {
        String id = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> opt = buscarCliente(id);
        if (opt.isPresent()) {
            double min = opt.get().obtenerConsumoMinimo(vista.obtenerMes(), vista.obtenerAnio());
            vista.mostrarConsumoMinimo(min);
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void consultarConsumoMaximo() {
        String id = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> opt = buscarCliente(id);
        if (opt.isPresent()) {
            double max = opt.get().obtenerConsumoMaximo(vista.obtenerMes(), vista.obtenerAnio());
            vista.mostrarConsumoMaximo(max);
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void consultarConsumoPorFranjas() {
        String id = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> opt = buscarCliente(id);
        if (opt.isPresent()) {
            double[] franjas = opt.get().obtenerConsumoPorFranjas(vista.obtenerMes(), vista.obtenerAnio());
            vista.mostrarConsumoPorFranjas(franjas);
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void consultarConsumoPorDias() {
        String id = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> opt = buscarCliente(id);
        if (opt.isPresent()) {
            double[] dias = opt.get().obtenerConsumoPorDias(vista.obtenerMes(), vista.obtenerAnio());
            vista.mostrarConsumoPorDias(dias);
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private void calcularValorFactura() {
        String id = vista.leerTexto("Número único de identificación del cliente: ");
        Optional<Modelo.Cliente> opt = buscarCliente(id);
        if (opt.isPresent()) {
            double total = opt.get().calcularValorFactura(vista.obtenerMes(), vista.obtenerAnio());
            vista.mostrarValorFactura(total);
        } else {
            vista.mostrarMensaje("Cliente no encontrado.");
        }
    }

    private Optional<Modelo.Cliente> buscarCliente(String id) {
        return clientes.stream()
            .filter(c -> c.getNumeroUnicoIdentificacion().equals(id))
            .findFirst();
    }

    private Optional<Modelo.Registrador> buscarRegistrador(Modelo.Cliente cliente, String idReg) {
        return cliente.getRegistradores().stream()
            .filter(r -> r.getNumeroIdentificacion().equals(idReg))
            .findFirst();
    }
}
