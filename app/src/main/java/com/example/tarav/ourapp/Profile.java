package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //define the buttons
        Button buttonPro = (Button)findViewById(R.id.button9);
        Button buttonC = (Button)findViewById(R.id.button5);
        Button buttonH = (Button)findViewById(R.id.button6);
        Button buttonS = (Button)findViewById(R.id.button7);
        Button buttonA = (Button)findViewById(R.id.button8);

        //set an OnClickListener to the buttons
        buttonPro.setOnClickListener(onClickListener);
        buttonC.setOnClickListener(onClickListener);
        buttonH.setOnClickListener(onClickListener);
        buttonS.setOnClickListener(onClickListener);
        buttonA.setOnClickListener(onClickListener);
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

           }

            //show healthy Challenge
            if (v.getId() == R.id.button5){

            }

            //show social Challenge
            if (v.getId() == R.id.button5){

            }

            //show adventure Challenge
            if (v.getId() == R.id.button5){

            }
        }
    };
}
