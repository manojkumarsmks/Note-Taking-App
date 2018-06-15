package com.skills.manojkumar.AlphabetScroll;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    LinearLayout listView;
    public static  final String TAG = "LOG_DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Country> countries = new ArrayList<>();

        recyclerview = (RecyclerView) findViewById(R.id.simple_recycle_view);
        listView = (LinearLayout) findViewById(R.id.alphabet_list);

        String[] alphabet = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");

        countries.add(new Country("Afghanistan"));
        countries.add(new Country("Albania"));
        countries.add(new Country("Argentina"));
        countries.add(new Country("Australia"));
        countries.add(new Country("Austria"));
        countries.add(new Country("Bahamas"));
        countries.add(new Country("Bangladesh"));
        countries.add(new Country("Belgium"));
        countries.add(new Country("Bermuda"));
        countries.add(new Country("Brazil"));
        countries.add(new Country("Canada"));
        countries.add(new Country("Cambodia"));
        countries.add(new Country("Chile"));
        countries.add(new Country("Chad"));
        countries.add(new Country("Denmark"));
        countries.add(new Country("Egypy"));
        countries.add(new Country("Ethiopia"));
        countries.add(new Country("Fiji"));
        countries.add(new Country("France"));
        countries.add(new Country("Georgia"));
        countries.add(new Country("Germany"));
        countries.add(new Country("Hungary"));
        countries.add(new Country("Iran"));
        countries.add(new Country("Iraq"));
        countries.add(new Country("India"));
        countries.add(new Country("Jordan"));
        countries.add(new Country("Japan"));
        countries.add(new Country("Lebanon"));
        countries.add(new Country("Nepal"));
        countries.add(new Country("Netherland"));
        countries.add(new Country("New Zealand"));
        countries.add(new Country("Norway"));
        countries.add(new Country("Pakistan"));
        countries.add(new Country("United States"));
        countries.add(new Country("Malta"));
        countries.add(new Country("Mongolia"));
        countries.add(new Country("Morocco"));

        ArrayList<String> alphabetList = new ArrayList<>();
        alphabetList.addAll(Arrays.asList(alphabet));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.alphabets_list_item, alphabetList);


        for (int i=0; i<alphabet.length; i++) {
            String country = alphabet[i];
            final TextView textView = new TextView(this);
            textView.setText(country);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            textView.setTextSize(12);
            textView.setPadding(2,2,2,2);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "This is the clicked element "+textView.getText().toString());
                }
            });
            //listView.setPadding(10,0,10,0);
            listView.addView(textView,i);
        }


        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        recyclerview.setAdapter(new CustomAdapter(countries));

        recyclerview.setItemAnimator(new DefaultItemAnimator());


    }
}
