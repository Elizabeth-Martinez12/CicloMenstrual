package com.example.ciclomenstrual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CicloMenstrualHelper {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static List<String> calcularDiasPeriodo(String fechaInicio, int duracionPeriodo) {
        List<String> diasPeriodo = new ArrayList<>();
        try {
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                Date inicio = sdf.parse(fechaInicio);
                Calendar cal = Calendar.getInstance();
                cal.setTime(inicio);

                for (int i = 0; i < duracionPeriodo; i++) {
                    diasPeriodo.add(sdf.format(cal.getTime()));
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            } else {
                throw new IllegalArgumentException("La fecha de inicio no puede ser nula o vacía.");
            }
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return diasPeriodo;
    }

    public static List<String> calcularDiasCiclo(String fechaInicio, int duracionCiclo) {
        List<String> diasCiclo = new ArrayList<>();
        try {
            Date inicio = sdf.parse(fechaInicio);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inicio);

            for (int i = 0; i < duracionCiclo; i++) {
                diasCiclo.add(sdf.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diasCiclo;
    }

    public static String calcularDiaOvulacion(String fechaInicio, int duracionCiclo) {
        try {
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                Date inicio = sdf.parse(fechaInicio);
                Calendar cal = Calendar.getInstance();
                cal.setTime(inicio);
                cal.add(Calendar.DAY_OF_MONTH, duracionCiclo - 14);
                return sdf.format(cal.getTime());
            } else {
                throw new IllegalArgumentException("La fecha de inicio no puede ser nula o vacía.");
            }
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> calcularDiasFertiles(String diaOvulacion) {
        List<String> diasFertiles = new ArrayList<>();
        try {
            if (diaOvulacion != null && !diaOvulacion.isEmpty()) {
                Date ovulacion = sdf.parse(diaOvulacion);
                Calendar cal = Calendar.getInstance();
                cal.setTime(ovulacion);

                for (int i = 3; i > 0; i--) {
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    diasFertiles.add(sdf.format(cal.getTime()));
                }

                diasFertiles.add(diaOvulacion);

                cal.setTime(ovulacion);
                for (int i = 0; i < 2; i++) {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    diasFertiles.add(sdf.format(cal.getTime()));
                }
            } else {
                throw new IllegalArgumentException("El día de ovulación no puede ser nulo o vacío.");
            }
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return diasFertiles;
    }
}
