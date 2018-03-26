package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    Button buttonPro;
    Button buttonC;
    Button buttonH;
    Button buttonS;
    Button buttonA;

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);

        //define the buttons
        buttonPro = (Button)findViewById(R.id.button9);
        buttonC = (Button)findViewById(R.id.button5);
        buttonH = (Button)findViewById(R.id.button6);
        buttonS = (Button)findViewById(R.id.button7);
        buttonA = (Button)findViewById(R.id.button8);

        //define progress bar
        progressbar = (ProgressBar)findViewById(R.id.progressBar);

        //set an OnClickListener to the buttons
        buttonPro.setOnClickListener(onClickListener);
        buttonC.setOnClickListener(onClickListener);
        buttonH.setOnClickListener(onClickListener);
        buttonS.setOnClickListener(onClickListener);
        buttonA.setOnClickListener(onClickListener);

        //set an OnClickListener to the progress bar
        progressbar.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //change to ChangeProfile
            if(v.getId() == R.id.button9){
                startActivity(new Intent(Profile.this, ChangeProfile.class));
            }

            //show creative Challenge
           if (v.getId() == R.id.button5){
                startActivity(new Intent(Profile.this, Challenge.class));
           }

            //show healthy Challenge
            if (v.getId() == R.id.button5){
                startActivity(new Intent(Profile.this, Challenge.class));
            }

            //show social Challenge
            if (v.getId() == R.id.button5){
                startActivity(new Intent(Profile.this, Challenge.class));
            }

            //show adventure Challenge
            if (v.getId() == R.id.button5){
                startActivity(new Intent(Profile.this, Challenge.class));
            }

            //if the user clicks on the progress bar he will be taken to the achievements layout
            if (v.getId() == R.id.progressBar){
                startActivity(new Intent(Profile.this, Achievements.class));
            }
        }
    };


    //Increment the progress bar, when a challenge has been completed
    /* private updateProgress(){

    }*/
}
