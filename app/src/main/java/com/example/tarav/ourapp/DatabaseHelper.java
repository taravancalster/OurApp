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

    private static final int DB_VERSION = 2;

    private static final String DB_NAME = "RegisterAndLogin.db";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PW = "Password";
    private static final String KEY_PW2 = "Password2";
    private static final String KEY_ID = "ID";

    SQLiteDatabase db;

    private static final String USER_TB_NAME = "Users";

    private static final String USER_TB_CREATE = "CREATE TABLE " + USER_TB_NAME +"(" + KEY_USERNAME + "TEXT," + KEY_EMAIL + "TEXT"
            + KEY_PW + "TEXT," + KEY_PW2 + "TEXT);";


    DatabaseHelper (Context context){
    super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TB_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + USER_TB_NAME);
        this.onCreate(db);

    }

    public void addUser(UserMemo user){
        SQLiteDatabase db = getWritableDatabase();

        //füge User hinzu
        ContentValues value = new ContentValues();

        //getting the count of all users to give the next id
        String query = "SELECT * FROM "+ USER_TB_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        value.put(KEY_ID, count);
        //zum richtigen einordnen in die Tabelle --> Schlüsselpaare bilden
        value.put(KEY_USERNAME, user.getUsername());
        value.put(KEY_EMAIL, user.getEmail());
        value.put(KEY_PW, user.getPassword());
        value.put(KEY_PW2, user.getPassword2());

        //Werte in Tabelle einfügen
        db.insert(USER_TB_NAME, null, value);
        cursor.close();
        db.close();

    }

    public String getUsername(String email){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + USER_TB_NAME + "WHERE " + KEY_EMAIL + " = " + email;
        Cursor cursor  = db.rawQuery(query, null);

        String username = "";

        username = cursor.getString(1);
        cursor.close();
        db.close();
        return username;
    }

    public String getPw(String email){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + USER_TB_NAME + "WHERE " + KEY_EMAIL + " = " + email;
        Cursor cursor  = db.rawQuery(query, null);

        String password = "";

        password = cursor.getString(3);
        cursor.close();
        db.close();
        return password;


    }

    /**
     * read in the db
     * get the row where the emails match
     * count the cursor --> if an email allready exists return true
     *
     * @param email
     * @param pw
     * @return
     */
    public boolean exists(String email, String pw){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TB_NAME + " WHERE " + KEY_EMAIL + " = " + email;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() > 0){
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

}