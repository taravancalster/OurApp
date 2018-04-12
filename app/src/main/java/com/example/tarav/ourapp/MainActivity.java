package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //TEST
        Button testBtn = (Button) findViewById(R.id.testButton);


		
		

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
                EditText email = (EditText) findViewById(R.id.etEmail);
                EditText password = (EditText) findViewById(R.id.etPW);

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
                    String readName = cursor.getString(1);
                    String readMail = cursor.getString(2);
                    String readPW = cursor.getString(3);

                    //if passwords match
                    if (readPW.equals(pass)) {
                        //go to profile
                        Intent toProfile = new Intent(MainActivity.this, Profile.class);
                        //Benutzername an Profile übergeben
                        toProfile.putExtra("username", readName);
                        startActivity(toProfile);
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

        	 testBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //change to ChangeProfile
                if (v.getId() == R.id.testButton) {
                    startActivity(new Intent(MainActivity.this, Profile.class));
                }
            }
        });



    }
	
	


}
