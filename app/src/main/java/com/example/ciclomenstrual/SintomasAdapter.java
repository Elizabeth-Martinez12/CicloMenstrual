package com.example.ciclomenstrual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.util.List;

public class SintomasAdapter extends ArrayAdapter<Sintoma> {
    public SintomasAdapter(Context context, List<Sintoma> sintomas) {
        super(context, 0, sintomas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sintoma, parent, false);
        }

        Sintoma sintoma = getItem(position);

        TextView nombreTextView = convertView.findViewById(R.id.nombreSintoma);
        TextView descripcionTextView = convertView.findViewById(R.id.descripcionSintoma);

        nombreTextView.setText(sintoma.getNombreSintoma());
        descripcionTextView.setText(sintoma.getDescripcion());

        return convertView;
    }
}

