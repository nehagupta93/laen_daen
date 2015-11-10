package com.hostoi.laendaen.laen_daen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LaenDaenInputActivity extends AppCompatActivity {

    String laendar, daendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laen_daen_input);

        Bundle data = getIntent().getExtras();
        laendar = data.getString("laendar");
        daendar = data.getString("daendar");

        TextView laendarValue = (TextView) findViewById(R.id.laendarValue);
        TextView daendarValue = (TextView) findViewById(R.id.daendarValue);
    }

    public void confirmButtonClicked(View view){

    }
}
