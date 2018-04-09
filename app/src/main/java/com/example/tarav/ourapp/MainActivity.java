package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import db.DbHelper;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bLogin = (Button) findViewById(R.id.bLogin);
        TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        /**
         * go to SignUp
         */
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });


        /**
         * check if user exists
         * and if password is right
         * and go to Profile
         */
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbh = new DbHelper(getApplicationContext());

                //get input fields
                EditText email = (EditText) findViewById(R.id.etMail);
                EditText password = (EditText) findViewById(R.id.etPassword);

                //hole die eingegebene email
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                //definiere was du holen willst
                String sql = "SELECT * FROM user WHERE email = ?";

                //mache db nutzbar
                SQLiteDatabase db = dbh.getWritableDatabase();

                //uebergebe sql mit variable mail als parameter
                Cursor cursor = db.rawQuery(sql, new String[]{mail});

                //gehe zum Anfang des Ergebnisses
                //cursor.moveToFirst();

                if(cursor != null){
                    cursor.moveToFirst();

                    //speichere email und pw in variable
                    String readMail = cursor.getString(2);
                    String readPW = cursor.getString(3);

                    //if passwords match
                    if (readPW.equals(pass)) {
                        //go to profile
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    } else {
                        Toast toastWrong = Toast.makeText(MainActivity.this, "Falsches Passwort oder falsche Email!", Toast.LENGTH_SHORT);
                        toastWrong.show();
                    }
                }

                //beende cursor
                cursor.close();
                db.close();

            }
        });

    }


}


                /*

                //mail aus db holen und in var speichern
                SQLiteDatabase dbRead = dbh.getReadableDatabase();



                String suchStr = "email = '" + email.getText().toString() + "'";
                String[] spalten = new String[] {"username", "email", "pw"};

                //suchanfrage an db geben
                Cursor cursor = dbRead.query("user", spalten, suchStr, null, null, null, null);
                //ob es einen eintrag gibt
                int anzahl = cursor.getCount();

                //daten aus eintrag holen und in var speichern
                long id;
                String uname;
                String pwReal = "";

                cursor.moveToFirst();
                for(int i = 0; i < anzahl; i++){
                    id = cursor.getLong(0);
                    uname = cursor.getString(1);
                    pwReal = cursor.getString(3);
                }
                cursor.close();


                //iff all fields are filled
                if((email.getText().toString() != null) && (password.getText().toString() != null)) {
                    Toast.makeText(MainActivity.this, "felder voll", Toast.LENGTH_SHORT);
                    //if email exists and the pws match
                    if ((anzahl > 0) && ((password.getText().toString()).equals(pwReal))) {
                        Toast.makeText(MainActivity.this, "email exisitert und pw passen!", Toast.LENGTH_SHORT);
                        //give var with uname an profile weiter

                        //go to profile
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    }
                }
                */