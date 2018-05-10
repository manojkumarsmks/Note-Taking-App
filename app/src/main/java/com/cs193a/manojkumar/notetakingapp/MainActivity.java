package com.cs193a.manojkumar.notetakingapp;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView dot;
    FloatingActionButton floatingActionButton;
    public static Custom customAdapter;
    private List<Notes> allNotes;
    NoteTakingAppDbHelper noteTakingAppDbHelper;
    public static  final String TAG = "LOG_DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.simple_list_view);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        // read the database when the Main activity opensup
        noteTakingAppDbHelper = new NoteTakingAppDbHelper(getApplicationContext());
        allNotes =  noteTakingAppDbHelper.readFromDatabase(noteTakingAppDbHelper);

        //Custom customAdapter = new Custom(marvelMovies);
        customAdapter = new Custom(allNotes);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the custom alert dialog
                showContentDialog(customAdapter);
            }
        });
        listView.setAdapter(customAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(final ContextMenu contextMenu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, v, menuInfo );
        if(v.getId() == R.id.simple_list_view) {
            getMenuInflater().inflate(R.menu.options, contextMenu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        Log.d(TAG, String.valueOf(index));
        switch (item.getItemId()) {
            case R.id.delete:
                noteTakingAppDbHelper.deteleSpecificRow(noteTakingAppDbHelper,info.position);
                allNotes.remove(info.position);
                customAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Deleted your notes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Toast.makeText(getApplicationContext(), "Edit your post", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    // Show the Dialog box for the new Note addition
    public void showContentDialog(final Custom customAdapter) {

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
                        long newId = noteTakingAppDbHelper.insertIntoDatabase(noteTakingAppDbHelper, contentValues);

                        // Get the notes added newly
                        Notes notes = noteTakingAppDbHelper.readSpecificColumn(noteTakingAppDbHelper, newId);

                        if(notes != null) {
                            // Add the new elements to the list 0th position
                            allNotes.add(0, notes);

                            // Notify the adapter to update
                            customAdapter.notifyDataSetChanged();
                        }

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
            TextView notesDate = (TextView) convertView.findViewById(R.id.note_date);

            dot.setText(Html.fromHtml("\u2022"));
            heading.setText(notesFromdb.get(position).getHeaderNotes());
            subHeading.setText(notesFromdb.get(position).getSubHeaderNotes());
            notesDate.setText(notesFromdb.get(position).getNotesDate());
            return convertView;
        }
    }
}
