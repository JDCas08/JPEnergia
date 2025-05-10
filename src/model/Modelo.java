package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

        
        public void cargarConsumosAutomaticos(int mes, int anio) {
            Random random = new Random();
            for (Registrador registrador : registradores) {
                for (int semana = 1; semana <= 4; semana++) {
                    int dia = (semana - 1) * 7 + 1;
                    if (dia > 28) dia = 28;
                    int hora = random.nextInt(24);
                    LocalDateTime fecha = LocalDateTime.of(anio, mes, dia, hora, 0);
                    double cantidadKWH = 100 + (899 * random.nextDouble());
                    Consumo consumo = new Consumo(fecha, cantidadKWH);
                    registrador.agregarConsumo(consumo);
                }
            }
        }

     
        public double obtenerConsumoMinimo(int mes, int anio) {
            double consumoMinimo = Double.MAX_VALUE;
            for (Registrador registrador : registradores) {
                for (Consumo consumo : registrador.getConsumos()) {
                    if (consumo.getFechaHora().getMonthValue() == mes && consumo.getFechaHora().getYear() == anio) {
                        if (consumo.getCantidadKWH() < consumoMinimo) {
                            consumoMinimo = consumo.getCantidadKWH();
                        }
                    }
                }
            }
            return consumoMinimo == Double.MAX_VALUE ? 0 : consumoMinimo;
        }

      
        public double obtenerConsumoMaximo(int mes, int anio) {
            double consumoMaximo = Double.MIN_VALUE;
            for (Registrador registrador : registradores) {
                for (Consumo consumo : registrador.getConsumos()) {
                    if (consumo.getFechaHora().getMonthValue() == mes && consumo.getFechaHora().getYear() == anio) {
                        if (consumo.getCantidadKWH() > consumoMaximo) {
                            consumoMaximo = consumo.getCantidadKWH();
                        }
                    }
                }
            }
            return consumoMaximo == Double.MIN_VALUE ? 0 : consumoMaximo;
        }

        public double[] obtenerConsumoPorFranjas(int mes, int anio) {
            double[] consumoPorFranjas = new double[3]; 
            for (Registrador registrador : registradores) {
                for (Consumo consumo : registrador.getConsumos()) {
                    if (consumo.getFechaHora().getMonthValue() == mes && consumo.getFechaHora().getYear() == anio) {
                        int franja = consumo.getFranja();
                        if (franja > 0 && franja <= 3) {
                            consumoPorFranjas[franja - 1] += consumo.getCantidadKWH();
                        }
                    }
                }
            }
            return consumoPorFranjas;
        }

       
        public double[] obtenerConsumoPorDias(int mes, int anio) {
            double[] consumoPorDias = new double[31]; 
            for (Registrador registrador : registradores) {
                for (Consumo consumo : registrador.getConsumos()) {
                    if (consumo.getFechaHora().getMonthValue() == mes && consumo.getFechaHora().getYear() == anio) {
                        int dia = consumo.getFechaHora().getDayOfMonth();
                        consumoPorDias[dia - 1] += consumo.getCantidadKWH();
                    }
                }
            }
            return consumoPorDias;
        }

  
        public double calcularValorFactura(int mes, int anio) {
            double totalFactura = 0;
            for (Registrador registrador : registradores) {
                for (Consumo consumo : registrador.getConsumos()) {
                    if (consumo.getFechaHora().getMonthValue() == mes && consumo.getFechaHora().getYear() == anio) {
                        totalFactura += consumo.calcularValor();
                    }
                }
            }
            return totalFactura;
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

   
        public boolean cambiarConsumo(LocalDateTime fechaObjetivo, double nuevaCantidadKWH) {
            for (Consumo consumo : consumos) {
                LocalDateTime fecha = consumo.getFechaHora();
                if (fecha.equals(fechaObjetivo)) {
                    consumo.setCantidadKWH(nuevaCantidadKWH);
                    return true; 
                }
            }
            return false; 
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
}
