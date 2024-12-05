package com.example.ciclomenstrual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView registerLink;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        requestQueue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Ingrese su usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    authenticateUser(user, pass);
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    private void authenticateUser(String user, String pass) {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/getUsuario";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray datos = response.getJSONArray("datos");
                        boolean userFound = false;

                        for (int i = 0; i < datos.length(); i++) {
                            JSONObject userObject = datos.getJSONObject(i);
                            String apiNombre = userObject.getString("nombreUsuario");
                            String apiContraseña = userObject.getString("contrasenia");
                            String apiIdUsuario = userObject.getString("idUsuario");

                            if (apiNombre.equals(user) && apiContraseña.equals(pass)) {
                                userFound = true;
                                handleLoginSuccess(apiNombre, apiIdUsuario);
                                break;
                            }
                        }

                        if (!userFound) {
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "Error procesando datos", e);
                        Toast.makeText(LoginActivity.this, "Error procesando datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Error al conectar", error);
                    Toast.makeText(LoginActivity.this, "Error al conectar", Toast.LENGTH_SHORT).show();
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(request);
    }


    private void handleLoginSuccess(String apiNombre, String apiIdUsuario) {
        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("idUsuario", apiIdUsuario);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("idUsuario", apiIdUsuario);
        startActivity(intent);
    }
}
