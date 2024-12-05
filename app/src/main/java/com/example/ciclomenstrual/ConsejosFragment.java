package com.example.ciclomenstrual;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

public class ConsejosFragment extends Fragment {

    public ConsejosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consejos, container, false);

        ImageView imgFood = rootView.findViewById(R.id.img_food);
        ImageView imgPrenatal = rootView.findViewById(R.id.img_prenatal);
        ImageView imgCycle = rootView.findViewById(R.id.img_cycle);

        String urlFood = "https://cdn-icons-png.flaticon.com/512/7757/7757761.png";
        String urlPrenatal = "https://cdn-icons-png.flaticon.com/512/8123/8123466.png";
        String urlCycle = "https://cdn-icons-png.flaticon.com/512/2867/2867360.png";

        Picasso.get().load(urlFood).into(imgFood);
        Picasso.get().load(urlPrenatal).into(imgPrenatal);
        Picasso.get().load(urlCycle).into(imgCycle);

        imgFood.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AlimentacionClass.class);
            startActivity(intent);
        });

        imgPrenatal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PrenatalClass.class);
            startActivity(intent);
        });

        imgCycle.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CicloClass.class);
            startActivity(intent);
        });

        return rootView;
    }
}
