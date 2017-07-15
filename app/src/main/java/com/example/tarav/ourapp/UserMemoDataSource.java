package com.example.tarav.ourapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by tarav on 15.07.2017.
 */

public class UserMemoDataSource {

    private static final String LOG_TAG = UserMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private RegisterRequest rr;

    public UserMemoDataSource (Context context){
        Log.d(LOG_TAG, "Unsere Datascource erzeugt jetzt den rr");
        rr = new RegisterRequest(context);
    }

}
