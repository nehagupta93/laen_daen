package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView userIdText = (TextView) findViewById(R.id.userIdText);
    TextView emailIdText = (TextView) findViewById(R.id.emailIdText);
    TextView balanceValue = (TextView) findViewById(R.id.balanceValue);
    User user, currentUser;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userLocalStore = new UserLocalStore(this);
        currentUser = userLocalStore.getLoggedInUser();

        Bundle data = getIntent().getExtras();
        String userId = data.getString("userId");
        String emailId = data.getString("emailId");

        if (userId.equals(null))
            user = new User(emailId);
        else
            user = new User(userId);

        userIdText.setText(user.userId);
        emailIdText.setText(user.emailId);
        balanceValue.setText("Rs."+user.balance);
    }

    public void lendButtonClicked(View view){
        Intent i = new Intent(this, LaenDaenInputActivity.class);
        i.putExtra("laendar", user.userId);
        i.putExtra("daendar", currentUser.userId);
        startActivity(i);
    }

    public void borrowButtonClicked(View view){
        Intent i = new Intent(this, LaenDaenInputActivity.class);
        i.putExtra("laendar", currentUser.userId);
        i.putExtra("daendar", user.userId);
        startActivity(i);
    }
}
