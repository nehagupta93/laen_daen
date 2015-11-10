package com.hostoi.laendaen.laen_daen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class LaendaenAdapter extends ArrayAdapter<String>{

    public LaendaenAdapter(Context context, String[] options) {
        super(context, R.layout.laendaen_options, options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(getContext());
        View customView = inf.inflate(R.layout.laendaen_options, parent, false);

        String singleOption = getItem(position);
        TextView laendarIdValue = (TextView) customView.findViewById(R.id.laendarIdValue);
        TextView daendarIdValue = (TextView) customView.findViewById(R.id.daendarIdValue);
        TextView dateValue = (TextView) customView.findViewById(R.id.dateValue);
        TextView amountValue = (TextView) customView.findViewById(R.id.amountValue);
        TextView reasonValue = (TextView) customView.findViewById(R.id.reasonValue);

        LaenDaen laenDaen = new LaenDaen(Integer.parseInt(singleOption));
        laendarIdValue.setText(laenDaen.laendar);
        daendarIdValue.setText(laenDaen.daendar);
        dateValue.setText(laenDaen.date);
        amountValue.setText(laenDaen.amount+"");
        reasonValue.setText(laenDaen.reason);

        return customView;
    }
}
