package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import db.DbHelper;

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


            setChallenge();

    }


    private void setChallenge(){
        //id von newChallenge übernehmen
        int challengeId = getIntent().getExtras().getInt("chId");

        //get challenge + anzeigen
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM challenges WHERE ch_id = ?";
        String[] whereArgs = {String.valueOf(challengeId)};


        Cursor cursor = db.rawQuery(sql, whereArgs);

        int counter = cursor.getCount();
        if (cursor != null) {
            if(counter == 1) {
                cursor.moveToFirst();

                //speichere email und pw in variable
                String chName = cursor.getString(1);
                String chGenre = cursor.getString(2);
                String chDescription = cursor.getString(4);
                String chLogo = cursor.getString(5);


                challengeCategory.setText(chGenre);
                challengeTitle.setText(chName);
                challengeDescription.setText(chDescription);

                setImage(chLogo);

            }
        }
        cursor.close();
        db.close();
        dbh.close();

    }

    /**
     * switch für das richtige Bild
     * @param chLogo
     */
    private void setImage(String chLogo){
        switch(chLogo){
            case "creativebtn1":
                challengePicture.setImageResource(R.drawable.creativebtn1);
                break;
            case "creativebtn2":
                challengePicture.setImageResource(R.drawable.creativebtn2);
                break;
            case "creativebtn3":
                challengePicture.setImageResource(R.drawable.creativebtn3);
                break;

            case "healthbtn1":
                challengePicture.setImageResource(R.drawable.healthbtn1);
                break;
            case "healthbtn2":
                challengePicture.setImageResource(R.drawable.healthbtn2);
                break;
            case "healthbtn3":
                challengePicture.setImageResource(R.drawable.healthbtn3);
                break;

            case "socialbtn1":
                challengePicture.setImageResource(R.drawable.socialbtn1);
                break;
            case "socialbtn2":
                challengePicture.setImageResource(R.drawable.socialbtn2);
                break;
            case "socialbtn3":
                challengePicture.setImageResource(R.drawable.socialbtn3);
                break;

            case "adventurebtn1":
                challengePicture.setImageResource(R.drawable.adventurebtn1);
                break;
            case "adventurebtn2":
                challengePicture.setImageResource(R.drawable.adventurebtn2);
                break;
            case "adventurebtn3":
                challengePicture.setImageResource(R.drawable.adventurebtn3);
                break;
        }
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
                        Intent toP = new Intent(Challenge.this, Profile.class);
                        String name = getIntent().getExtras().getString("username");
                        toP.putExtra("username", name);
                        startActivity(toP);
                    }
        } ;

    };

}