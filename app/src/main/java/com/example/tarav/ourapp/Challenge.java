package com.example.tarav.ourapp;


import android.content.ContentValues;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.DbHelper;

public class Challenge extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 228;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4192;
    Button buttonProof, buttonComplete, homeButton;
    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;

    int challengeId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        challengeId = getIntent().getExtras().getInt("chId");

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

    public String getUserId(String username){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();
        String uId = "";
        String sql = "SELECT * FROM user WHERE username = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{username});
        int count = cursor.getCount();

        if(count > 0){
            cursor.moveToFirst();
            uId = cursor.getString(0);
        }

        cursor.close();
        db.close();
        dbh.close();

        return uId;
    }

    private void saveChallengesAsDone(int chId){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        String uName = getUserId(getIntent().getExtras().getString("username"));
        String uId = getUserId(uName);

        ContentValues values = new ContentValues();
        values.put("ch_status", "done");
        values.put("ch_id", chId);
        values.put("user_id", uId);

        String ch_id = String.valueOf(chId);

        //doing bei status eintragen, wo die id passt
        db.update("ch_user", values, "WHERE ch_id = ?", new String[]{ch_id} );

        db.close();
        dbh.close();
    }




    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

                //TAKE A PROOF PICTURE-BUTTON
                    if (v.getId() == R.id.proofPicButton) {
                        invokeCamera();
                    }


                 //CHALLENGE COMPLETED-BUTTON
                    if (v.getId() == R.id.button16) {
                        //ask user again if the challenge was completed
                        //mark ChallengesTable as done
                        //put it in Achievements
                        //change to Profile --> empty cross

                        saveChallengesAsDone(challengeId);
                        Intent toP = new Intent(Challenge.this, Profile.class);
                        String name = getIntent().getExtras().getString("username");
                        toP.putExtra("username", name);
                        startActivity(toP);
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



    private void invokeCamera(){

        //get a file reference
            Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", createImageFile());

        //get our camera, as this is an imlicit intent we pass just a string ACTION_IMAGE_CAPTURE that says; we wish to invoke the camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //tell the camera where to save the image. We want to save the image at EXTRA_OUTPUT location
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        //tell the camera to request WRITE permission
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private File createImageFile() {

        //get public pictures directory where all apps can access
            File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //make a timestamp that makes unique name
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());

        //put together the directory and the timestamp to make a unique image location
            File imageFile = new File(picturesDirectory, "challengerPicture" + timestamp + ".jpg");

            return imageFile;
    }

    //gives us the image path
    //save this in DB?
    //set it as profile picture
        public String getImagePath(){
            File imageFile = createImageFile();
            String imagePath = imageFile.getPath();
            return imagePath;
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
       // if (resultCode == RESULT_OK) {
            //once the camera closes, this activity is opened up
            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, "Image saved!", Toast.LENGTH_LONG).show();
            }
        }
   // }
}