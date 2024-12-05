package com.example.ciclomenstrual;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CalendarioService {
    @GET("ciclo/getCalendario")
    Call<List<Calendario>> obtenerCalendarios();

    @POST("ciclo/insertCalendario")
    Call<Void> insertCalendario(@Body Calendario calendario);
}

