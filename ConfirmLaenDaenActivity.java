package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ConfirmLaenDaenActivity extends AppCompatActivity {

    int index;
    private static final String CONFIRM_URL = "http://laendaen.esy.es/phpfiles/confirmLaenDaen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_laen_daen);

        Bundle data = getIntent().getExtras();
        index = data.getInt("index");
        TextView laendarValue = (TextView) findViewById(R.id.laendarValue);
        TextView daendarValue = (TextView) findViewById(R.id.daendarValue);
        TextView dateValue = (TextView) findViewById(R.id.dateValue);
        TextView amountValue = (TextView) findViewById(R.id.amountValue);
        TextView reasonValue = (TextView) findViewById(R.id.reasonValue);

        LaenDaen laenDaen = new LaenDaen(index);
        laendarValue.setText(laenDaen.laendar);
        daendarValue.setText(laenDaen.daendar);
        dateValue.setText(laenDaen.date);
        amountValue.setText("Rs."+laenDaen.amount);
        reasonValue.setText(laenDaen.reason);
    }

    public void confirmClicked(View view){

        class ConfirmLaenDaen extends AsyncTask<String, Void, String>{

            boolean success = false;
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ConfirmLaenDaenActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ConfirmLaenDaenActivity.this, s, Toast.LENGTH_LONG);
                if(s.equals("Laen-Daen Confirmed"))
                    success = true;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> input = new HashMap<String, String>();
                input.put("index", params[0]);

                String result = ruc.sendPostRequest(CONFIRM_URL, input);
                return result;
            }
        }

        ConfirmLaenDaen cld = new ConfirmLaenDaen();
        cld.execute(index+"");

        if (cld.success)
            startActivity(new Intent(this, NewLaenDaenActivity.class));
    }

    public void cancelClicked(View view){
        startActivity(new Intent(this, NewLaenDaenActivity.class));
    }
}