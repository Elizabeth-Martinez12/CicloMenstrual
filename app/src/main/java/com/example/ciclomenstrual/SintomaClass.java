package com.example.ciclomenstrual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SintomaClass extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tableLayout = findViewById(R.id.tableLayoutSintomas);
        requestQueue = Volley.newRequestQueue(this);


        fetchSintomasData();
    }

    private void fetchSintomasData() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUsuario = prefs.getString("idUsuario", "");

        if (idUsuario.isEmpty()) {
            Toast.makeText(this, "ID de usuario no encontrado. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getSintoma";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray datos = response.getJSONArray("datos");

                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject obj = datos.getJSONObject(i);
                            String nombreSintoma = obj.getString("nombreSintoma");
                            String descripcion = obj.getString("descripcion");

                            addSintomaRow(nombreSintoma, descripcion);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("SintomaClass", "Error procesando la respuesta de la API: " + e.getMessage());
                        Toast.makeText(this, "Error al procesar los datos del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("SintomaClass", "Error en la API: " + error.toString());
                    Toast.makeText(this, "Error al cargar los datos del servidor", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }

    private void addSintomaRow(String nombre, String descripcion) {
        TableRow tableRow = new TableRow(this);

        TextView nombreView = new TextView(this);
        nombreView.setText(nombre);
        nombreView.setTextColor(getResources().getColor(android.R.color.black));
        nombreView.setTextSize(14);
        nombreView.setPadding(8, 8, 8, 8);
        nombreView.setGravity(Gravity.START);
        nombreView.setMaxLines(1);

        TextView descripcionView = new TextView(this);
        descripcionView.setText(descripcion);
        descripcionView.setTextColor(getResources().getColor(android.R.color.black));
        descripcionView.setTextSize(14);
        descripcionView.setPadding(8, 8, 8, 8);
        descripcionView.setGravity(Gravity.START);
        descripcionView.setMaxLines(Integer.MAX_VALUE);
        descripcionView.setEllipsize(null);
        descripcionView.setLineSpacing(1.5f, 1f);

        tableRow.addView(nombreView);
        tableRow.addView(descripcionView);

        if (tableLayout.getChildCount() % 2 == 1) {
            tableRow.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));  // Fila gris claro
        } else {
            tableRow.setBackgroundColor(getResources().getColor(android.R.color.white));  // Fila blanca
        }

        tableLayout.addView(tableRow);
    }


}
