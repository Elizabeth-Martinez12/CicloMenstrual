package com.example.ciclomenstrual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.CalendarioViewHolder> {

    private Context context;
    private List<Calendario> calendarioList;

    public CalendarioAdapter(Context context, List<Calendario> calendarioList) {
        this.context = context;
        this.calendarioList = calendarioList;
    }

    public static class CalendarioViewHolder extends RecyclerView.ViewHolder {
        TextView textNombreDia, textDia;

        public CalendarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombreDia = itemView.findViewById(R.id.text_nombre_dia);
            textDia = itemView.findViewById(R.id.text_dia);
        }
    }

    public void setCalendarios(List<Calendario> nuevosCalendarios) {
        this.calendarioList = nuevosCalendarios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalendarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calendario, parent, false);
        return new CalendarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarioViewHolder holder, int position) {
        Calendario calendario = calendarioList.get(position);

        holder.textNombreDia.setText(calendario.getNombreDia());
        holder.textDia.setText(new SimpleDateFormat("dd", Locale.getDefault()).format(calendario.getFechaInicio()));

        if (calendario.esDeshabilitado()) {
            holder.textDia.setBackgroundResource(R.drawable.circle_background_disabled);
            holder.textDia.setTextColor(ContextCompat.getColor(context, R.color.gray_light));
        } else if (calendario.esDiaEvento()) {
            holder.textDia.setBackgroundResource(R.drawable.circle_background_event);
            holder.textDia.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else if (calendario.esPeriodoEspecial()) {
            holder.textDia.setBackgroundResource(R.drawable.circle_background_selected);
            holder.textDia.setTextColor(ContextCompat.getColor(context, R.color.light_red));
        } else {
            holder.textDia.setBackgroundResource(R.drawable.circle_background);
            holder.textDia.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return calendarioList.size();
    }
}
