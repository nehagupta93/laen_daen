package com.hostoi.laendaen.laen_daen;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by neha on 11/9/2015.
 */
public class LaenDaenArrays {

    LaenDaen laendars[], daendars[], laendarRequests[], laendarsRequested[], daendarRequests[], daendarsRequested[], laenDaenRequests[], laenDaensRequested[];
    int laendarCounter, daendarCounter, laendarRequestsCounter, laendarsRequestedCounter, daendarRequestsCounter, daendarsRequestedCounter;

    private static final String LAENDAENINDICES_URL = "http://31.170.165.112/laen_daen/fetchLaenDaen.php";

    public LaenDaen[] getLaendars(String userId){
        GetIndices gi = new GetIndices();
        gi.execute("laendars", userId);
        laendarCounter = getSize(gi.indices);
        int index[] = getIndices(gi.indices);
        laendars = new LaenDaen[laendarCounter];
        for(int i=0; i<laendarCounter; i++)
            laendars[i] = new LaenDaen(index[i]);
        return laendars;
    }

    public LaenDaen[] getDaendars(String userId){
        GetIndices gi =  new GetIndices();
        gi.execute("daendars", userId);
        daendarCounter = getSize(gi.indices);
        int index[] = getIndices(gi.indices);
        daendars = new LaenDaen[daendarCounter];
        for (int i=0; i<daendarCounter; i++)
            daendars[i] = new LaenDaen(index[i]);
        return daendars;
    }

    public LaenDaen[] getLaenDaenRequests(String userId){
        GetIndices gi = new GetIndices();
        gi.execute("laendarRequests", userId);
        GetIndices gi2 = new GetIndices();
        gi2.execute("daendarRequests", userId);
        laendarRequestsCounter = getSize(gi.indices);
        daendarRequestsCounter = getSize(gi2.indices);
        int index1[] = getIndices(gi.indices);
        int index2[] = getIndices(gi2.indices);
        int index[] = new int[laendarRequestsCounter+daendarRequestsCounter];
        for (int i=0;i<laendarRequestsCounter;i++)
            index[i]=index1[i];
        for (int i=0;i<daendarRequestsCounter; i++)
            index[i+laendarRequestsCounter] = index2[i];
        index = sort(index);
        laenDaenRequests = new LaenDaen[laendarRequestsCounter+daendarRequestsCounter];
        for (int i=0; i<(laendarRequestsCounter+daendarRequestsCounter); i++)
            laenDaenRequests[i] = new LaenDaen(index[i]);
        return laenDaenRequests;
    }

    public LaenDaen[] getLaenDaensRequested(String userId){
        GetIndices gi1 = new GetIndices();
        gi1.execute("laendarsRequested", userId);
        GetIndices gi2 = new GetIndices();
        gi2.execute("daendarsRequested", userId);
        laendarsRequestedCounter = getSize(gi1.indices);
        daendarsRequestedCounter = getSize(gi2.indices);
        int index1[] = getIndices(gi1.indices);
        int index2[] = getIndices(gi2.indices);
        int index[] = new int[laendarsRequestedCounter+daendarsRequestedCounter];
        for (int i=0; i<laendarsRequestedCounter; i++)
            index[i] = index1[i];
        for (int i=0; i<daendarsRequestedCounter; i++)
            index[i+laendarsRequestedCounter] = index2[i];
        index = sort(index);
        laenDaensRequested = new LaenDaen[laendarsRequestedCounter+daendarsRequestedCounter];
        for (int i=0; i<(laendarsRequestedCounter+daendarsRequestedCounter); i++)
            laenDaensRequested[i] = new LaenDaen(index[i]);
        return laenDaensRequested;
    }

    class GetIndices extends AsyncTask<String, Void, String>{

        String indices = "";
        RegisterUserClass ruc = new RegisterUserClass();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            indices = s;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("tag", params[0]);
            data.put("userId", params[1]);

            String result = ruc.sendPostRequest(LAENDAENINDICES_URL, data);
            return result;
        }
    }

    public int[] getIndices(String indices){
        String item = "";
        int index[] = new int[getSize(indices)];
        int counter = 0;
        for (int i = 0; i<indices.length(); i++){
            char c = indices.charAt(i);
            if (c==','){
                index[counter] = Integer.parseInt(item);
                counter++;
                item="";
                continue;
            }
            item += c;
        }
        return index;
    }

    public int getSize(String indices){
        int counter = 0;
        for (int i = 0; i<indices.length(); i++){
            char c = indices.charAt(i);
            if (c==',')
                counter++;
        }
        return counter;
    }

    public int[] sort(int[] index){
        int l = index.length;
        for (int i=0; i<l-1; i++){
            for (int j=i+1; j<l; j++){
                if(index[i]<index[j]){
                    int temp = index[i];
                    index[i] = index[j];
                    index[j] = temp;
                }
            }
        }
        return index;
    }
}
