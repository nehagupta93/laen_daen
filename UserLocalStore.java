package com.hostoi.laendaen.laen_daen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by neha on 11/3/2015.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
        }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("userId", user.userId);
        spEditor.putString("emailId", user.emailId);
        spEditor.putString("password", user.password);
        spEditor.putFloat("balance", user.balance);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String userId = userLocalDatabase.getString("userId", "");
        String emailId = userLocalDatabase.getString("emailId", "");
        String password = userLocalDatabase.getString("password", "");
        float balance = userLocalDatabase.getFloat("balance", 0);

        User storedUser = new User(userId, emailId, password, balance);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false)){
            return true;
        }
        return false;
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
    }

}
