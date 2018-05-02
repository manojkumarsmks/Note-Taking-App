// Database helper class
package com.cs193a.manojkumar.notetakingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteTakingAppDbHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERISON = 1;
    private static final String DATBASE_NAME= "NoteTakingApp.db";

    public NoteTakingAppDbHelper(Context context, String name) {
        super(context, name, null, DATABSE_VERISON);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the note taking  database in the app
        db.execSQL(DBClass.NoteTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBClass.NoteTable.CREATE_TABLE);
        onCreate(db);
    }
}
