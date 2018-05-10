package com.cs193a.manojkumar.notetakingapp;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView dot;
    FloatingActionButton floatingActionButton;

    NoteTakingAppDbHelper noteTakingAppDbHelper;
    public static  final String TAG = "LOG_DEBUG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.simple_list_view);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        // read the database when the Main activity opensup
        NoteTakingAppDbHelper noteTakingAppDbHelper = new NoteTakingAppDbHelper(getApplicationContext());
        List<Notes>allNotes =  noteTakingAppDbHelper.readFromDatabase(noteTakingAppDbHelper);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the custom alert dialog
                showContentDialog();
            }
        });

        //Custom customAdapter = new Custom(marvelMovies);
        Custom customAdapter = new Custom(allNotes);

        listView.setAdapter(customAdapter);
    }


    // Show the Dialog box for the new Note addition
    private void showContentDialog() {
        // Set the builder with new instance
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Initialize the inflate
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
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
                        NoteTakingAppDbHelper noteTakingAppDbHelper = new NoteTakingAppDbHelper(MainActivity.this);

                        // Calling the function to insert into the database
                        noteTakingAppDbHelper.insertIntoDatabase(noteTakingAppDbHelper, contentValues);

                    }
                })
                .show();
    }

    /*Get todays data
     * return Date in "Day Month date" */
    private String getTodaysDate() {
        Date curretDate = Calendar.getInstance().getTime();
        String[] date = curretDate.toString().split(" ");
        return date[0] + "  " +date[1] + " " +date[2];
    }

    class Custom extends BaseAdapter {
        List<Notes> notesFromdb;
        public Custom(List<Notes> notesFromdb) {
            this.notesFromdb = notesFromdb;
        }
        @Override
        public int getCount() {
            return notesFromdb.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_list_item, null);
            TextView dot = (TextView)convertView.findViewById(R.id.dot);
            TextView heading = (TextView)convertView.findViewById(R.id.note_heading);
            TextView subHeading = (TextView) convertView.findViewById(R.id.note_subheading);
            //EditText notesDate = (EditText) convertView.findViewById(R.id.note_date);

            dot.setText(Html.fromHtml("\u2022"));
            heading.setText(notesFromdb.get(position).getHeaderNotes());
            subHeading.setText(notesFromdb.get(position).getSubHeaderNotes());
            //notesDate.setText(notesFromdb.get(position).getNotesDate());
            return convertView;
        }
    }
}
