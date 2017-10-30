package com.example.android.popularmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eng_I on 5/31/2017.
 */

public class MoviesGridAdapter extends BaseAdapter {

    public LayoutInflater inflater;

    public Context context;

     public ArrayList<Movie> moviesList;
     public ArrayList<String> images;


    public MoviesGridAdapter(Context context, ArrayList<Movie> moviesList, ArrayList<String> images){

        this.context=context;

        this.moviesList= moviesList;

        this.images=images;
    }


    @Override
    public int getCount() {
        return MainActivity.moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridLayout= convertView;

        if (convertView == null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridLayout=inflater.inflate(R.layout.movie_item,null);
        }

        TextView movieName=(TextView) gridLayout.findViewById(R.id.movieName);
        ImageView movieImage=(ImageView)gridLayout.findViewById(R.id.trailerImage);

        //movieImage.setImageResource();
        Picasso.with(context).load(MainActivity.images.get(position)).into(movieImage);
        movieName.setText(MainActivity.moviesList.get(position).title);


        return gridLayout;
    }
}
