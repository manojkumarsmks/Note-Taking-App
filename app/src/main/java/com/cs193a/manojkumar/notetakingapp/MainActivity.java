package com.cs193a.manojkumar.notetakingapp;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
                newNoteTakingDialog();
            }
        });

        //Custom customAdapter = new Custom(marvelMovies);
        Custom customAdapter = new Custom(allNotes);

        listView.setAdapter(customAdapter);
    }

    private void newNoteTakingDialog() {
        CustomDialog dialog = new CustomDialog();
        dialog.show(getSupportFragmentManager(), "New Note Taking Dialog" );
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
            EditText heading = (EditText)convertView.findViewById(R.id.note_heading);
            EditText subHeading = (EditText) convertView.findViewById(R.id.note_subheading);
            //EditText notesDate = (EditText) convertView.findViewById(R.id.note_date);

            dot.setText(Html.fromHtml("\u2022"));
            heading.setText(notesFromdb.get(position).getHeaderNotes());
            subHeading.setText(notesFromdb.get(position).getSubHeaderNotes());
            //notesDate.setText(notesFromdb.get(position).getNotesDate());
            return convertView;
        }
    }
}
