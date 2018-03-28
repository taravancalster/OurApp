package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChallengeNew extends AppCompatActivity {

    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;
    Button tryButton, notNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_new);

        //define TextViews
        challengeCategory = (TextView) findViewById(R.id.textView17);
        challengeTitle = (TextView) findViewById(R.id.textView18);
        challengeDescription = (TextView) findViewById(R.id.textView19);
        //define ImageView
        challengePicture = (ImageView) findViewById(R.id.imageView4);
        //define Buttons
        tryButton = (Button) findViewById(R.id.button20);
        notNowButton = (Button) findViewById(R.id.button19);

        //add OnClick events
        tryButton.setOnClickListener(onClickListener);
        notNowButton.setOnClickListener(onClickListener);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //if tryButton is clicked: change to Challenge
            if(v.getId() == R.id.button20){
                startActivity(new Intent(ChallengeNew.this, Challenge.class));
                //set new challenge in profile
            }

            //if notNotButton is clicked: change to Profile
            if (v.getId() == R.id.button19){
                startActivity(new Intent(ChallengeNew.this, Profile.class));
            }
        }
    };
}
