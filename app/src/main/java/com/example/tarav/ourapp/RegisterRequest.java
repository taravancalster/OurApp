package com.example.tarav.ourapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarav on 15.07.2017.
 */


public class RegisterRequest extends SQLiteOpenHelper {

    private static final String LOG_TAG = RegisterRequest.class.getSimpleName();

    public RegisterRequest (Context context){
        super(context, "USER", null, 1);
        Log.d(LOG_TAG, "RegisterRequest hat die DB: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

/*
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://taravancalster.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String email, String password, String password2, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
        params.put("password2", password2);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
*/