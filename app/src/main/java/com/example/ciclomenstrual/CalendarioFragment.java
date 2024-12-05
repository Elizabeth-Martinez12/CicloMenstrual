package com.example.ciclomenstrual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CalendarioFragment extends Fragment {

    private CalendarView calendarView;
    private TextView txtFechaSeleccionada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendario, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        txtFechaSeleccionada = rootView.findViewById(R.id.txtMesAnio);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1; // De 0 a 11

                String selectedDate = dayOfMonth + "/" + month + "/" + year;
                txtFechaSeleccionada.setText(selectedDate);
            }
        });

        return rootView;
    }
}
