package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        String[] options = {user.userId+" (Account)", "Old Laen-Daen", "New Laen-Daen", "Search User"};
        ListAdapter adapter = new CustomAdapter(this, options);
        ListView optionsList = (ListView) findViewById(R.id.optionsList);

        optionsList.setAdapter(adapter);
        optionsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this, OldLaenDaenActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(MainActivity.this, NewLaenDaenActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, SearchUserActivity.class));
                        }
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!authenticate())
            startActivity(new Intent(this, LoginActivity.class));
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }
}
