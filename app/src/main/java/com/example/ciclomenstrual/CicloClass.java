package com.example.ciclomenstrual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CicloClass extends AppCompatActivity {

    private ImageView imagenCicloMenstrual;
    private TextView txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duracion); // Reemplaza por tu layout XML

        imagenCicloMenstrual = findViewById(R.id.imagenCicloMenstrual);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Activa la flecha de regreso
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        String imagenUrl = "https://www.quironsalud.com/embarazoymaternidad/es/etapas/quiero-quedarme-embarazada/ciclo-menstrual-fertilidad.ficheros/1394601-Ciclo_v2.jpg?width=500&height=515&aspectRatio=true";  // URL de la imagen
        new LoadImageTask(imagenCicloMenstrual).execute(imagenUrl);

        String descripcion = "El ciclo menstrual es un proceso natural que se repite cada mes en el cuerpo de las personas con útero, " +
                "y es un indicador importante de la salud reproductiva. Comienza el primer día de un período menstrual, " +
                "cuando el revestimiento del útero se desprende y se expulsa a través de la vagina en forma de sangre y tejido. " +

                "\n\nEl ciclo tiene varias fases principales: la fase menstrual, la fase folicular, la ovulación y la fase lútea. " +
                "Durante la fase folicular, el cuerpo comienza a preparar un óvulo para la ovulación. En esta etapa, " +
                "los niveles hormonales de estrógeno aumentan y estimulan el crecimiento del revestimiento uterino." +

                "\n\nLa ovulación ocurre aproximadamente a la mitad del ciclo, cuando un ovario libera un óvulo maduro. " +
                "Este es el momento más fértil del ciclo, ya que el óvulo puede ser fecundado si se encuentra con un espermatozoide." +

                "\n\nLa fase lútea sigue a la ovulación. Durante esta fase, el cuerpo lúteo se forma en el ovario y produce progesterona, " +
                "una hormona que ayuda a mantener el revestimiento del útero en caso de que ocurra un embarazo. Si el óvulo no es fertilizado, " +
                "los niveles hormonales disminuyen, lo que provoca el inicio de un nuevo ciclo menstrual." +

                "\n\nLa duración típica del ciclo menstrual varía de persona a persona, pero generalmente se encuentra entre 21 y 35 días, " +
                "con un promedio de 28 días. La duración del sangrado menstrual suele ser de 2 a 7 días. " +

                "\n\nSe considera que un ciclo menstrual es irregular cuando la duración de los ciclos es muy variable, " +
                "con descansos demasiado largos o cortos entre los períodos. Esto puede deberse a diversos factores como cambios hormonales, " +
                "estrés, dieta, actividad física excesiva o condiciones médicas subyacentes como el síndrome de ovario poliquístico (SOP) o problemas tiroideos." +

                "\n\nEs importante llevar un registro del ciclo menstrual, ya que esto puede ayudar a identificar patrones y posibles irregularidades. " +
                "Consultar a un médico es recomendable si se presentan cambios significativos en el ciclo o síntomas preocupantes, " +
                "como sangrados muy abundantes, períodos extremadamente dolorosos o la ausencia prolongada de menstruación (amenorrea).";

        txtDescripcion.setText(descripcion);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUsuario = prefs.getString("idUsuario", "");

        if (idUsuario.isEmpty()) {
            Toast.makeText(this, "ID de usuario no encontrado. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class)); // Redirige a inicio de sesión si falta el ID
            finish();
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Detecta la flecha de regreso
            onBackPressed(); // Llama al método de retroceso
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlString = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                Toast.makeText(imageView.getContext(), "Error al cargar la imagen.", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.womens_day); // Fallback local
            }
        }
    }
}
