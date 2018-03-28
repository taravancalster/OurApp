package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Achievements extends AppCompatActivity {

    Button homeButton;
    ProgressBar progressbar;
    TextView progressText, txtChallenge1, txtChallenge2, txtChallenge3, txtChallenge4, txtChallenge5,
            txtChallenge6, txtChallenge7, txtChallenge8, txtChallenge9, txtChallenge10, txtChallenge11, txtChallenge12;

    ImageView imgChallenge1, imgChallenge2, imgChallenge3, imgChallenge4, imgChallenge5, imgChallenge6,
            imgChallenge7, imgChallenge8, imgChallenge9, imgChallenge10, imgChallenge11, imgChallenge12;

     /*
    Von der Datenbank die abgeschlossenen Challenges holen
    int challengeCompleted = ???;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        //define the button
        homeButton = (Button) findViewById(R.id.homeButtonAchievements);

        //define progress bar
        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        //define text views
        progressText = (TextView) findViewById(R.id.textViewProgress);
        txtChallenge1 = (TextView) findViewById(R.id.textChallenge1);
        txtChallenge2 = (TextView) findViewById(R.id.textChallenge2);
        txtChallenge3 = (TextView) findViewById(R.id.textChallenge3);
        txtChallenge4 = (TextView) findViewById(R.id.textChallenge4);
        txtChallenge5 = (TextView) findViewById(R.id.textChallenge5);
        txtChallenge6 = (TextView) findViewById(R.id.textChallenge6);
        txtChallenge7 = (TextView) findViewById(R.id.textChallenge7);
        txtChallenge8 = (TextView) findViewById(R.id.textChallenge8);
        txtChallenge9 = (TextView) findViewById(R.id.textChallenge9);
        txtChallenge10 = (TextView) findViewById(R.id.textChallenge10);
        txtChallenge11 = (TextView) findViewById(R.id.textChallenge11);
        txtChallenge12 = (TextView) findViewById(R.id.textChallenge12);

        //define image views
        imgChallenge1 = (ImageView) findViewById(R.id.imageChallenge1);
        imgChallenge2 = (ImageView) findViewById(R.id.imageChallenge2);
        imgChallenge3 = (ImageView) findViewById(R.id.imageChallenge3);
        imgChallenge4 = (ImageView) findViewById(R.id.imageChallenge4);
        imgChallenge5 = (ImageView) findViewById(R.id.imageChallenge5);
        imgChallenge6 = (ImageView) findViewById(R.id.imageChallenge6);
        imgChallenge7 = (ImageView) findViewById(R.id.imageChallenge7);
        imgChallenge8 = (ImageView) findViewById(R.id.imageChallenge8);
        imgChallenge9 = (ImageView) findViewById(R.id.imageChallenge9);
        imgChallenge10 = (ImageView) findViewById(R.id.imageChallenge10);
        imgChallenge11 = (ImageView) findViewById(R.id.imageChallenge11);
        imgChallenge12 = (ImageView) findViewById(R.id.imageChallenge12);


        //set an OnClickListener to the buttons
        homeButton.setOnClickListener(onClickListener);

        updateProgressBar();
        updateProgressText();

    }


    /**
     * sets the progress of the progressbar to the number of challenges the user has completed
     */
    public void updateProgressBar() {
        //progressbar.setProgress(completedChallenges);
        progressbar.setProgress(2); //Test ob er den Wert auf 2 ändert
    }


    /**
     * converts the progress of the progressbar to a number and sets this to a string
     */
    public void updateProgressText(){
        //progressText.setText(completedChallenges.toString() + "/12");
        progressText.setText("2/12");   //Test ob er den Text zu 2/12 ändert
    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //change to Profile
            if (v.getId() == R.id.homeButtonAchievements) {
                startActivity(new Intent(Achievements.this, Profile.class));
            }

        };
    };


}


    //muss die leeren oder Fragezeichen-Bilder durch Bilder aus der DB ersetzten

