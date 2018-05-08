package com.cs193a.manojkumar.notetakingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CustomDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Set the builder with new instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Initialize the inflate
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);

        //EditText subHeader = (EditText)view.findViewById(R.id.note_subheading);

        builder.setView(view);


        // define the builder
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Initialize the views in custom alert dialog
                        EditText main_header_note = (EditText)view.findViewById(R.id.main_notes);
                        EditText details_note = (EditText)view.findViewById(R.id.details_notes);

                        // Content values initalizer for the database
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_HEADER, main_header_note.getText().toString());
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_DETAILS, details_note.getText().toString());
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_DATE, getTodaysDate());
                        NoteTakingAppDbHelper noteTakingAppDbHelper = new NoteTakingAppDbHelper(getContext());

                        // Calling the function to insert into the database
                        noteTakingAppDbHelper.insertIntoDatabase(noteTakingAppDbHelper, contentValues);

                    }
                });

        return builder.create();
    }

    /*Get todays data
    * return Date in "Day Month date" */
    private String getTodaysDate() {
        Date curretDate = Calendar.getInstance().getTime();
        String[] date = curretDate.toString().split(" ");
        return date[0] + "  " +date[1] + " " +date[2];
    }
}

