package com.example.ciclomenstrual;

public class Actividades {
    private String nombreActividad;
    private String descripcion;
    private String faseRecomendada;

    public Actividades(String nombreActividad, String descripcion, String faseRecomendada) {
        this.nombreActividad = nombreActividad;
        this.descripcion = descripcion;
        this.faseRecomendada = faseRecomendada;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFaseRecomendada() {
        return faseRecomendada;
    }

    public void setFaseRecomendada(String faseRecomendada) {
        this.faseRecomendada = faseRecomendada;
    }
}
