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

public class AlimentacionAdapter extends BaseAdapter {
    private Context context;
    private List<Alimentacion> alimentacionList;

    public AlimentacionAdapter(Context context, List<Alimentacion> alimentacionList) {
        this.context = context;
        this.alimentacionList = alimentacionList;
    }

    @Override
    public int getCount() {
        return alimentacionList.size();
    }

    @Override
    public Object getItem(int position) {
        return alimentacionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alimentacion, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.nombreAlimento = convertView.findViewById(R.id.nombreAlimento);
            holder.beneficios = convertView.findViewById(R.id.beneficios);
            holder.faseRecomendada = convertView.findViewById(R.id.fase);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Alimentacion alimentacion = alimentacionList.get(position);

        holder.nombreAlimento.setText(alimentacion.getNombreAlimento());
        holder.beneficios.setText(alimentacion.getBeneficios());
        holder.faseRecomendada.setText(alimentacion.getFaseRecomendada());
        Picasso.get().load(alimentacion.getImagenUrl()).into(holder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView nombreAlimento, beneficios, faseRecomendada;
    }
}
