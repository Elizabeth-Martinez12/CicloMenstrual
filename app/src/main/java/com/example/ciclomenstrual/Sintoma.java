package com.example.ciclomenstrual;

public class Sintoma {
    private String nombreSintoma;
    private String descripcion;

    public Sintoma(String nombreSintoma, String descripcion) {
        this.nombreSintoma = nombreSintoma;
        this.descripcion = descripcion;
    }

    public String getNombreSintoma() {
        return nombreSintoma;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

