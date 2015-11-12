package com.hostoi.laendaen.laen_daen;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by neha on 11/9/2015.
 */
public class LaenDaen {

    int index;
    String laendar, daendar, date, reason, timeStamp;
    boolean laendarConfirmation, daendarConfirmation;
    float amount;

    private static final String LAENDAENDETAILS_URL = "http://laendaen.esy.es/phpfiles/getLaenDaenDetails.php";

    public LaenDaen(int index){
        this.index = index;
        class GetLaenDaenDetails extends AsyncTask<String, Void, String>{
            String details = "";
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                details = s;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("index", params[0]);

                String result = ruc.sendPostRequest(LAENDAENDETAILS_URL, data);
                return result;
            }
        }

        GetLaenDaenDetails gl = new GetLaenDaenDetails();
        gl.execute(index+"");

        assignLaenDaenDetails(gl.details);
    }

    public void assignLaenDaenDetails(String details){
        int counter = 0;
        String data="";
        for (int i=0; i<details.length(); i++ ){
            char c = details.charAt(i);
            if (c==','){
                switch (counter){
                    case 0:
                        this.laendar = data;
                        break;
                    case 1:
                        this.laendarConfirmation = Boolean.parseBoolean(data);
                        break;
                    case 2:
                        this.daendar = data;
                        break;
                    case 3:
                        this.daendarConfirmation = Boolean.parseBoolean(data);
                        break;
                    case 4:
                        this.amount = Float.parseFloat(data);
                        break;
                    case 5:
                        this.date = data;
                        break;
                    case 6:
                        this.reason = data;
                        break;
                    case 7:
                        this.timeStamp = data;
                }
                data = "";
                counter++;
                continue;
            }
            data+=c;
        }
    }
}
