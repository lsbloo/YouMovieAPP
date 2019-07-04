package org.toolboxinst.buscadornotas.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.toolboxinst.buscadornotas.model.Movie;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class ConnectorImpl {


    protected static final String API_URL_BASE = "http://www.omdbapi.com/";
    public static final String MY_KEY="4e0ff564";
    public static final String CONTENT_TYPE = "application/json";



    private ConnectorEntry service;
    public ConnectorImpl (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(ConnectorEntry.class);
    }


    public void getMovie(String api_key, String title) throws IOException {
        Call<Object> call = this.service.getMovies(api_key,title);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.err.println(call.isExecuted());
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }



    public String getMovieConnect(String key,String tittle){
      String u = API_URL_BASE+"?apikey="+key+"&t="+tittle;
      try{
          URL url = new URL(u);
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
          urlConnection.setRequestMethod("GET");
          urlConnection.setDoInput(true);
          urlConnection.connect();
          if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
              InputStream inputStream = urlConnection.getInputStream();
              return converterInputStreamToString(inputStream);
          }

      }catch (MalformedURLException e){
          e.printStackTrace();
      } catch (ProtocolException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }

      return null;
    }



    private static String converterInputStreamToString(InputStream is){

        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();

    }

    // eu preciso baixar a foto? se sim.
    // HttpConnection? é
    public byte[] getPhotoMovieByAPI(String url_photo_movie){
        try {
            URL url = new URL(url_photo_movie);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap map  = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                map.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

                byte[] img = byteArrayOutputStream.toByteArray();
                return img;

            }


        }catch(MalformedURLException e){
            // se a url não for "adequada"; =\
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
