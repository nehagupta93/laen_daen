package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfirmLogoutActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_logout);

        userLocalStore = new UserLocalStore(this);
    }

    public void logoutButtonClicked(View view){
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);

        startActivity(new Intent(this, LoginActivity.class));
    }

    public void backButtonClicked(View view){
        startActivity(new Intent(this, AccountActivity.class));
    }

}
