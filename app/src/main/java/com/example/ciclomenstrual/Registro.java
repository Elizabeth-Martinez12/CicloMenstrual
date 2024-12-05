package com.example.ciclomenstrual;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    private static final String TAG = "Registro";
    private EditText nombre, edad, correo, username, password;
    private Button registerButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.nombre);
        edad = findViewById(R.id.edad);
        correo = findViewById(R.id.correo);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.loginButton);

        requestQueue = Volley.newRequestQueue(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUser();
                } catch (JSONException e) {
                    Log.e(TAG, "Error al crear JSON", e);
                }
            }
        });
    }

    private void registerUser() throws JSONException {
        String url = "https://ciclomenstrual-431e75843ffd.herokuapp.com/ciclo/insertUsuario";

        JSONObject userData = new JSONObject();
        userData.put("nombre", nombre.getText().toString().trim());
        userData.put("edad", edad.getText().toString().trim());
        userData.put("correoElectronico", correo.getText().toString().trim());
        userData.put("nombreUsuario", username.getText().toString().trim());
        userData.put("contrasenia", password.getText().toString().trim());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(Registro.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al procesar la respuesta", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud", error);
                        Toast.makeText(Registro.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
}


        private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
