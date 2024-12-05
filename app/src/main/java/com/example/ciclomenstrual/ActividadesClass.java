package com.example.ciclomenstrual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActividadesClass extends AppCompatActivity {
    private ListView listView;
    private ActividadesAdapter adapter;
    private ArrayList<Actividades> actividadesList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ImageView headerImage = findViewById(R.id.headerImage);
        String imageUrl = "https://content.clara.es/medio/2021/12/20/patry-jordan-ciclo-menstrual-entrenar_74207827_800x800.jpg"; // Reemplaza con la URL de tu imagen

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.time)
                .error(R.drawable.error)
                .into(headerImage);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        listView = findViewById(R.id.listViewActividades);
        actividadesList = new ArrayList<>();
        adapter = new ActividadesAdapter(this, actividadesList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchActividadesData();
    }

    private void fetchActividadesData() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getActividad";

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
                    Log.d("ActividadesClass", "Respuesta del API: " + response.toString());
                    try {
                        JSONArray datos = response.getJSONArray("datos");
                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject obj = datos.getJSONObject(i);
                            String nombreActividad = obj.getString("nombreActividad");
                            String descripcion = obj.getString("descripcion");
                            String faseRecomendada = obj.getString("faseRecomendada");
                            Actividades actividad = new Actividades(nombreActividad, descripcion, faseRecomendada);
                            actividadesList.add(actividad);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ActividadesClass", "Error procesando datos: " + e.getMessage());
                    }
                },
                error -> Log.e("ActividadesClass", "Error de conexión: " + error.toString())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + prefs.getString("authToken", ""));
                return headers;
            }
        };

        requestQueue.add(request);
    }



    private void fetchActividadesDatails(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nombreActividad = response.getString("nombreActividad");
                            String descripcion = response.getString("descripcion");
                            String faseRecomendada = response.getString("faseRecomendada");

                            Actividades act = new Actividades(nombreActividad,descripcion, faseRecomendada);

                            actividadesList.add(act);

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActividadesClass.this, "error cargando los datos", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}
