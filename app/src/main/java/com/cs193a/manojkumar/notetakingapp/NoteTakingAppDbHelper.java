// Database helper class
package com.cs193a.manojkumar.notetakingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteTakingAppDbHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERISON = 1;
    private static final String DATBASE_NAME= "NoteTakingApp.db";

    public NoteTakingAppDbHelper(Context context) {
        super(context,  DATBASE_NAME, null, DATABSE_VERISON);
        Log.d(MainActivity.TAG, "NoteTakingAppDbHelper constructor");
        getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the note taking  database in the app
        Log.d(MainActivity.TAG, "NoteTakingAppDbHelper creating a database");
        db.execSQL(DBClass.NoteTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MainActivity.TAG, "NoteTakingAppDbHelper upgrading the database");
        db.execSQL(DBClass.NoteTable.CREATE_TABLE);
        onCreate(db);
    }
}
