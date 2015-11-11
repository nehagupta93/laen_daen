package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;

import java.util.HashMap;

/**
 * Created by neha on 10/30/2015.
 */
public class User {

    String userId, emailId, password;
    float balance;

    private static final String GETUSERDATA_URL = "http://31.170.165.112/laen_daen/getUserData.php";

    public User(String userId, String emailId, String password, float balance){
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.balance = balance;
    }

    public User(String id){
        if (id.length()==7){
            userId = id;
            emailId = null;
        }else {
            emailId = id;
            userId = null;
        }

        balance = 0;
        // TODO: 10/30/2015 update balance, emailId and userId from d/b

        class GetData extends AsyncTask<String, Void, String>{

            String data = "";
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                data = s;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> input = new HashMap<String, String>();
                input.put("tag", params[0]);
                input.put("id", params[1]);

                String result = ruc.sendPostRequest(GETUSERDATA_URL, input);

                return result;
            }
        }

        GetData gd = new GetData();

        if (userId == null){
            gd.execute("userId", emailId);
            userId = gd.data;
        }else{
            gd.execute("emailId", userId);
            emailId = gd.data;
        }

        gd.execute("password", userId);
        password = gd.data;

        gd.execute("balance", userId);
        balance = Float.parseFloat(gd.data);
    }

}