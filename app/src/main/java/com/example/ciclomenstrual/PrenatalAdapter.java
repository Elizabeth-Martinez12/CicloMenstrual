package com.example.ciclomenstrual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PrenatalAdapter extends BaseAdapter {
    private Context context;
    private List<Prenatal> prenatalList;

    public PrenatalAdapter(Context context, List<Prenatal> prenatalList) {
        this.context = context;
        this.prenatalList = prenatalList;
    }

    @Override
    public int getCount() {
        return prenatalList.size();
    }

    @Override
    public Object getItem(int position) {
        return prenatalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PrenatalAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_prenatal, parent, false);
            holder = new PrenatalAdapter.ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.titulo = convertView.findViewById(R.id.titulo);
            holder.categoria = convertView.findViewById(R.id.categoria);
            holder.descripcion = convertView.findViewById(R.id.descripcion);
            holder.recomendaciones = convertView.findViewById(R.id.recomendaciones);
            holder.beneficios = convertView.findViewById(R.id.beneficios);
            holder.sintomas = convertView.findViewById(R.id.sintomas);
            convertView.setTag(holder);
        } else {
            holder = (PrenatalAdapter.ViewHolder) convertView.getTag();
        }

        Prenatal prenatal = prenatalList.get(position);

        holder.titulo.setText(prenatal.getTitulo());
        holder.categoria.setText(prenatal.getCategoria());
        holder.descripcion.setText(prenatal.getDescripcion());
        holder.recomendaciones.setText(prenatal.getRecomendaciones());
        holder.beneficios.setText(prenatal.getBeneficios());
        holder.sintomas.setText(prenatal.getSintomas());
        Picasso.get().load(prenatal.getImagenUrl()).into(holder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView titulo, categoria, descripcion, recomendaciones, beneficios, sintomas;
    }
}
