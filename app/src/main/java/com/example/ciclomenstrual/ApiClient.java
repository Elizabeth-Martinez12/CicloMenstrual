package com.example.ciclomenstrual;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Response;

public class ApiClient {
    private static final String BASE_URL = "https://ciclomenstrual-431e75843ffd.herokuapp.com/";  // Usa la URL correcta
    private static Retrofit retrofit = null;
    private static CalendarioService apiService;

    // Método estático para obtener el cliente Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Método para obtener el servicio CalendarioService
    public static CalendarioService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(CalendarioService.class);
        }
        return apiService;
    }

    public static void insertarCalendario(Calendario nuevoCalendario, final CalendarioInsertCallback callback) {
        Call<Void> call = getApiService().insertCalendario(nuevoCalendario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("API", "Inserción exitosa");
                    callback.onSuccess();
                } else {
                    Log.e("API", "Error en la inserción: " + response.message());
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Fallo en la conexión: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public static void cargarDatosCalendario(final CalendarioListCallback callback) {
        Call<List<Calendario>> call = getApiService().obtenerCalendarios();
        call.enqueue(new Callback<List<Calendario>>() {
            @Override
            public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Calendario> calendarioList = response.body();
                    callback.onSuccess(calendarioList);
                } else {
                    Log.e("API", "Error al obtener datos: " + response.message());
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Calendario>> call, Throwable t) {
                Log.e("API", "Fallo en la conexión: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface CalendarioInsertCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface CalendarioListCallback {
        void onSuccess(List<Calendario> calendarioList);
        void onFailure(String errorMessage);
    }
}
