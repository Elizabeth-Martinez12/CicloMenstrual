package com.example.ciclomenstrual;

public class Alimentacion {
    private String imagenUrl;
    private String nombreAlimento;
    private String beneficios;
    private String faseRecomendada;

    // Constructor
    public Alimentacion(String imagenUrl, String nombreAlimento, String beneficios, String faseRecomendada) {
        this.imagenUrl = imagenUrl;
        this.nombreAlimento = nombreAlimento;
        this.beneficios = beneficios;
        this.faseRecomendada = faseRecomendada;
    }

    // Getters y setters
    public String getImagenUrl() {
        return imagenUrl;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public String getFaseRecomendada() {
        return faseRecomendada;
    }
}
