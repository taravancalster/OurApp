package com.example.tarav.ourapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by tarav on 15.07.2017.
 */



public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    //Konstruktor
    public DatabaseHelper(Context activity, String dbName){
        super(activity, dbName, null, 1);
        db = getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db){
        try{
            //Tabelle anlegen
            String sql = "CREATE TABLE user " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email VARCHAR(20) NOT NULL, " +
                    "password VARCHAR(10) NOT NULL) ";

            db.execSQL(sql);

        }catch{
            Log.e("carpelibrum", ex.getMessage());
        }
    }

    public void OnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //auf Versionswechsel reagieren
    }

    }