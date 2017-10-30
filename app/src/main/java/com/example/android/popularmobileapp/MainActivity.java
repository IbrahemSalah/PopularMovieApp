package com.example.android.popularmobileapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Debug;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmobileapp.ContentProvider.MoviesContract;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static public ArrayList<Movie> moviesList;
    static public ArrayList<String> images;
    static public ArrayList<String> trailersList;
    static public ArrayList<String> reviewsList;
    public String mostPopular="http://api.themoviedb.org/3/movie/popular?api_key=3a4cbd478e1038335ec1fca44c04cb7d";
    public String highRated="http://api.themoviedb.org/3/movie/top_rated?api_key=3a4cbd478e1038335ec1fca44c04cb7d ";
    static public MoviesGridAdapter gridAdapter;
    static public GridView gridView;
    public static boolean connectionEnabled;
    public static Context currentContext;
    public static Cursor moviesData;
    public static final String LIFECYCLE_CALLBACKS_TEXT="callbacks";
    public static final String LIFECYCLE_CALLBACKS_SCROLL_POSITION = "position";
    public int currentState;
    public static int previousScrollPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String result="";
        moviesList=new ArrayList<>();
        images=new ArrayList<>();
        trailersList=new ArrayList<>();
        reviewsList=new ArrayList<>();
        currentContext=getApplicationContext();

         if (isNetworkAvailable()!= false) {

             if(savedInstanceState==null){
                 connectionEnabled=true;
                 getJsonData(0);
                 new FetchMovies(currentContext,-1);
             }

                gridView =(GridView) findViewById(R.id.moviesGridView);
                gridAdapter =new MoviesGridAdapter(MainActivity.this,moviesList,images);
                gridView.setAdapter(gridAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent movieIntent=new Intent(getApplicationContext(),MovieActivity.class);
                        Log.i("Default position ", String.valueOf(position));
                        movieIntent.putExtra("position",position);

                        trailersList=new ArrayList<String>();
                        reviewsList=new ArrayList<String>();
                        //getMovieTrailers(position);
                        getMovieReviews(position);
                        startActivity(movieIntent);
                    }
                });
             gridAdapter.notifyDataSetChanged();
         } else{
                Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
                connectionEnabled=false;
         }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Saved Instance State","  Fired!!");
        int index=0;
        if(isNetworkAvailable()!= false&&moviesList!=null){
        //Log.i("currentState :", String.valueOf(currentState));
        if (currentState==0){
            outState.putString(LIFECYCLE_CALLBACKS_TEXT,"mostpopular");
            index = gridView.getFirstVisiblePosition();
            outState.putInt(LIFECYCLE_CALLBACKS_SCROLL_POSITION,index);
            //Log.i("onSave :","State = 0");
        }else if(currentState==1){
            outState.putString(LIFECYCLE_CALLBACKS_TEXT,"highrated");
            index = gridView.getFirstVisiblePosition();
            outState.putInt(LIFECYCLE_CALLBACKS_SCROLL_POSITION,index);
            //Log.i("onSave :","State = 1");
        }else if(currentState==2){
            outState.putString(LIFECYCLE_CALLBACKS_TEXT,"favorite");
            index = gridView.getFirstVisiblePosition();
            outState.putInt(LIFECYCLE_CALLBACKS_SCROLL_POSITION,index);
            //Log.i("onSave :","State = 2");
            gridAdapter.notifyDataSetChanged();
        }

        //Log.i("Index :", String.valueOf(index));

        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Restore Instance State","  Fired!!");
        previousScrollPosition=0;
        //Log.i("currentState :", String.valueOf(currentState));
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT)){
                String previousState=savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT);
                if (previousState=="mostpopular"){
                    //Log.i("onRestore :","State = 0");
                    mostPopularButtonClicked();
                }else if(previousState=="highrated"){
                    //Log.i("onSave :","State = 1");
                    highRatedButtonClicked();
                }else if(previousState=="favorite"){
                    //Log.i("onSave :","State = 2");
                    favoriteButtonClicked();
                }
            }

         if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_SCROLL_POSITION)){
             previousScrollPosition=savedInstanceState.getInt(LIFECYCLE_CALLBACKS_SCROLL_POSITION);
             //Log.i("Passed index:", String.valueOf(previousScrollPosition));
         }
        }

        if(isNetworkAvailable()!= false&&moviesList!=null) {
            gridAdapter.notifyDataSetChanged();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //get json file
    //0 for most popular
    //1 for highest-rated
    public void getJsonData(int searchBy){
        FetchMovies downloadTask=new FetchMovies(currentContext,-1);
        try {
            if (searchBy == 0 ){
                downloadTask.execute(mostPopular);
            }
            else if(searchBy == 1){
                downloadTask.execute(highRated);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getMovieTrailers(int position){
        FetchMovieTrailer downloadtask=new FetchMovieTrailer(currentContext, position);
        try{
            downloadtask.execute(APIStrings.TRAILER_START_URL+ moviesList.get(position).id +APIStrings.TRAILER_POST_ID_URL+APIStrings.API_KEY+APIStrings.TRAILER_POST_KEY_URL);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getMovieReviews(int position) {
        FetchMovieReview downloadtask=new FetchMovieReview(currentContext, position);
        try{
            downloadtask.execute(APIStrings.TRAILER_START_URL+ moviesList.get(position).id +APIStrings.REVIEW_POST_ID_URL+APIStrings.API_KEY+APIStrings.TRAILER_POST_KEY_URL);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mostpopular_button) {
            mostPopularButtonClicked();
        }
        else if (id== R.id.highrated_button){
            highRatedButtonClicked();
        }
        else if (id== R.id.favorite_button){
            favoriteButtonClicked();
        }


        return super.onOptionsItemSelected(item);
    }

    public static void updategridAdapterWithData(){

        String movieName;
        int movieID;
        Log.i("Cursor Data: ", String.valueOf(moviesData.getCount()));

        if (moviesData!= null){

            int MOVIE_NAME_COL=moviesData.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME);
            int MOVIE_ID_COL=moviesData.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIES_ID);

            while (moviesData.moveToNext()){
                movieName=moviesData.getString(MOVIE_NAME_COL);
                movieID=moviesData.getInt(MOVIE_ID_COL);
                new FetchMovies(currentContext,movieID).execute(APIStrings.MOVIE_URL_SEARCH+movieName);
                Log.i("movieID", String.valueOf(movieID));
            }

            moviesData.close();
            gridAdapter.notifyDataSetChanged();
        }
    }

    public void mostPopularButtonClicked(){
        Log.i("Function Fired... :", "mostPopular");
        currentState=0;
        if (isNetworkAvailable()!= false) {
            moviesList = new ArrayList<>();
            images = new ArrayList<>();
            new FetchMovies(currentContext,-1).execute(mostPopular);
            if (!moviesList.isEmpty()){
                gridAdapter.notifyDataSetChanged();
            }        }
        else{
            Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }


    public void highRatedButtonClicked(){
        Log.i("Function Fired... :", "highRated");
        currentState=1;
        if (isNetworkAvailable()!= false) {
            moviesList = new ArrayList<>();
            images = new ArrayList<>();
            new FetchMovies(currentContext,-1).execute(highRated);

            if (!moviesList.isEmpty()){
            gridAdapter.notifyDataSetChanged();
            }
        }
        else{
            Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }


    public void favoriteButtonClicked(){
        Log.i("Function Fired... :", "favorite");
        currentState=2;
        if (isNetworkAvailable()!= false) {
            moviesList = new ArrayList<>();
            images = new ArrayList<>();
            new FetchMoviesCursor(getContentResolver()).execute();

            if (!moviesList.isEmpty()){
                gridAdapter.notifyDataSetChanged();
            }
        }
        else{
            Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }


}
