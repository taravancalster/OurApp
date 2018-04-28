package com.example.tarav.ourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Challenge extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 0;
    Button buttonProof, buttonComplete, homeButton;
    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //define the Buttons
            buttonProof = (Button) findViewById(R.id.proofPicButton);
            buttonComplete = (Button) findViewById(R.id.button16);
            homeButton = (Button) findViewById(R.id.homeButtonChallenge);
        //define TextViews
            challengeCategory = (TextView) findViewById(R.id.textView11);
            challengeTitle = (TextView) findViewById(R.id.textView12);
            challengeDescription = (TextView) findViewById(R.id.textView13);
        //define ImageView
            challengePicture = (ImageView) findViewById(R.id.imageView5);
        //set an OnClickListener to the buttons
            buttonProof.setOnClickListener(onClickListener);
            buttonComplete.setOnClickListener(onClickListener);
            homeButton.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

                //TAKE A PROOF PICTURE-BUTTON
                    if (v.getId() == R.id.proofPicButton) {
                        //Invokes the camera using an Intent
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent,CAMERA_REQUEST);
                    }

                 //CHALLENGE COMPLETED-BUTTON
                    if (v.getId() == R.id.button16) {
                        //aks user again if the challenge was completed
                        //mark ChallengesTable as done
                        //put it in Achievements
                        //change to Profile --> empty cross
                        startActivity(new Intent(Challenge.this, Profile.class));
                    }

                //HOME-BUTTON
                    if (v.getId() == R.id.homeButtonChallenge) {
                        startActivity(new Intent(Challenge.this, Profile.class));
                    }
        } ;

    };

}