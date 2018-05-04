package com.cs193a.manojkumar.notetakingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class CustomDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Set the builder with new instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Initialize the inflate
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        // define the builder
        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteTakingAppDbHelper noteTakingAppDbHelper = new NoteTakingAppDbHelper(getContext());

                        // Testing writing data into the database
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_HEADER, "Header 1");
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_DETAILS, "Header 1, details are inseerted here");
                        contentValues.put(DBClass.NoteTable.COLUMN_NOTE_DATE, "Mar 9th");

                        // Calling the function to insert into the database
                        noteTakingAppDbHelper.insertIntoDatabase(noteTakingAppDbHelper, contentValues);

                    }
                });

        return builder.create();
    }
}

