package com.example.ciclomenstrual;

import android.app.Activity;
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

public class ConcepcionClass extends AppCompatActivity {
    private ListView listView;
    private ConcepcionAdapter adapter;
    private List<Concepcion> concepcionList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concepcion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        listView = findViewById(R.id.listViewConcepcion);
        concepcionList = new ArrayList<>();
        adapter = new ConcepcionAdapter(this, concepcionList);
        listView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchAlimentacionData();
    }

    private void fetchAlimentacionData() {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getConcepcion";

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
                            String titulo = obj.getString("titulo");
                            String descripcion = obj.getString("descripcion");

                            Concepcion concepcion = new Concepcion(titulo, descripcion);
                            concepcionList.add(concepcion);
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

