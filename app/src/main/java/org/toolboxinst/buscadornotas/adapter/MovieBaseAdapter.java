package org.toolboxinst.buscadornotas.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import org.toolboxinst.buscadornotas.R;
import org.toolboxinst.buscadornotas.model.Movie;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieBaseAdapter extends BaseAdapter {


    private static List<Movie> movieList;

    private Activity act;
    private Context context;

    public MovieBaseAdapter(List<Movie> movies, Activity act,Context ctx){
        this.movieList=movies;
        this.act=act;
        this.context=ctx;

    }

    @Override
    public int getCount() {
        return this.movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.movieList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(this.context)
                .inflate(R.layout.list_movies,parent,false);


        Movie m = this.movieList.get(position);


        TextView name =  v.findViewById(R.id.name);
        TextView nota =  v.findViewById(R.id.nota);
        CircleImageView photo = v.findViewById(R.id.photo);


        name.setText(m.getName_movie());
        if(m.getNota() == null){
            nota.setText("Not Found");
        }else{
            nota.setText("Nota:  " + m.getNota());
        }


        Bitmap pt = getPhoto(m.getImg_move_byte());

        if(pt != null) {
            photo.setImageBitmap(pt);
        }else{
            photo.setImageResource(R.drawable.img);

        }


        return v;
    }

    public Bitmap getPhoto(byte[] photo_bytes){
        try {
            return BitmapFactory.decodeByteArray(photo_bytes, 0, photo_bytes.length);
        }catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }

    }


    public Filter getFilter() {
        Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence filtro) {
            FilterResults results = new FilterResults();
            if (filtro == null || filtro.length() == 0) {
                results.count = movieList.size();
                results.values = movieList;
            } else {
                List<Movie> itens_filtrados = new ArrayList<Movie>();
                for (int i = 0; i < movieList.size(); i++) {
                    Movie data = movieList.get(i);
                    filtro = filtro.toString().toLowerCase();
                    String condicao = data.getName_movie().toLowerCase();
                    if (condicao.contains(filtro)) {
                        itens_filtrados.add(data);
                    }
                }
                results.count = itens_filtrados.size();
                results.values = itens_filtrados;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            Movie itens_exibicao = (Movie) results.values;
            notifyDataSetChanged();
        }

    };
        return filter;

    }


}
