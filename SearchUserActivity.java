package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class SearchUserActivity extends AppCompatActivity {

    private static final String SEARCH_URL = "http://laendaen.esy.es/phpfiles/searchUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    public void homeLinkClicked(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void searchButtonClicked(View view){
        String userId, emailId = null;
        EditText idInput = (EditText) findViewById(R.id.idInput);
        userId = idInput.getText().toString();
        if (userId.length()!=7){
            emailId = userId;
            userId = null;
        }
        class SearchUser extends AsyncTask<String, Void, String>{
            String exists = "false";
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchUserActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SearchUserActivity.this, s, Toast.LENGTH_LONG).show();
                if (s.equals("User found"))
                    exists = "true";
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", params[0]);
                data.put("emailId", params[1]);

                String result = ruc.sendPostRequest(SEARCH_URL, data);
                return result;
            }
        }

        SearchUser su = new SearchUser();
        su.execute(userId, emailId);

        if (su.exists.equals("false"))
            return;

        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("emailId", emailId);
        startActivity(i);
    }
}
