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

public class AlimentacionClass extends AppCompatActivity {
    private ListView listView;
    private AlimentacionAdapter adapter;
    private List<Alimentacion> alimentacionList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentacion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        listView = findViewById(R.id.listViewAlimentacion);
        alimentacionList = new ArrayList<>();
        adapter = new AlimentacionAdapter(this, alimentacionList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchAlimentacionData();
    }

    private void fetchAlimentacionData() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getAlimentacion";

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUsuario = prefs.getString("idUsuario", "");

        if (idUsuario.isEmpty()) {
            Toast.makeText(this, "ID de usuario no encontrado. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class)); // Redirige a inicio de sesión si falta el ID
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
                            String nombreAlimento = obj.getString("nombreAlimento");
                            String beneficios = "Beneficios: " + obj.getString("beneficios");
                            String faseRecomendada = "Fase Recomendada: " + obj.getString("faseRecomendada");

                            Alimentacion alimentacion = new Alimentacion(imagenUrl, nombreAlimento, beneficios, faseRecomendada);
                            alimentacionList.add(alimentacion);
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
