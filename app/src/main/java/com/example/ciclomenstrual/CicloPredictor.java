package com.example.ciclomenstrual;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CicloPredictor {
    private static final int CICLO_PROMEDIO = 28;
    private static final int DURACION_PERIODO = 5;
    private static final int OVULACION = 14;

    public static List<String> calcularDiasPeriodo(String fechaInicio) {
        List<String> diasPeriodo = new ArrayList<>();
        LocalDate inicio = LocalDate.parse(fechaInicio);

        for (int i = 0; i < DURACION_PERIODO; i++) {
            diasPeriodo.add(inicio.plusDays(i).toString());
        }
        return diasPeriodo;
    }

    public static List<String> calcularDiasOvulacion(String fechaInicio) {
        List<String> diasOvulacion = new ArrayList<>();
        LocalDate inicio = LocalDate.parse(fechaInicio);

        diasOvulacion.add(inicio.plusDays(OVULACION).toString());
        return diasOvulacion;
    }

    public static List<String> calcularDiasFertiles(String fechaInicio) {
        List<String> diasFertiles = new ArrayList<>();
        LocalDate inicio = LocalDate.parse(fechaInicio);

        for (int i = OVULACION - 4; i <= OVULACION + 1; i++) {
            diasFertiles.add(inicio.plusDays(i).toString());
        }
        return diasFertiles;
    }
}

