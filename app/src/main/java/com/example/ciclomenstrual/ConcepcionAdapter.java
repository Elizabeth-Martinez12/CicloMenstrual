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

public class ConcepcionAdapter extends BaseAdapter {
    private Context context;
    private List<Concepcion> concepcionList;

    public ConcepcionAdapter(Context context, List<Concepcion> concepcionList) {
        this.context = context;
        this.concepcionList = concepcionList;
    }

    @Override
    public int getCount() {
        return concepcionList.size();
    }

    @Override
    public Object getItem(int position) {
        return concepcionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConcepcionAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_concepcion, parent, false);
            holder = new ConcepcionAdapter.ViewHolder();
            holder.titulo = convertView.findViewById(R.id.titulo);
            holder.descripcion = convertView.findViewById(R.id.descripcion);
            convertView.setTag(holder);
        } else {
            holder = (ConcepcionAdapter.ViewHolder) convertView.getTag();
        }

        Concepcion concepcion = concepcionList.get(position);

        holder.titulo.setText(concepcion.getTitulo());
        holder.descripcion.setText(concepcion.getDescripcion());

        return convertView;
    }

    private static class ViewHolder {
        TextView titulo, descripcion;
    }
}
