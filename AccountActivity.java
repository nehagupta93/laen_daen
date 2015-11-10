package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;

    private String userId, emailId, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        userLocalStore = new UserLocalStore(this);

        TextView userIdText = (TextView) findViewById(R.id.userIdText);
        TextView emailIdText = (TextView) findViewById(R.id.emailIdText);
        TextView balanceValueText = (TextView) findViewById(R.id.balanceValueText);

        User user = userLocalStore.getLoggedInUser();

        userId = user.userId;
        emailId = user.emailId;
        balance = user.balance+"";

        balance = displayBalance(balance);

        userIdText.setText(userId);
        emailIdText.setText(emailId);
        balanceValueText.setText("Rs."+balance);
    }

    public void logOutClicked(View view){
        startActivity(new Intent(this, ConfirmLogoutActivity.class));
    }

    public void changePasswordClicked(View view){
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    public void homeButtonClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public String displayBalance(String balance){
        String temp = "";
        char c;
        int flag=0;
        for (int i=0; i<balance.length(); i++){
            c = balance.charAt(i);
            if (c=='.'){
                flag++;
                temp=temp+c;
                continue;
            }
            if (flag==0) {
                temp=temp+c;
                continue;
            }
            flag++;
            if(flag>3){
                break;
            }
            temp=temp+c;
        }
        return temp;
    }
}
