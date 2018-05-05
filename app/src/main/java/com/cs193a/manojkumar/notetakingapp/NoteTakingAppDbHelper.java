// Database helper class
package com.cs193a.manojkumar.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NoteTakingAppDbHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERISON = 1;
    private static final String DATBASE_NAME= "NoteTakingApp.db";
    private Context context;

    public NoteTakingAppDbHelper(Context context) {
        super(context,  DATBASE_NAME, null, DATABSE_VERISON);
        Log.d(MainActivity.TAG, "NoteTakingAppDbHelper constructor");
        this.context = context;
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

    // Insert a new row to the noteDatabaseTable table
    public void insertIntoDatabase(NoteTakingAppDbHelper noteTakingAppDbHelper, ContentValues contentValues) {
        SQLiteDatabase db = noteTakingAppDbHelper.getWritableDatabase();
        long newRowID = db.insert(DBClass.NoteTable.TABLE_NAME, null, contentValues);

        Log.d(MainActivity.TAG, "New Row ID is added "+newRowID);
    }


    public List<Notes> readFromDatabase(NoteTakingAppDbHelper noteTakingAppDbHelper){
        // Get a readable database reference
        SQLiteDatabase db = noteTakingAppDbHelper.getReadableDatabase();

        // List to get all the Notes
        List<Notes> allNotes = new ArrayList<>();

        // Create a query
        String query = "SELECT * FROM "+DBClass.NoteTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        // Move the cursor till you past the last entry
        while(cursor.moveToNext()) {
            // Get the main Header notes, sub header notes and date of the notes separately
            String mainHeaderNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_HEADER));
            String subHeaderNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_DETAILS));
            String dateNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_DATE));
            // create an instance of the notes
            Notes note = new Notes(mainHeaderNotes, subHeaderNotes, dateNotes);
            // add the instance to the list
            allNotes.add(note);
        }

        cursor.close();
        // return the notes
        return allNotes;

    }
}
