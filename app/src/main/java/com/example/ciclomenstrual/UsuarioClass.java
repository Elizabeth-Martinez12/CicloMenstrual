package com.example.ciclomenstrual;

import com.google.gson.annotations.SerializedName;

public class UsuarioClass {
    @SerializedName("idUsuario")
    private String idUsuario;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("edad")
    private String edad;

    @SerializedName("correoElectronico")
    private String correoElectronico;

    @SerializedName("nombreUsuario")
    private String nombreUsuario;

    @SerializedName("contrasenia")
    private String contrasenia;

    // Getters y setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
