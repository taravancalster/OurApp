package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    Button buttonPro, buttonC, buttonH, buttonS, buttonA;
    ProgressBar progressbar;
    TextView progressText;
    //EditText etUsername;
    TextView etUsername;
    ImageView userPicture;

    /*
    Von der Datenbank die abgeschlossenen Challenges holen
    int challengeCompleted = ???;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //etUsername = (EditText) findViewById(R.id.etUsername);
        etUsername = (TextView) findViewById(R.id.etUsername);


        //define the buttons
        buttonPro = (Button)findViewById(R.id.button9);
        buttonC = (Button)findViewById(R.id.button5);
        buttonH = (Button)findViewById(R.id.button6);
        buttonS = (Button)findViewById(R.id.button7);
        buttonA = (Button)findViewById(R.id.button8);

        //define progress bar
        progressbar = (ProgressBar)findViewById(R.id.progressBar);

        //define textView
        progressText = (TextView)findViewById(R.id.textViewProgressBar);

        //define image view
        userPicture = (ImageView) findViewById(R.id.imageView2);

        //set an OnClickListener to the buttons
        buttonPro.setOnClickListener(onClickListener);
        buttonC.setOnClickListener(onClickListener);
        buttonH.setOnClickListener(onClickListener);
        buttonS.setOnClickListener(onClickListener);
        buttonA.setOnClickListener(onClickListener);

        //set an OnClickListener to the progress bar
        progressbar.setOnClickListener(onClickListener);


        setUserName();
        setUserPicture();
        updateProgressBar();
        updateProgressText();
    }

    public void setUserName(){
        // etUsername.setText(*Muss username von Datenbank reintun*);
        //etUsername.setText("Max Mustermann");    //Test ob er den User Namen ändert

        if(getIntent().hasExtra("username") == true){
            String name = getIntent().getExtras().getString("username");
            etUsername.setText(name);
        }

    }

    public void setUserPicture(){
        // userPicture.setImageResource(R.drawable.*Muss Bild von Datenbank rein*);
    }

    /**
     * sets the progress of the progressbar to the number of challenges the user has completed
     */
    public void updateProgressBar(){
        //progressbar.setProgress(completedChallenges);
        progressbar.setProgress(2); //Test ob er den Wert auf 7 ändert
    }

    /**
     * converts the progress of the progressbar to a number and sets this to a string
     */
    public void updateProgressText(){
        //progressText.setText(completedChallenges.toString() + "/12");
        progressText.setText("2/12");   //Test ob er den Text zu 7/12 ändert
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
                startActivity(new Intent(Profile.this, ChallengeNew.class));
           }

            //show healthy Challenge
            if (v.getId() == R.id.button6){
                startActivity(new Intent(Profile.this, ChallengeNew.class));
            }

            //show social Challenge
            if (v.getId() == R.id.button7){
                startActivity(new Intent(Profile.this, ChallengeNew.class));
            }

            //show adventure Challenge
            if (v.getId() == R.id.button8){
                startActivity(new Intent(Profile.this, Challenge.class));
            }

            //if the user clicks on the progress bar he will be taken to the achievements layout
            if (v.getId() == R.id.progressBar){
                startActivity(new Intent(Profile.this, Achievements.class));
            }
        }
    };


}
