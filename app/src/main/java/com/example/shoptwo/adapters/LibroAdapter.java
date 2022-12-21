package com.example.shoptwo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoptwo.entity.Libro;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.shoptwo.R;

public class LibroAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Libro> libros;

    public LibroAdapter(Context context, ArrayList<Libro> libros) {
        this.context = context;
        this.libros = libros;
    }

    @Override
    public int getCount() {
        return libros.size();
    }

    @Override
    public Object getItem(int position) {
        return libros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_lista_libros, null);
        }
        TextView lbTitulo = convertView.findViewById(R.id.lbTitulo);
        TextView lbCodigo = convertView.findViewById(R.id.lbCodigo);
        ImageView imagen = convertView.findViewById(R.id.imagen);

        Libro libro = libros.get(position);

        lbTitulo.setText(libro.getTitulo());
        lbCodigo.setText(libro.getCodigo());

        if (libro.getImagen()!=null && libro.getImagen().length()>7) {
            Picasso.get()
                    .load(libro.getImagen())
                    .resize(100, 100)
                    .centerCrop()
                    .into(imagen);
        }

        return convertView;
    }
}

