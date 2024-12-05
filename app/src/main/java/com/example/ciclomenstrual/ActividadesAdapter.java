package com.example.ciclomenstrual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ActividadesAdapter extends ArrayAdapter<Actividades> {
    public ActividadesAdapter(Context context, List<Actividades> actividades) {
        super(context, 0, actividades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Actividades actividad = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_actividades, parent, false);
        }

        // Obtener las vistas
        TextView nombreActividad = convertView.findViewById(R.id.nombreActividad);
        TextView descripcion = convertView.findViewById(R.id.descripcion);
        TextView fase = convertView.findViewById(R.id.fase);
        ImageView imagen = convertView.findViewById(R.id.imagen);

        // Asignar los datos a las vistas
        nombreActividad.setText(actividad.getNombreActividad());
        descripcion.setText("Descripción: " + actividad.getDescripcion());
        fase.setText("Fase recomendada: " + actividad.getFaseRecomendada());


        return convertView;
    }
}

