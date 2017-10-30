package com.example.android.popularmobileapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Eng_I on 8/3/2017.
 */

public class FetchMovieTrailer extends AsyncTask<String, Void, String>{

    public Context currentContext;
    int position;


    public FetchMovieTrailer(Context context, int position){
        currentContext =context;
        this.position=position;
    }

    protected String doInBackground(String... URLS) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(URLS[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //update UI, processing Data
        super.onPostExecute(result);

        if (MainActivity.connectionEnabled) {

            try {
                JSONObject trailersObject = new JSONObject(result);
                JSONArray trailersArray = trailersObject.getJSONArray("results");
                final int numberOfTrailers = trailersArray.length();

                for (int i = 0; i < numberOfTrailers; i++) {
                    JSONObject trailer = trailersArray.getJSONObject(i);
                    String trailerForMovie;

                     trailerForMovie= trailer.getString("key");
                    MainActivity.trailersList.add(trailerForMovie);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
            }

            if (MainActivity.trailersList!= null){
                ArrayAdapter trailersArrayAdapter= new ArrayAdapter<String>(currentContext, R.layout.trailer_item,MovieActivity.trailerText);
                MovieActivity.trailerList.setAdapter(trailersArrayAdapter);
                trailersArrayAdapter.notifyDataSetChanged();

                for(int i=0;i<MainActivity.trailersList.size();i++){
                    MovieActivity.trailerText.add("Play Trailer  "+(i+1));
                }
            }

        }
        else{
            //network is not working
            Toast.makeText(currentContext, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }

}
