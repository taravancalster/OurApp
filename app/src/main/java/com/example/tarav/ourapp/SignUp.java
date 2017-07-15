package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText etMail = (EditText) findViewById(R.id.etMail);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);

        final Button bRegister = (Button) findViewById(R.id.bRegister);
    }

    /*public void toProfile(View view){
        startActivity(new Intent(this, Profile.class));
    }*/

    public void signIn(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
