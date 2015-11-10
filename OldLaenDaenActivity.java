package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class OldLaenDaenActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;
    LaenDaenArrays laenDaenArrays;

    LaenDaen laendars[], daendars[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_laen_daen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        laenDaenArrays = new LaenDaenArrays();
        laendars = laenDaenArrays.getLaendars(user.userId);
        daendars = laenDaenArrays.getDaendars(user.userId);
        int laendarCounter = laenDaenArrays.laendarCounter;
        int daendarCounter = laenDaenArrays.daendarCounter;
        String laendarOptions[] = new String[laendarCounter];
        String daendarOptions[] = new String[daendarCounter];

        for (int i=0; i<laendarCounter; i++)
            laendarOptions[i] = laendars[i].index+"";
        for (int i=0; i<daendarCounter; i++)
            daendarOptions[i] = daendars[i].index+"";
        ListAdapter laendarAdapter = new LaendaenAdapter(this, laendarOptions);
        ListAdapter daendarAdapter = new LaendaenAdapter(this, daendarOptions);

        ListView laendarList = (ListView) findViewById(R.id.laendarList);
        ListView daendarList = (ListView) findViewById(R.id.daendarList);

        laendarList.setAdapter(laendarAdapter);
        daendarList.setAdapter(daendarAdapter);
    }

    public void addButtonClicked(View view){
        startActivity(new Intent(this, NewLaenDaenActivity.class));
    }

    public void homeLinkClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
