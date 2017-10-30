package com.example.android.popularmobileapp.ContentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Eng_I on 8/8/2017.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.example.android.popularmobileapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_TASKS = "movies";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();



        public static final String TABLE_NAME = "movies";


        public static final String COLUMN_MOVIES_ID = "movies_id";
        public static final String COLUMN_NAME= "name";


    }

}
