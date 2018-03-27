package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Achievements extends AppCompatActivity {

    Button homeButton;
    ProgressBar progressbar;
    TextView progressText;

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

        //define the button
        progressText = (TextView) findViewById(R.id.textViewProgress);

        //set an OnClickListener to the buttons
        homeButton.setOnClickListener(onClickListener);

        updateProgressBar();
        updateProgressText();

    }


    /**
     * sets the progress of the progressbar to the number of challenges the user has completed
     */
    private void updateProgressBar() {
        //progressbar.setProgress(completedChallenges);
        progressbar.setProgress(2); //Test ob er den Wert auf 2 ändert
    }


    /**
     * converts the progress of the progressbar to a number and sets this to a string
     */
    private void updateProgressText(){
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


//muss die leeren oder Fragezeichen-Bilder nur durch Bilder aus der DB ersetzten
//Schonmal leere Images + TextViews ins Layout setzten
//ProgressBar oben (mit Text?)
//Scrollbar/ScrollView
