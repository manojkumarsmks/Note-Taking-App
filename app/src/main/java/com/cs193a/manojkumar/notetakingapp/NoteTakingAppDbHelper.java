// Database helper class
package com.cs193a.manojkumar.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

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
    public long insertIntoDatabase(NoteTakingAppDbHelper noteTakingAppDbHelper, ContentValues contentValues) {
        SQLiteDatabase db = noteTakingAppDbHelper.getWritableDatabase();
        return db.insert(DBClass.NoteTable.TABLE_NAME, null, contentValues);
    }

    public Notes readSpecificColumn(NoteTakingAppDbHelper noteTakingAppDbHelper, long row) {
        // Get a readable database reference
        SQLiteDatabase db = noteTakingAppDbHelper.getReadableDatabase();

        String[] column = new String[] {_ID , DBClass.NoteTable.COLUMN_NOTE_HEADER, DBClass.NoteTable.COLUMN_NOTE_DETAILS, DBClass.NoteTable.COLUMN_NOTE_DATE};

        // Query for reading from the specific row
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBClass.NoteTable.TABLE_NAME + " WHERE _ID = ?", new String[]{String.valueOf(row)});

        if(cursor != null) {
            cursor.moveToFirst();

            // Get the main Header notes, sub header notes and date of the notes separately
            String mainHeaderNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_HEADER));
            String subHeaderNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_DETAILS));
            String dateNotes = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.NoteTable.COLUMN_NOTE_DATE));

            cursor.close();
            db.close();

            // Return the notes
            return new Notes(mainHeaderNotes, subHeaderNotes, dateNotes);
        }

        return null;
    }


    // Read the complete database
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
            allNotes.add(0,note);
        }

        cursor.close();
        db.close();
        // return the notes
        return allNotes;

    }

    public int deteleSpecificRow(NoteTakingAppDbHelper noteTakingAppDbHelper, int row) {
        // Get a readable database reference
        SQLiteDatabase db = noteTakingAppDbHelper.getReadableDatabase();

        if(db == null)
            return -1;

        return db.delete(DBClass.NoteTable.TABLE_NAME, _ID + "=" +row,null);
    }
}
