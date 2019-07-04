package org.toolboxinst.buscadornotas.service;

import org.toolboxinst.buscadornotas.model.Movie;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface ConnectorEntry {


    @GET("/{apikey}/{t}")
    Call<Object> getMovies(@Path("apikey") String apikey , @Path("t") String tittle);
}
