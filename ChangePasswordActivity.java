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

public class ChangePasswordActivity extends AppCompatActivity {

    private static String oldPassword;
    private static String newPassword1;
    private static String newPassword2;

    UserLocalStore userLocalStore;

    private static final String CHANGEPASSSWORD_URL = "http://laendaen.esy.es/phpfiles/changePassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userLocalStore = new UserLocalStore(this);
    }

    public void changeButtonClicked(View view){
        EditText oldPasswordInput = (EditText) findViewById(R.id.oldPasswordInput);
        EditText newPasswordInput1 = (EditText) findViewById(R.id.newPasswordInput1);
        EditText newPasswordInput2 = (EditText) findViewById(R.id.newPasswordInput2);

        oldPassword = oldPasswordInput.getText().toString();
        newPassword1 = newPasswordInput1.getText().toString();
        newPassword2 = newPasswordInput2.getText().toString();

        if(!newPassword1.equals(newPassword2)){
            Toast.makeText(ChangePasswordActivity.this, "new password inputs different", Toast.LENGTH_LONG).show();
            return;
        }

        User user = userLocalStore.getLoggedInUser();
        if(!oldPassword.equals(user.password)){
            Toast.makeText(ChangePasswordActivity.this, "incorrect old password", Toast.LENGTH_LONG).show();
            return;
        }

        class ChangePassword extends AsyncTask<String, Void, String>{

            ProgressDialog loading;
            boolean success = false;

            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChangePasswordActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("userId", params[0]);
                data.put("newPassword", params[1]);

                String result = ruc.sendPostRequest(CHANGEPASSSWORD_URL, data);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (s.equals("Successfully changed password")){
                    success = true;
                }
            }
        }



        ChangePassword cp = new ChangePassword();
        cp.execute(user.userId, newPassword1);
        if (cp.success) {
            user.password = newPassword1;
            userLocalStore.storeUserData(user);
            startActivity(new Intent(this, AccountActivity.class));
        }
    }
}
