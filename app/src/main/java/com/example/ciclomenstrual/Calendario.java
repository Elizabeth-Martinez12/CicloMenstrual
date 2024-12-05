package com.example.ciclomenstrual;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calendario {
    private String idCalendario;
    private String idUsuario;
    private Date fechaInicio;
    private Date fechaFin;
    private int duracionCiclo;
    private int duracionPeriodo;
    private Date dias;
    private String nombreDia;
    private boolean esDiaEvento;
    private boolean esPeriodoEspecial;
    private boolean esDeshabilitado;

    public Calendario(String idCalendario, String fechaInicio, int duracionCiclo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.fechaInicio = formatter.parse(fechaInicio);
        } catch (ParseException e) {
            e.printStackTrace();
            this.fechaInicio = null;
        }
        this.idCalendario = idCalendario;
        this.duracionCiclo = duracionCiclo;
    }

    public Calendario(String idCalendario, String idUsuario, String fechaInicio, String fechaFin, int duracionCiclo, int duracionPeriodo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.fechaInicio = formatter.parse(fechaInicio);
            this.fechaFin = formatter.parse(fechaFin);
        } catch (ParseException e) {
            e.printStackTrace();
            this.fechaInicio = null;
            this.fechaFin = null;
        }
        this.idCalendario = idCalendario;
        this.idUsuario = idUsuario;
        this.duracionCiclo = duracionCiclo;
        this.duracionPeriodo = duracionPeriodo;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombreDia() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        return sdf.format(fechaInicio);
    }

    public String getNumeroDia() {
        // tienes que guardar en un atributo de esta clase el ultimo dia que se obtuvo
        // lurgo hscer que se retore pero el siguiente dia apartir del dia guardado en dicha variable
        // lo mismo para el texto del dia de la semana
        // sirve para autocompletarte el codigo, o igual pidele al chat lo que te digo pq la neta no se manejar fechas
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        //1 justo aqui antes de retornar el dia de hoy guardarlo
        // ejemplo-> this.ultimoDia = sdf.format(fechaInicio);


        return sdf.format(fechaInicio);
    }

    public String getDias(int diasAdicionales) {
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_MONTH, diasAdicionales);
        return sdf.format(calendar.getTime());
    }

    public static String calcularProximaFecha(String fechaUltimoPeriodo, int duracionCiclo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaUltimo = sdf.parse(fechaUltimoPeriodo);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaUltimo);
            calendar.add(Calendar.DAY_OF_YEAR, duracionCiclo);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public String getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(String idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDuracionCiclo() {
        return duracionCiclo;
    }

    public void setDuracionCiclo(int duracionCiclo) {
        this.duracionCiclo = duracionCiclo;
    }

    public int getDuracionPeriodo() {
        return duracionPeriodo;
    }

    public void setDuracionPeriodo(int duracionPeriodo) {
        this.duracionPeriodo = duracionPeriodo;
    }

    public Date getDias() {
        return dias;
    }

    public void setDias(Date dias) {
        this.dias = dias;
    }
    public boolean esDiaEvento() {
        return esDiaEvento;
    }

    public void setDiaEvento(boolean esDiaEvento) {
        this.esDiaEvento = esDiaEvento;
    }

    public boolean esPeriodoEspecial() {
        return esPeriodoEspecial;
    }

    public void setPeriodoEspecial(boolean esPeriodoEspecial) {
        this.esPeriodoEspecial = esPeriodoEspecial;
    }

    public boolean esDeshabilitado() {
        return esDeshabilitado;
    }

    public void setDeshabilitado(boolean esDeshabilitado) {
        this.esDeshabilitado = esDeshabilitado;
    }

}
