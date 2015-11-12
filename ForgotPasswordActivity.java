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
import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static String emailId;
    private static String newPassword;

    private static final String FORGOTPASSWORD_URL = "http://laendaen.esy.es/phpfiles/forgotPassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void buttonClicked(View view){
        EditText emailIdInput = (EditText) findViewById(R.id.emailIdInput);
        newPassword = generateNewpassword();
        emailId = emailIdInput.getText().toString();

        class ForgotPassword extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            boolean success = false;

            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ForgotPasswordActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (s.equals("check email for new password")) {
                    success = true;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("emailId", params[0]);
                data.put("newPassword", params[1]);

                String result = ruc.sendPostRequest(FORGOTPASSWORD_URL, data);
                return result;
            }
        }

        ForgotPassword fp = new ForgotPassword();
        fp.execute(emailId, newPassword);

        if (fp.success){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private String generateNewpassword() {
        long password;
        Random r = new Random();
        double randomFraction = r.nextDouble();
        password = (long)(randomFraction*1000000);
        String newPassword = password+"";
        return newPassword;
    }
}
