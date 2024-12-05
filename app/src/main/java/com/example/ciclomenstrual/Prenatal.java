package com.example.ciclomenstrual;

public class Prenatal {
    private String imagenUrl;
    private String titulo;
    private String categoria;
    private String descripcion;
    private String recomendaciones;
    private String beneficios;
    private String sintomas;

    public Prenatal(String imagenUrl, String titulo, String categoria, String descripcion, String recomendaciones, String beneficios, String sintomas) {
        this.imagenUrl = imagenUrl;
        this.titulo = titulo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.recomendaciones = recomendaciones;
        this.beneficios = beneficios;
        this.sintomas = sintomas;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
}
