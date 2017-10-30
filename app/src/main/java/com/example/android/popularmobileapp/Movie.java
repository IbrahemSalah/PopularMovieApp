package com.example.android.popularmobileapp;

import android.util.Log;


/**
 * Created by Eng_I on 6/6/2017.
 */

public class Movie {

    int    id;
    String title;
    String original_title;
    String overview;
    String popularity;
    String release_date;
    String poster_path;
    String backdrop_path;
    String original_language;
    String vote_average;
    String vote_count;
    String tagline;
    String status;
    String runtime;
    String revenue;


    public Movie(){
        id=0;
        title="";
        original_title="";
        overview="";
        popularity="";
        release_date="";
        poster_path="";
        backdrop_path="";
        original_language="";
        vote_average="";
        vote_count="";
        tagline="";
        status="";
        runtime="";
        revenue="";
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    //for debug
    public static void showMoviesData(Movie movie){
        Log.i("ID :", String.valueOf(movie.id));
        Log.i("Title :",movie.title);
        Log.i("Popularity  :",movie.popularity);
        Log.i("Language :",movie.original_language);
        Log.i("Run Time :",movie.runtime);
        Log.i("Poster :",movie.poster_path);
        Log.i("Vote Avg :",movie.vote_average);
        Log.i("Status :",movie.status);
    }
}
