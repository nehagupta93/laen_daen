package com.hostoi.laendaen.laen_daen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by neha on 10/29/2015.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, String[] options) {
        super(context, R.layout.mainactivity_options, options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(getContext());
        View customView = inf.inflate(R.layout.mainactivity_options, parent, false);

        String singleOption = getItem(position);
        TextView optionName = (TextView) customView.findViewById(R.id.optionName);
        ImageView optionImage = (ImageView) customView.findViewById(R.id.optionImage);

        optionName.setText(singleOption);
        switch (position) {
            case 0:
                optionImage.setImageResource(R.drawable.account);
                break;
            case 1:
                optionImage.setImageResource(R.drawable.default_icon);
                break;
            case 2:
                optionImage.setImageResource(R.drawable.add);
                break;
            case 3:
                optionImage.setImageResource(R.drawable.search);
        }
        return customView;
    }
}
