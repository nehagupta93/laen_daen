package com.hostoi.laendaen.laen_daen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText newEmailId;
    private EditText newUserId;
    private EditText newPassword;
    private EditText reEnteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newEmailId = (EditText) findViewById(R.id.newEmailId);
        newUserId = (EditText) findViewById(R.id.newUserId);
        newPassword = (EditText) findViewById(R.id.newPassword);
        reEnteredPassword = (EditText) findViewById(R.id.reEnteredPassword);



    }

    public void confirmButtonClicked(View view){

        String emailId, userId, password, repeatedPassword; float balance;
        emailId = newEmailId.getText().toString().trim().toLowerCase();
        userId = newUserId.getText().toString().trim().toLowerCase();
        password = newPassword.getText().toString().trim().toLowerCase();
        repeatedPassword = reEnteredPassword.getText().toString().trim().toLowerCase();
        balance = 0;

        if(emailId.length()==0||userId.length()==0||password.length()==0||repeatedPassword.length()==0) {
            Toast.makeText(RegisterActivity.this, "Please fill all fields.", Toast.LENGTH_LONG).show();
            return;
        }

        if (userId.length()!=7||!isAlphanumeric(userId)) {
            Toast.makeText(RegisterActivity.this, "UserId Invalid", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(repeatedPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        int verificationCode;
        Random r = new Random();
        double randomFraction = r.nextDouble();
        verificationCode = (int)(randomFraction*10000);

        Intent i = new Intent(this, VerifyEmailActivity.class);

        i.putExtra("emailId", emailId);
        i.putExtra("userId", userId);
        i.putExtra("password", password);
        i.putExtra("balance", balance);
        i.putExtra("verificationCode", verificationCode);

        startActivity(i);

    }

    public boolean isAlphanumeric(String text){
        char ch;
        int p = 0;
        boolean validity = true;
        do {
            ch=text.charAt(p);
            if (((ch >= 65&&ch<=90)||(ch>=97&&ch<=122)||(ch>=48&&ch<=57))==false)
                validity = false;
            p++;
        } while (p<text.length());
        return validity;
    }

}
