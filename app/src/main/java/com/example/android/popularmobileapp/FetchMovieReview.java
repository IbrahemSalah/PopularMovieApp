package com.example.android.popularmobileapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eng_I on 8/3/2017.
 */

public class FetchMovieReview extends AsyncTask<String, Void, String>{

    public Context currentContext;
    int position;


    public FetchMovieReview(Context context,int position){
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
                JSONObject reviewsObject = new JSONObject(result);
                JSONArray reviewsArray = reviewsObject.getJSONArray("results");
                final int numberOfTrailers = reviewsArray.length();

                for (int i = 0; i < numberOfTrailers; i++) {
                    JSONObject review = reviewsArray.getJSONObject(i);
                    String reviewForMovie;

                    reviewForMovie= review.getString("content");
                    MainActivity.reviewsList.add(reviewForMovie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (MovieActivity.reviewsList!= null) {
                ArrayAdapter reviewsArrayAdapter= new ArrayAdapter<String>(currentContext, R.layout.review_item,MainActivity.reviewsList);
                MovieActivity.reviewsList.setAdapter(reviewsArrayAdapter);
                reviewsArrayAdapter.notifyDataSetChanged();
            }

        }
        else{
            //network is not working
            Toast.makeText(currentContext, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }

}
