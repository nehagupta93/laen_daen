package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hostoi.laendaen.laen_daen.DateService.MyLocalBinder;

import java.util.HashMap;

public class LaenDaenInputActivity extends AppCompatActivity {

    private static final String ADDLAENDAEN_URL = "http://laendaen.esy.es/phpfiles/addLaenDaen.php";

    String laendar, daendar;
    DateService dateService;
    boolean isBound = false;
    EditText dateInput;
    UserLocalStore userLocalStore;
    User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laen_daen_input);

        Intent i = new Intent(this, DateService.class);
        bindService(i, bobsConnection, Context.BIND_AUTO_CREATE);

        Bundle data = getIntent().getExtras();
        laendar = data.getString("laendar");
        daendar = data.getString("daendar");

        TextView laendarValue = (TextView) findViewById(R.id.laendarValue);
        TextView daendarValue = (TextView) findViewById(R.id.daendarValue);

        dateInput = (EditText) findViewById(R.id.dateInput);
        String currentTime = dateService.getCurrentTime();
        dateInput.setText(currentTime);

        userLocalStore = new UserLocalStore(this);
        loggedInUser = userLocalStore.getLoggedInUser();
    }

    public void confirmButtonClicked(View view){

        EditText amountInput = (EditText) findViewById(R.id.amountInput);
        EditText reasonInput = (EditText) findViewById(R.id.reasonInput);

        final String date = dateInput.getText().toString();
        final String amount = amountInput.getText().toString();
        final String reason = reasonInput.getText().toString();

        boolean dateCheck = dateValid(date);
        boolean amountCheck = amountValid(amount);
        if (!(dateCheck&&amountCheck)){
            Toast.makeText(LaenDaenInputActivity.this, "Recheck date and amount format",Toast.LENGTH_LONG);
            return;
        }

        boolean lc=false, dc=false;
        if (laendar.equals(loggedInUser.userId)){
            lc=true;
        }else{
            dc=true;
        }

        class AddLaenDaen extends AsyncTask<String, Void, String>{

            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();
            boolean success = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LaenDaenInputActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if (s.equals("Laen-Daen Created"))
                    success = true;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> input = new HashMap<String, String>();
                input.put("laendar", params[0]);
                input.put("lc",params[1]);
                input.put("daendar", params[2]);
                input.put("dc", params[3]);
                input.put("date", params[4]);
                input.put("amount", params[5]);
                input.put("reason", params[6]);

                String result = ruc.sendPostRequest(ADDLAENDAEN_URL, input);
                return result;
            }
        }

        AddLaenDaen ald = new AddLaenDaen();
        ald.execute(laendar, lc+"", daendar, dc+"", date, amount, reason);
        if (ald.success)
            startActivity(new Intent(this, NewLaenDaenActivity.class));
    }

    private ServiceConnection bobsConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            dateService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    public boolean dateValid(String date){
        boolean validity = true;
        for(int i=0;i<date.length();i++){
            char c = date.charAt(i);
            boolean lvalidity=false;
            if(c=='-'||c=='.'||c=='/')
                lvalidity=true;
            if(i==2||i==5)
                validity=validity&lvalidity;
            else
                validity=validity&!lvalidity;
        }
        return validity;
    }

    public boolean amountValid(String amount){
        int pointCounter =0;
        for(int i=0;i<amount.length();i++){
            char c = amount.charAt(i);
            if (c=='.')
                pointCounter++;
        }
        if (pointCounter>1)
            return false;
        return true;
    }
}
