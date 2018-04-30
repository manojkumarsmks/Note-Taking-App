package com.cs193a.manojkumar.notetakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.simple_list_view);

        // Load the data from the list_view_content to a string array
        String[] marvelMovies = getResources().getStringArray(R.array.list_view_contect);

        // Setup an adapter
        ArrayAdapter adapter = new ArrayAdapter(
                // Context
                this,
                // Simple list of Items
               R.layout.single_list_item,

                //  The
                R.id.label,

                // Source for the list
                marvelMovies);

        listView.setAdapter(adapter);

    }
}
