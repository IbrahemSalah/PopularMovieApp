package com.example.android.popularmobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmobileapp.ContentProvider.MoviesContentProvider;
import com.example.android.popularmobileapp.ContentProvider.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieActivity extends AppCompatActivity {

    public TextView descText;
    public TextView titleText;
    public TextView dateText;
    public TextView ratingText;
    public ImageView movieposterImageView;
    public static ArrayList<String> trailerText;
    public static ArrayAdapter<String> trailersArrayAdapter;
    public ArrayAdapter<String> reviewsArrayAdapter;
    public static ListView trailerList;
    public static ListView reviewsList;
    public int position;
    public ImageButton favoriteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favoriteButton = (ImageButton) findViewById(R.id.favoriteButton);
        trailerList=(ListView)findViewById(R.id.trailersListView);

        trailerText= new ArrayList<>();
        descText=(TextView) findViewById(R.id.descText);
        titleText=(TextView)findViewById(R.id.titleText);
        dateText=(TextView)findViewById(R.id.dateText);
        ratingText=(TextView)findViewById(R.id.ratingText);
        movieposterImageView=(ImageView)findViewById(R.id.movieposterImage);


        Intent movieIntent=getIntent();
        position=  movieIntent.getIntExtra("position",0);

        descText.setText(MainActivity.moviesList.get(position).overview);
        titleText.setText(MainActivity.moviesList.get(position).title);
        ratingText.setText(MainActivity.moviesList.get(position).vote_average);
        dateText.setText(MainActivity.moviesList.get(position).release_date);
        Picasso.with(getApplicationContext()).load(MainActivity.images.get(position)).into(movieposterImageView);

        MainActivity.getMovieTrailers(position);


        publishTrailerListView(position, getApplicationContext());
        publishReviewsListView(position,getApplicationContext());

        setDefaultState();
    }


    public void publishTrailerListView(int position, final Context context){

        trailersArrayAdapter= new ArrayAdapter<String>(context, R.layout.trailer_item,trailerText);

        trailerList=(ListView)findViewById(R.id.trailersListView);
        trailerList.setAdapter(trailersArrayAdapter);

        trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Trailer position", String.valueOf(position));

                String url = APIStrings.YouTube_Start_Link+MainActivity.trailersList.get(position);
                //Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }


    public void publishReviewsListView(int position, final Context context){
        reviewsArrayAdapter= new ArrayAdapter<String>(context, R.layout.review_item,MainActivity.reviewsList);

        reviewsList=(ListView)findViewById(R.id.reviewsListView);
        reviewsList.setAdapter(reviewsArrayAdapter);

    }

    public void favoriteButtonClick(View view){

        boolean movieAlreadyExisted=checkIfMovieIsFavorite();

        if (movieAlreadyExisted){
            removeMovieFromFavorite();
        }else{
            addMovieToFavorite();
        }

    }

    public boolean checkIfMovieIsFavorite(){

        Cursor cursor = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(MainActivity.moviesList.get(position).id)).build(),
                null,
                MoviesContract.MoviesEntry.COLUMN_MOVIES_ID + "=" + MainActivity.moviesList.get(position).id,
                null,
                null);

        Log.i("Value: ", String.valueOf(cursor.getCount()));

        if(cursor.getCount() == 0) {
            // didn't find movie ID
            return false;
        }
        else {
            return true;
        }
    }

    public void addMovieToFavorite(){

        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIES_ID, MainActivity.moviesList.get(position).id);
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_NAME, MainActivity.moviesList.get(position).title);
        // i could have added the aditional but it's not worthy because images wont be loaded while it is offline

        Uri uri = getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

        if(uri != null) {
            Toast.makeText(getBaseContext(), "Movie Added To Favorite ..!", Toast.LENGTH_SHORT).show();
            favoriteButton.setBackgroundResource(R.drawable.in);
        }
    }

    public void removeMovieFromFavorite(){
        int movieDeleted=0;
        movieDeleted = getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(MainActivity.moviesList.get(position).id)).build(),
                MoviesContract.MoviesEntry.COLUMN_MOVIES_ID + "=" + MainActivity.moviesList.get(position).id,
                null);

        if (movieDeleted!=0){
            Toast.makeText(getBaseContext(), "Movie Removed From Favorite ..!", Toast.LENGTH_SHORT).show();
            favoriteButton.setBackgroundResource(R.drawable.out);
        }

    }

    public void setDefaultState(){
        boolean isFavorite=checkIfMovieIsFavorite();

        if (isFavorite){
            favoriteButton.setBackgroundResource(R.drawable.in);
        }else{
            favoriteButton.setBackgroundResource(R.drawable.out);
        }
    }

    /*
    @Override
    public void onBackPressed() {
        if(MainActivity.moviesList!=null) {
            MainActivity.gridAdapter.notifyDataSetChanged();
            NavUtils.navigateUpFromSameTask(this);
        }
    }
    */
}
