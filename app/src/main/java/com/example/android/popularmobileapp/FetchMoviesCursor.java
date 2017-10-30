package com.example.android.popularmobileapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmobileapp.ContentProvider.MoviesContract;

/**
 * Created by Eng_I on 8/9/2017.
 */

public class FetchMoviesCursor extends AsyncTask<Void, Void, Cursor>{
        ContentResolver contentResolver;

        FetchMoviesCursor(ContentResolver contentResolver){
            this.contentResolver=contentResolver;
        }
        @Override
        protected Cursor doInBackground(Void... params) {

            ContentResolver resolver = contentResolver;

            Cursor cursor = resolver.query(MoviesContract.MoviesEntry.CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            MainActivity.moviesData = cursor;
            MainActivity.updategridAdapterWithData();

    }

}
