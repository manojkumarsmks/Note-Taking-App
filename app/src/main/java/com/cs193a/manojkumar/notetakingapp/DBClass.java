//  Note data class and Query for creating the new files
package com.cs193a.manojkumar.notetakingapp;

import android.provider.BaseColumns;

public class DBClass {

    private DBClass() {

    }

    public static class NoteTable implements BaseColumns {
        public static final String TABLE_NAME = "noteDatabaseTable";
        public static final String COLUMN_NOTE_HEADER = "mainHeader";
        public static final String COLUMN_NOTE_DETAILS = "detailsSubHeader";
        public static final String COLUMN_NOTE_DATE = "noteDate";

        public static final String CREATE_TABLE = "CREATE FINAL IF NOT EXISTS " + TABLE_NAME + " (" +
                _ID +" INTEGER PRIMARY KEY, " + COLUMN_NOTE_HEADER +" TEXT, "+
                COLUMN_NOTE_DETAILS +" TEXT, " + COLUMN_NOTE_DATE +" TEXT)";
    }
}
