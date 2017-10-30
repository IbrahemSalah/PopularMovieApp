package com.example.android.popularmobileapp;

/**
 * Created by Eng_I on 6/6/2017.
 */


public class APIStrings {

    // complete UR
    static public String API_FULL_URL="http://api.themoviedb.org/3/movie/popular?api_key=3a4cbd478e1038335ec1fca44c04cb7d";
    // this is part of the URL "PART"
    static public String API_URL = "http://api.themoviedb.org/3/discover/movie";
    // this is the API Key
    static public String API_KEY = "3a4cbd478e1038335ec1fca44c04cb7d";
    //URL Start
    static public String IMAGE_URL = "http://image.tmdb.org/t/p/";
    //desired size
    static public String IMAGE_SIZE_185 = "w500";
    //default image for null posters
    static public String IMAGE_NOT_FOUND = "http://www.wellesleysocietyofartists.org/wp-content/uploads/2015/11/image-not-found.jpg";
    //youtube start url
    static public String YouTube_Start_Link="https://www.youtube.com/watch?v=";
    // trailer start url
    static public String TRAILER_START_URL="https://api.themoviedb.org/3/movie/";
    //second part for url after the id for trailer
    static public String TRAILER_POST_ID_URL="/videos?api_key=";
    //second part for url after the id for review
    static public String REVIEW_POST_ID_URL="/reviews?api_key=";
    //3rd part of url after the api key
    static public String TRAILER_POST_KEY_URL="&language=en-US";

    static public String MOVIE_URL_SEARCH="https://api.themoviedb.org/3/search/movie?api_key=3a4cbd478e1038335ec1fca44c04cb7d&query=";

    //TRAILER_START_URL+id+TRAILER_POST_ID_URL+key+TRAILER_POST_KEY_URL
    //https://api.themoviedb.org/3/movie/211672/videos?api_key=3a4cbd478e1038335ec1fca44c04cb7d&language=en-US


    //https://api.themoviedb.org/3/search/movie?api_key=3a4cbd478e1038335ec1fca44c04cb7d&query=Deadpool

}
