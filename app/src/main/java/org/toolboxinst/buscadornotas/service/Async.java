package org.toolboxinst.buscadornotas.service;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.toolboxinst.buscadornotas.model.Movie;
import org.toolboxinst.buscadornotas.repository.HelperRepository;

public class Async extends AsyncTask<String, Void , String> {


    private Context ctx;
    public Async(Context ctx){

        this.ctx=ctx;
    }

    public void InsertMovies(Movie m){
        ConnectorImpl c = new ConnectorImpl();
        HelperRepository helperRepository = new HelperRepository(this.ctx);
        byte[] array = c.getPhotoMovieByAPI(m.getImg_movie());
        m.setImg_move_byte(array);
        helperRepository.insertMovie(m);

    }

    @Override
    protected String doInBackground(String... strings) {
        String key = strings[0];
        String title = strings[1];
        ConnectorImpl c = new ConnectorImpl();
        String exit =  c.getMovieConnect(key,title);
        Movie m  = getMovieFormatedJson(exit);
        InsertMovies(m);
        return exit;
    }

    public Movie getMovieFormatedJson(String exit){
        try {
            System.err.println(exit);
            Movie m = new Movie();

                JSONObject obj = new JSONObject(exit);

                String name = obj.getString("Title");
                String genre = obj.getString("Genre");
                String actor = obj.getString("Actors");
                String img = obj.getString("Poster");
                String sinopse = obj.getString("Plot");
                String nota = obj.getString("imdbRating");


                m.setName_movie(name);
                m.setGenre(genre);
                m.setActors(actor);
                m.setImg_movie(img);
                m.setSinopse_movie(sinopse);
                m.setNota(nota);
            return m;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
