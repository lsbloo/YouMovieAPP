package org.toolboxinst.buscadornotas.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.toolboxinst.buscadornotas.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class HelperRepository {


    private MoviesRepository moviesRepository;
    private Context ctx;
    private List<Movie> my_movies_recovery = new ArrayList<Movie>();

    public HelperRepository(Context ctx){
        this.ctx=ctx;
        this.moviesRepository = new MoviesRepository(this.ctx);
    }

    public List<Movie> recoveryMyMoviesInsert(){
        SQLiteDatabase db = this.moviesRepository.getReadableDatabase();
        String query_sql = "SELECT * FROM " + this.moviesRepository.NAME_TABLE;

        Cursor c = db.rawQuery(query_sql,null);

        while(c.moveToNext()){
            int indexColumName = c.getColumnIndex(this.moviesRepository.COLUMN_NAME_MOVIE);
            int indexColumnActor = c.getColumnIndex(this.moviesRepository.COLUMN_ACTORS);
            int indexColumnGenre = c.getColumnIndex(this.moviesRepository.COLUMN_GENRE);
            int indexColumnNotas = c.getColumnIndex(this.moviesRepository.COLUMN_NOTAS);
            int indexColumnSinopse = c.getColumnIndex(this.moviesRepository.COLUMN_SINOPSE);
            int indexColumnId = c.getColumnIndex(this.moviesRepository.COLUMN_ID);
            int indexColumnPhoto = c.getColumnIndex(this.moviesRepository.COLUMN_IMG_BYTES);
            String name = c.getString(indexColumName);
            String actor = c.getString(indexColumnActor);
            String genre = c.getString(indexColumnGenre);
            String notas = c.getString(indexColumnNotas);
            String sinopse = c.getString(indexColumnSinopse);
            Long id = c.getLong(indexColumnId);
            byte[] img_bytes = c.getBlob(indexColumnPhoto);
            my_movies_recovery.add(getMovie(name,actor,genre,notas,sinopse,id,img_bytes));
        }
        return this.my_movies_recovery;
    }

    public void clear(){
        this.my_movies_recovery.clear();
    }

    public Movie getMovie(String name,String actor,String genre,String notas, String sinopse,Long id,byte[] photo){
        Movie m = new Movie();

        m.setName_movie(name);
        m.setActors(actor);
        m.setGenre(genre);
        m.setNota(notas);
        m.setSinopse_movie(sinopse);
        m.setId(id);
        m.setImg_move_byte(photo);
        return m;
    }

    /**
     * Example check Existence param Name;
     * @param movie
     * @return
     */
    public boolean checkExisteSameMovie(Movie movie){
        if(my_movies_recovery.size() == 0){
            recoveryMyMoviesInsert();
        }

        for(Movie m : my_movies_recovery){
            if(m.getName_movie().equals(movie.getName_movie())){
                return true;
            }
        }
        return false;

    }

    public int deleteMovie(Movie movie){
        SQLiteDatabase db =  this.moviesRepository.getWritableDatabase();
        int line = db.delete(this.moviesRepository.NAME_TABLE,this.moviesRepository.COLUMN_ID+"=?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
        Log.d("Delete" , "Delete movie from database.");
        return line;
    }

    public Movie getMovieById(Long id){
        SQLiteDatabase db = this.moviesRepository.getWritableDatabase();
        String query = "SELECT * FROM " + this.moviesRepository.NAME_TABLE + " WHERE " + this.moviesRepository.COLUMN_ID + "=" + id;
        Cursor c = db.rawQuery(query,null);
        String name = "";
        String actor = "";
        String genre = "";
        String notas = "";
        String sinopse = "";
        Long idx = null;
        byte[] img_bytes = null;

        while(c.moveToNext()){
            int indexColumName = c.getColumnIndex(this.moviesRepository.COLUMN_NAME_MOVIE);
            int indexColumnActor = c.getColumnIndex(this.moviesRepository.COLUMN_ACTORS);
            int indexColumnGenre = c.getColumnIndex(this.moviesRepository.COLUMN_GENRE);
            int indexColumnNotas = c.getColumnIndex(this.moviesRepository.COLUMN_NOTAS);
            int indexColumnSinopse = c.getColumnIndex(this.moviesRepository.COLUMN_SINOPSE);
            int indexColumnId = c.getColumnIndex(this.moviesRepository.COLUMN_ID);
            int indexColumnPhoto = c.getColumnIndex(this.moviesRepository.COLUMN_IMG_BYTES);
            name = c.getString(indexColumName);
            actor = c.getString(indexColumnActor);
            genre = c.getString(indexColumnGenre);
            notas = c.getString(indexColumnNotas);
            sinopse = c.getString(indexColumnSinopse);
            idx = c.getLong(indexColumnId);
            img_bytes = c.getBlob(indexColumnPhoto);
        }
        return getMovie(name,actor,genre,notas,sinopse,idx,img_bytes);
    }


    public void insertMovie(Movie movie){
        if(checkExisteSameMovie(movie)){
            Log.d("OR NO!" , "There is a movie with this same name in the database. ");
            clear();
        }else {
            SQLiteDatabase db = this.moviesRepository.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(this.moviesRepository.COLUMN_NAME_MOVIE, movie.getName_movie());
            cv.put(this.moviesRepository.COLUMN_ACTORS, movie.getActors());
            cv.put(this.moviesRepository.COLUMN_GENRE, movie.getGenre());
            cv.put(this.moviesRepository.COLUMN_NOTAS, movie.getNota());
            cv.put(this.moviesRepository.COLUMN_SINOPSE, movie.getSinopse_movie());
            cv.put(this.moviesRepository.COLUMN_IMG_BYTES, movie.getImg_move_byte());

            Long id = db.insert(this.moviesRepository.NAME_TABLE, null, cv);

            if (id != -1) {
                movie.setId(id);
            }
        }
    }
}
