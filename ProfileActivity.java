package com.hostoi.laendaen.laen_daen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView userIdText = (TextView) findViewById(R.id.userIdText);
    TextView emailIdText = (TextView) findViewById(R.id.emailIdText);
    TextView balanceValue = (TextView) findViewById(R.id.balanceValue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle data = getIntent().getExtras();
        String userId = data.getString("userId");
        String emailId = data.getString("emailId");
    }
}
