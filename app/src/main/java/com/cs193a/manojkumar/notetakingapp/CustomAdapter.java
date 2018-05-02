package com.cs193a.manojkumar.notetakingapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private String[] testingString;
    private LayoutInflater layoutInflater;

    public CustomAdapter(Context context, String[] data, LayoutInflater layoutInflater) {
        this.context = context;
        this.testingString = data;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return testingString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        layoutInflater = ((Activity)context).getLayoutInflater();
        if(convertView != null) {
            v = layoutInflater.inflate(R.layout.single_list_item, null);

        }

        TextView dot = (TextView)v.findViewById(R.id.dot);
        TextView heading = (TextView)v.findViewById(R.id.test);
        TextView subHeading = (TextView)v.findViewById(R.id.test2);

        dot.setText(Html.fromHtml("\u2022"));
        heading.setText(testingString[position]);
        subHeading.setText(testingString[position]);
        return v;

    }
}
