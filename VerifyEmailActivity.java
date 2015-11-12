package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.util.HashMap;

public class VerifyEmailActivity extends AppCompatActivity {

    private static String userId;
    private static String emailId;
    private static String password;
    private static float balance;
    private static int verificationCode;

    private static final String REGISTER_URL = "http://laendaen.esy.es/phpfiles/register.php";
    private static final String EMAIL_VERIFY_URL = "http://laendaen.esy.es/phpfiles/verifyEmail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        Bundle registrationData = getIntent().getExtras();
        userId = registrationData.getString("userId");
        emailId = registrationData.getString("emailId");
        password = registrationData.getString("password");
        balance = registrationData.getFloat("balance");
        verificationCode = registrationData.getInt("verificationCode");

        class VerifyEmail extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerifyEmailActivity.this, "Sending Verification Code through Email",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Verification Code sent to your Email",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> x = new HashMap<String, String>();
                x.put("code", params[0]);
                x.put("emailId", params[1]);

                String result = ruc.sendPostRequest(EMAIL_VERIFY_URL, x);

                return result;
            }
        }
        VerifyEmail ve = new VerifyEmail();
        ve.execute(verificationCode+"", emailId);
    }

    public void verifyButtonClicked(View view) {

        EditText codeInput = (EditText) findViewById(R.id.codeInput);
        String code = codeInput.getText().toString();
        if (code.length()==0){
            Toast.makeText(VerifyEmailActivity.this, "Please fill verification code", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isNumeric(code)){
            Toast.makeText(VerifyEmailActivity.this, "invalid code", Toast.LENGTH_LONG).show();
        }
        int inputCode = Integer.parseInt(code);

        if (verificationCode != inputCode) {
            Toast.makeText(VerifyEmailActivity.this, "Incorrect Code", Toast.LENGTH_LONG).show();
            return;
        }

        class RegisterUser extends AsyncTask<String, Void, String>{

            int success=0;
            ProgressDialog loading;

            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerifyEmailActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(s.equals("Successfully registered"))
                    this.success = 1;
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", params[0]);
                data.put("emailId", params[1]);
                data.put("password", params[2]);
                data.put("balance", params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL, data).toString();
                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(userId, emailId, password, balance+"");

        if (ru.success==1)
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, RegisterActivity.class));

    }

    public boolean isNumeric(String x){
        int count = 0;
        for(int i=0;i<x.length();i++){
            char c=x.charAt(i);
            if (c<48||c>57)
                count++;
        }
        if (count==0)
            return true;
        return false;
    }
}