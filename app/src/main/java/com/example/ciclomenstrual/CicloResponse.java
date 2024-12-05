package com.example.ciclomenstrual;

import java.util.List;

public class CicloResponse {
    private List<CicloData> datos;

    public List<CicloData> getDatos() {
        return datos;
    }

    public void setDatos(List<CicloData> datos) {
        this.datos = datos;
    }

    public static class CicloData {
        private String idCalendario;
        private String idUsuario;
        private String fechaInicio;
        private String fechaFin;
        private String duracionCiclo;
        private String duracionPeriodo;

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getDuracionCiclo() {
            return duracionCiclo;
        }

        public void setDuracionCiclo(String duracionCiclo) {
            this.duracionCiclo = duracionCiclo;
        }

        public String getDuracionPeriodo() {
            return duracionPeriodo;
        }

        public void setDuracionPeriodo(String duracionPeriodo) {
            this.duracionPeriodo = duracionPeriodo;
        }
    }
}
