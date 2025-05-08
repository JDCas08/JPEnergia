package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {




    public static class Cliente {
        private String numeroUnicoIdentificacion;
        private String tipoIdentificacion;
        private String correoElectronico;
        private String direccionFisica;
        private List<Registrador> registradores;

        public Cliente(String numeroUnicoIdentificacion, String tipoIdentificacion, String correoElectronico, String direccionFisica) {
            this.numeroUnicoIdentificacion = numeroUnicoIdentificacion;
            this.tipoIdentificacion = tipoIdentificacion;
            this.correoElectronico = correoElectronico;
            this.direccionFisica = direccionFisica;
            this.registradores = new ArrayList<>();
        }

        public String getNumeroUnicoIdentificacion() {
            return numeroUnicoIdentificacion;
        }

        public String getTipoIdentificacion() {
            return tipoIdentificacion;
        }

        public void setTipoIdentificacion(String tipoIdentificacion) {
            this.tipoIdentificacion = tipoIdentificacion;
        }

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public void setCorreoElectronico(String correoElectronico) {
            this.correoElectronico = correoElectronico;
        }

        public String getDireccionFisica() {
            return direccionFisica;
        }

        public void setDireccionFisica(String direccionFisica) {
            this.direccionFisica = direccionFisica;
        }

        public List<Registrador> getRegistradores() {
            return registradores;
        }

        public void agregarRegistrador(Registrador registrador) {
            this.registradores.add(registrador);
        }

        public void eliminarRegistrador(Registrador registrador) {
            this.registradores.remove(registrador);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cliente cliente = (Cliente) o;
            return numeroUnicoIdentificacion.equals(cliente.numeroUnicoIdentificacion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroUnicoIdentificacion);
        }
    }



    public static class Registrador {
        private String numeroIdentificacion;
        private String direccion;
        private String ciudad;
        private List<Consumo> consumos;

        public Registrador(String numeroIdentificacion, String direccion, String ciudad) {
            this.numeroIdentificacion = numeroIdentificacion;
            this.direccion = direccion;
            this.ciudad = ciudad;
            this.consumos = new ArrayList<>();
        }

        public String getNumeroIdentificacion() {
            return numeroIdentificacion;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public List<Consumo> getConsumos() {
            return consumos;
        }

        public void agregarConsumo(Consumo consumo) {
            this.consumos.add(consumo);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Registrador that = (Registrador) o;
            return numeroIdentificacion.equals(that.numeroIdentificacion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroIdentificacion);
        }
    }




    public static class Consumo {
        private LocalDateTime fechaHora;
        private double cantidadKWH;
        private int franja;

        public Consumo(LocalDateTime fechaHora, double cantidadKWH) {
            this.fechaHora = fechaHora;
            this.cantidadKWH = cantidadKWH;
            this.franja = determinarFranja(fechaHora.getHour());
        }

        public LocalDateTime getFechaHora() {
            return fechaHora;
        }

        public double getCantidadKWH() {
            return cantidadKWH;
        }

        public int getFranja() {
            return franja;
        }

        public void setCantidadKWH(double cantidadKWH) {
            this.cantidadKWH = cantidadKWH;
        }

        private int determinarFranja(int hora) {
            if (hora >= 0 && hora <= 6 && cantidadKWH >= 100 && cantidadKWH <= 300) {
                return 1;
            } else if (hora >= 7 && hora <= 17 && cantidadKWH > 300 && cantidadKWH <= 600) {
                return 2;
            } else if (hora >= 18 && hora <= 23 && cantidadKWH > 600 && cantidadKWH < 1000) {
                return 3;
            } else {
                return 0;
            }
        }

        public double calcularValor() {
            return switch (franja) {
                case 1 -> cantidadKWH * 200;
                case 2 -> cantidadKWH * 300;
                case 3 -> cantidadKWH * 500;
                default -> 0;
            };
        }
    }




    public static class VisualizadorDatos {

        public static void mostrarDatosClientes(List<Cliente> clientes) {
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
                return;
            }

            for (Cliente cliente : clientes) {
                System.out.println("====================================");
                System.out.println("Cliente:");
                System.out.println(" - ID único: " + cliente.getNumeroUnicoIdentificacion());
                System.out.println(" - Tipo de identificación: " + cliente.getTipoIdentificacion());
                System.out.println(" - Correo electrónico: " + cliente.getCorreoElectronico());
                System.out.println(" - Dirección física: " + cliente.getDireccionFisica());

                List<Registrador> registradores = cliente.getRegistradores();
                if (registradores.isEmpty()) {
                    System.out.println("   No hay registradores asociados.");
                } else {
                    for (Registrador registrador : registradores) {
                        System.out.println("   ------------------------------------");
                        System.out.println("   Registrador:");
                        System.out.println("    - Nº Identificación: " + registrador.getNumeroIdentificacion());
                        System.out.println("    - Dirección: " + registrador.getDireccion());
                        System.out.println("    - Ciudad: " + registrador.getCiudad());

                        List<Consumo> consumos = registrador.getConsumos();
                        if (consumos.isEmpty()) {
                            System.out.println("      No hay consumos registrados.");
                        } else {
                            for (Consumo consumo : consumos) {
                                System.out.println("      * Consumo:");
                                System.out.println("         - Fecha y hora: " + consumo.getFechaHora());
                                System.out.println("         - Cantidad KWH: " + consumo.getCantidadKWH());
                                System.out.println("         - Franja: " + consumo.getFranja());
                                System.out.println("         - Valor calculado: " + consumo.calcularValor());
                            }
                        }
                    }
                }
                System.out.println("====================================");
            }
        }
    }




    public static class EditorCliente {

        public static void cambiarTipoIdentificacion(Cliente cliente, String nuevoTipo) {
            cliente.setTipoIdentificacion(nuevoTipo);
        }

        public static void cambiarCorreoElectronico(Cliente cliente, String nuevoCorreo) {
            cliente.setCorreoElectronico(nuevoCorreo);
        }

        public static void cambiarDireccionFisica(Cliente cliente, String nuevaDireccion) {
            cliente.setDireccionFisica(nuevaDireccion);
        }
    }

 

    public static class EditorRegistrador {

        public static void cambiarDireccion(Registrador registrador, String nuevaDireccion) {
            registrador.setDireccion(nuevaDireccion);
        }

        public static void cambiarCiudad(Registrador registrador, String nuevaCiudad) {
            registrador.setCiudad(nuevaCiudad);
        }
    }
}
