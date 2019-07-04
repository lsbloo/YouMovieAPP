package org.toolboxinst.buscadornotas.model;

import java.io.Serializable;

/**
 * Entity Movie API;
 */
public class Movie implements Serializable {

    private String name_movie;

    private String sinopse_movie;

    private String img_movie;

    private byte[] img_move_byte;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    private String nota;

    private String genre;

    public String actors;

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getImg_move_byte() {
        return img_move_byte;
    }

    public String getImg_movie() {
        return img_movie;
    }

    public String getName_movie() {
        return name_movie;
    }

    public String getNota() {
        return nota;
    }

    public String getSinopse_movie() {
        return sinopse_movie;
    }

    public void setImg_move_byte(byte[] img_move_byte) {
        this.img_move_byte = img_move_byte;
    }

    public void setImg_movie(String img_movie) {
        this.img_movie = img_movie;
    }

    public void setName_movie(String name_movie) {
        this.name_movie = name_movie;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setSinopse_movie(String sinopse_movie) {
        this.sinopse_movie = sinopse_movie;
    }

    public Movie(){

    }
}
