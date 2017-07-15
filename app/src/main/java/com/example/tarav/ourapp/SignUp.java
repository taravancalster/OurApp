package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void toProfile(View view){
        startActivity(new Intent(this, Profile.class));
    }

    public void signIn(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
