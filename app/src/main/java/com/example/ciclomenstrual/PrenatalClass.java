package com.example.ciclomenstrual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class PrenatalClass extends AppCompatActivity {
    private ListView listView;
    private PrenatalAdapter adapter;
    private List<Prenatal> prenatalList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenatal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        listView = findViewById(R.id.listViewPrenatal);
        prenatalList = new ArrayList<>();
        adapter = new PrenatalAdapter(this, prenatalList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchAlimentacionData();
    }

    private void fetchAlimentacionData() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getCuidados";

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUsuario = prefs.getString("idUsuario", "");

        if (idUsuario.isEmpty()) {
            Toast.makeText(this, "ID de usuario no encontrado. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray datos = response.getJSONArray("datos");

                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject obj = datos.getJSONObject(i);

                            String imagenUrl = obj.getString("imagenUrl");
                            String titulo = obj.getString("titulo");
                            String categoria = "Categoría: " + obj.getString("categoria");
                            String descripcion = "Descripción: " + obj.getString("descripcion");
                            String recomendaciones = "Recomendaciones: " + obj.getString("recomendaciones");
                            String beneficios = "Beneficios: " + obj.getString("beneficios");
                            String sintomas = "Síntomas comunes: " + obj.getString("sintomas_comunes");

                            Prenatal prenatal = new Prenatal(imagenUrl,titulo, categoria, descripcion, recomendaciones, beneficios, sintomas);
                            prenatalList.add(prenatal);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        requestQueue.add(request);
    }
}
