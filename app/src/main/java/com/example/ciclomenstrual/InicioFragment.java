package com.example.ciclomenstrual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InicioFragment extends Fragment {

    private RecyclerView recyclerViewCalendario;
    private CalendarioAdapter calendarioAdapter;
    private List<Calendario> calendarioList = new ArrayList<>();
    private Button btnRegistrarPeriodo;
    private TextView textProximaFecha, textDiasFaltantes, textCycleInfo;
    private RequestQueue requestQueue;

    private String idUsuario;
    private static final String TAG = "InicioFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Inicialización de vistas
        recyclerViewCalendario = view.findViewById(R.id.recyclerViewCalendario);
        btnRegistrarPeriodo = view.findViewById(R.id.button_register_period);
        textProximaFecha = view.findViewById(R.id.text_countdown_number);
        textDiasFaltantes = view.findViewById(R.id.text_countdown_label);
        textCycleInfo = view.findViewById(R.id.text_cycle_info);

        requestQueue = Volley.newRequestQueue(requireContext());

        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
        idUsuario = prefs.getString("idUsuario", ""); // Obtén el ID del usuario desde SharedPreferences

        if (idUsuario.isEmpty()) {
            Toast.makeText(requireContext(), "ID de usuario no encontrado. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            return view;
        }

        configurarRecyclerView();
        fetchHistorialCiclo();

        btnRegistrarPeriodo.setOnClickListener(v -> registrarPeriodo());

        return view;
    }

    private void configurarRecyclerView() {
        recyclerViewCalendario.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        calendarioAdapter = new CalendarioAdapter(getContext(), calendarioList);
        recyclerViewCalendario.setAdapter(calendarioAdapter);
    }

    private void fetchHistorialCiclo() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getCalendario?idUsuario=" + idUsuario;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray datos = response.getJSONArray("datos");
                        calendarioList.clear();
                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject obj = datos.getJSONObject(i);
                            String fechaInicio = obj.getString("fechaInicio");
                            String fechaFin = obj.getString("fechaFin");

                            Calendario calendario = new Calendario(
                                    obj.getString("idCalendario"),
                                    idUsuario,
                                    fechaInicio,
                                    fechaFin,
                                    obj.getInt("duracionCiclo"),
                                    obj.getInt("duracionPeriodo")
                            );
                            calendarioList.add(calendario);
                        }
                        calendarioAdapter.notifyDataSetChanged();
                        actualizarPrediccion(); // Basado en el último ciclo
                    } catch (JSONException e) {
                        Log.e(TAG, "Error al procesar el historial del ciclo", e);
                    }
                },
                error -> Log.e(TAG, "Error en la solicitud: " + error.toString())
        );
        requestQueue.add(request);
    }

    private void registrarPeriodo() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/insertCiclo";

        String fechaInicio = obtenerFechaActual();
        String fechaFin = calcularProximaFecha(fechaInicio, 5);

        JSONObject nuevoCiclo = new JSONObject();
        try {
            nuevoCiclo.put("idUsuario", idUsuario);
            nuevoCiclo.put("fechaInicio", fechaInicio);
            nuevoCiclo.put("fechaFin", fechaFin);
            nuevoCiclo.put("duracionCiclo", 28); // Ejemplo de duración promedio
            nuevoCiclo.put("duracionPeriodo", 5);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, nuevoCiclo,
                    response -> {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(requireContext(), "Ciclo registrado exitosamente", Toast.LENGTH_SHORT).show();
                                fetchHistorialCiclo(); // Refresca el historial tras el registro
                            } else {
                                Toast.makeText(requireContext(), "Error al registrar el ciclo", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error procesando respuesta", e);
                        }
                    },
                    error -> Log.e(TAG, "Error en la solicitud: " + error.toString())
            );
            requestQueue.add(request);

        } catch (JSONException e) {
            Log.e(TAG, "Error creando el JSON del ciclo", e);
        }
    }

    private void actualizarPrediccion() {
        if (calendarioList.isEmpty()) return;

        Calendario ultimoCiclo = calendarioList.get(calendarioList.size() - 1);
        long diasRestantes = calcularDiasRestantes(ultimoCiclo.getFechaFin());

        textProximaFecha.setText(String.valueOf(diasRestantes));
        if (diasRestantes <= 5) {
            textDiasFaltantes.setText("Días del periodo restantes");
            textCycleInfo.setText("Alta probabilidad de embarazo");
        } else {
            textDiasFaltantes.setText("Días restantes para el próximo ciclo");
            textCycleInfo.setText("Baja probabilidad de embarazo");
        }
    }

    private long calcularDiasRestantes(String fechaFin) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date dateFin = sdf.parse(fechaFin);
            long diferencia = dateFin.getTime() - new Date().getTime();
            return diferencia / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            Log.e(TAG, "Error calculando días restantes", e);
            return 0;
        }
    }

    private String calcularProximaFecha(String fechaInicio, int duracionPeriodo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(sdf.parse(fechaInicio));
            calendario.add(Calendar.DAY_OF_MONTH, duracionPeriodo);
            return sdf.format(calendario.getTime());
        } catch (Exception e) {
            return fechaInicio;
        }
    }

    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
