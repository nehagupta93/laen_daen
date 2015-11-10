package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    UserLocalStore userLocalStore;

    private static final String LOGIN_URL = "http://laendaen.esy.es/phpfiles/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLocalStore = new UserLocalStore(this);
    }

    public void loginButtonClicked(View view){
        EditText IdInput = (EditText) findViewById(R.id.idInput);
        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);

        String userId = IdInput.getText().toString();
        String password = passwordInput.getText().toString();
        String emailId;

        if (userId.length()==7){
            emailId = null;
        }
        else{
            emailId = userId;
            userId = null;
        }

        class Login extends AsyncTask<String, Void, String>{

            String exists="false";
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                exists = s;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", params[0]);
                data.put("emailId", params[1]);
                data.put("password", params[2]);

                String result = ruc.sendPostRequest(LOGIN_URL, data);
                return result;
            }
        }

        Login l = new Login();
        l.execute(userId, emailId, password);

        if (l.exists.equals("false")) {
            Toast.makeText(LoginActivity.this, "Incorrect ID or password", Toast.LENGTH_LONG);
            return;
        }

        User user;

        if (userId==null)
            user = new User(emailId);
        else
            user = new User(userId);

        userLocalStore.storeUserData(user);
        userLocalStore.setUserLoggedIn(true);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    public void registerButtonClicked(View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void forgotPasswordClicked(View view){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

}
