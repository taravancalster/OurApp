package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public DatabaseHelper dbh = new DatabaseHelper(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText email = (EditText) findViewById(R.id.etMail);
        final EditText password = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

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

                //if user exists
                if(dbh.exists(email.getText().toString(), password.getText().toString())) {
                    //get the right Password
                    String pw = dbh.getPw(email.getText().toString());
                        //if the passwords match
                        if (pw.equals(password.getText().toString())) {
                            //get the username
                            dbh.getUsername(email.getText().toString());
                            //save username in file

                            //go to Profile
                            startActivity(new Intent(MainActivity.this, Profile.class));
                        }
                }
            }
        });
    }
}
