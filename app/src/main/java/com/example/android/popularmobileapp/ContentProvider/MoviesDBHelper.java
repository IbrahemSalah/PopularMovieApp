package com.example.android.popularmobileapp.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmobileapp.ContentProvider.MoviesContract.MoviesEntry;

/**
 * Created by Eng_I on 8/8/2017.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "moviesDb.db";

    //starting version for me
    private static final int VERSION = 1;


    MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_MOVIES_ID    + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
