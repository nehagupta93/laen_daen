package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NewLaenDaenActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;
    LaenDaenArrays laenDaenArrays;

    LaenDaen requests[], requested[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_laen_daen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        laenDaenArrays = new LaenDaenArrays();
        requests = laenDaenArrays.getLaenDaenRequests(user.userId);
        requested = laenDaenArrays.getLaenDaensRequested(user.userId);
        int requestsCounter = laenDaenArrays.laendarRequestsCounter + laenDaenArrays.daendarRequestsCounter;
        int requestedCounter = laenDaenArrays.laendarsRequestedCounter + laenDaenArrays.daendarsRequestedCounter;
        String requestsOptions[]  = new String[requestsCounter];
        String requestedOptions[] = new String[requestedCounter];

        for (int i=0; i<requestsCounter; i++)
            requestsOptions[i] = requests[i].index+"";
        for (int i=0; i<requestedCounter; i++)
            requestedOptions[i] = requested[i].index+"";

        ListAdapter requestsAdapter = new LaendaenAdapter(this, requestsOptions);
        ListAdapter requestedAdapter = new LaendaenAdapter(this, requestedOptions);

        ListView requestsList = (ListView) findViewById(R.id.requestsList);
        ListView requestedList = (ListView) findViewById(R.id.requestedList);

        requestsList.setAdapter(requestsAdapter);
        requestedList.setAdapter(requestedAdapter);

        requestsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int itemIndex = requests[position].index;
                        Intent i = new Intent(NewLaenDaenActivity.this, ConfirmLaenDaenActivity.class);
                        i.putExtra("index", itemIndex);
                        startActivity(i);
                    }
                }
        );
    }

    public void homeLinkClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void createButtonClicked(View view){
        startActivity(new Intent(this, SearchUserActivity.class));
    }
}
