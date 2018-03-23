package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Challenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //define the buttons
        Button buttonProof = (Button)findViewById(R.id.button15);
        Button buttonComplete = (Button)findViewById(R.id.button16);

        //set an OnClickListener to the buttons
        buttonProof.setOnClickListener(onClickListener);
        buttonComplete.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button15){
                //go to gallery
                //save the picture in the right ChallengeTable
            }

            else if(v.getId() == R.id.button16){
                //mark ChallengeTable as done
                //put it in Achievements
                //change to Profile --> empty cross
                startActivity(new Intent(Challenge.this, Profile.class));
            }
        }
    };
}
