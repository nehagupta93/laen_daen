package com.hostoi.laendaen.laen_daen;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Binder;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateService extends Service{

    private final IBinder timeBinder = new MyLocalBinder();

    public DateService(){
    }

    @Override
    public IBinder onBind(Intent intent) {
        return timeBinder;
    }

    public String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return df.format(new Date());
    }

    public class MyLocalBinder extends Binder{
        DateService getService(){
            return DateService.this;
        }
    }
}
