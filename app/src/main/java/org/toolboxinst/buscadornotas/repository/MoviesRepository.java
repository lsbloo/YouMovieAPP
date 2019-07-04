package org.toolboxinst.buscadornotas.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesRepository extends SQLiteOpenHelper {


    public static final String NAME_DATABASE="MoviesDB";
    public static final String NAME_TABLE = "MOVIES_TABLE";
    public static final Integer VERSION_DATABASE=1;
    public static final String COLUMN_NAME_MOVIE="name_movies";
    public static final String COLUMN_SINOPSE="sinopse_movies";
    public static final String COLUMN_IMG_BYTES="images_movies";
    public static final String COLUMN_NOTAS="notas_movies";
    public static final String COLUMN_GENRE="genre_movies";
    public static final String COLUMN_ACTORS = "actors_movies";
    public static final String COLUMN_ID = "id_movie";


   public MoviesRepository(Context ctx){
       super(ctx,NAME_DATABASE,null,VERSION_DATABASE);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL(
               "CREATE TABLE IF NOT EXISTS " + NAME_TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                       COLUMN_NAME_MOVIE + " TEXT NOT NULL , " + COLUMN_SINOPSE + " TEXT NOT NULL , " + COLUMN_IMG_BYTES + " BLOB , " +
                       COLUMN_NOTAS + " TEXT ," + COLUMN_GENRE + " TEXT ," + COLUMN_ACTORS + " TEXT" + " )"
       );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
