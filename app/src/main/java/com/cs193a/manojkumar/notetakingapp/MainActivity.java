package com.cs193a.manojkumar.notetakingapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    TextView dot;
    Button add;

    NoteTakingAppDbHelper noteTakingAppDbHelper;
    public static  final String TAG = "LOG_DEBUG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.simple_list_view);
        editText = (EditText)findViewById(R.id.enter_new_data);
        add = (Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteTakingAppDbHelper = new NoteTakingAppDbHelper(getApplicationContext());
                newNoteTakingDialog();
            }
        });

        // Load the data from the list_view_content to a string array
        String[] marvelMovies = getResources().getStringArray(R.array.list_view_contect);

        //Custom customAdapter = new Custom(marvelMovies);
        Custom customAdapter = new Custom(marvelMovies);

        listView.setAdapter(customAdapter);
    }

    private void newNoteTakingDialog() {
        CustomDialog dialog = new CustomDialog();
        dialog.show(getSupportFragmentManager(), "New Note Taking Dialog" );

    }

    class Custom extends BaseAdapter {
        String[] marvelMovies;
        public Custom(String[] marvelMovies) {
            this.marvelMovies = marvelMovies;
        }
        @Override
        public int getCount() {
            return marvelMovies.length;
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
            TextView subHeading = (TextView)convertView.findViewById(R.id.note_subheading);

            dot.setText(Html.fromHtml("\u2022"));
            heading.setText(marvelMovies[position]);
            subHeading.setText(marvelMovies[position]);
            return convertView;
        }
    }
}
