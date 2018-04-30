package com.example.tarav.ourapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import db.ChallengesTable;
import db.DbHelper;

public class Profile extends AppCompatActivity {

    Button buttonPro, buttonC, buttonH, buttonS, buttonA;
    ProgressBar progressbar;
    TextView progressText;
    //EditText etUsername;
    TextView etUsername;
    ImageView userPicture;

    //ids der challenges, die gerade laufen
    int doingCreative = 0;
    int doingHealth = 0;
    int doingSocial = 0;
    int doingAdventure = 0;



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
        buttonPro = (Button)findViewById(R.id.button9); //ChangeProfile
        buttonC = (Button)findViewById(R.id.button5);   //CreativeButton
        buttonH = (Button)findViewById(R.id.button6);   //HealthButton
        buttonS = (Button)findViewById(R.id.button7);   //SocialButton
        buttonA = (Button)findViewById(R.id.button8);   //AdventureButton

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

        fillChallenges();

        updateProgressBar();
        setLogos();


    }


    public String getGiven(){
            String name = getIntent().getExtras().getString("username");
            return  name;
    }


    public void fillChallenges(){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        boolean exists = checkTable();

        if(exists == false) {
            dbh.createChallenges(db);
            dbh.fillTable(db);
        }
        db.close();
        dbh.close();
    }



    /**
     * checks if challengesTable exists
     * @return exists
     */
    public boolean checkTable(){

        boolean exists = false;

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        //anstatt challenger sqlite_master?
        //challenges groß oder klein?
        String sql = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{"challenges"});

        if(cursor != null){
            if(cursor.getCount() > 0){
                exists = true;
            }else{
                exists = false;
            }
        }

        cursor.close();
        db.close();
        dbh.close();

        return exists;
    }


    /**
     * username wird vom login übergeben
     */
    public void setUserName(){
            etUsername.setText(getGiven());
    }


    public void setUserPicture(){
        // userPicture.setImageResource(R.drawable.*Muss Bild von Datenbank rein*);

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM user WHERE username = ? AND pic != ?";
        Cursor cursor = db.rawQuery(sql, new String[]{getGiven(), ""});
        int count = cursor.getCount();

        if(count > 0){
            cursor.moveToFirst();
            String pic = cursor.getString(4);
            Uri picURI = Uri.parse(pic);

            try{
                InputStream inputStream = getContentResolver().openInputStream(picURI);
                //get a bitmap from the stream
                Bitmap imageGallery = BitmapFactory.decodeStream(inputStream);
                //what do we do with the bitmap?
                //We give it to an ImageView, to represent it
                userPicture.setImageBitmap(imageGallery);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            userPicture.setImageResource(R.drawable.profilepic);
        }

    }


    public int countDone(){
        //zähle alle fertigen challenges
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM challenges WHERE ch_status = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{"done"});
        int count = cursor.getCount();

        cursor.close();
        dbh.close();
        db.close();

        return count;
    }

    /**
     * sets the progress of the progressbar to the number of challenges the user has completed
     */
    public void updateProgressBar(){
        //progressbar.setProgress(completedChallenges);

        int doneChallenges = countDone();

        progressbar.setProgress(doneChallenges);
        progressText.setText(doneChallenges + "/12");

    }


    private void setDoingLogo(){
        //get ch_status and logo (+ oder laufende_challenge oder finished_category)
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        String sql = "SELECT * FROM challenges WHERE ch_status = ?";

        //alles wo der username dem geholten username entspricht
        Cursor cursor = db.rawQuery(sql, new String[]{"doing"});
        int count = cursor.getCount();


        if(count > 0){
            cursor.moveToFirst();
            String [] genres = new String[4];
            String [] logos = new String[4];

            for(int i = 0; i < count; i++) {
                //im Array an Position i das gegebene Genre und das logo speichern
                genres[i] = cursor.getString(2);
                logos[i] = cursor.getString(5);
                cursor.moveToNext();

                System.out.println(genres[i]);
                System.out.println(logos[i]);
            }

            //bei welchem genre?


            //creative, health, social, adventure
            //get the genre --> get the logos for each of the 4 possible buttons
            for(int gl = 0; gl < count; gl++ ) {


                switch (genres[gl]) {
                    case "creative":
                        switch (logos[gl]) {
                            case "creativebtn1":
                                buttonC.setBackgroundResource(R.drawable.creativebtn1);
                                doingCreative = 1;
                                break;
                            case "creativebtn2":
                                buttonC.setBackgroundResource(R.drawable.creativebtn2);
                                doingCreative = 2;
                                break;
                            case "creativebtn3":
                                buttonC.setBackgroundResource(R.drawable.creativebtn3);
                                doingCreative = 3;
                                break;
                        }
                        break;

                    case "health":
                        switch (logos[gl]) {
                            case "healthbtn1":
                                doingHealth = 4;
                                buttonH.setBackgroundResource(R.drawable.healthbtn1);
                                break;
                            case "healthbtn2":
                                buttonH.setBackgroundResource(R.drawable.healthbtn2);
                                doingHealth = 5;
                                break;
                            case "healthbtn3":
                                buttonH.setBackgroundResource(R.drawable.healthbtn3);
                                doingHealth = 6;
                                break;
                        }
                        break;

                    case "social":
                        switch (logos[gl]) {
                            case "socialbtn1":
                                buttonS.setBackgroundResource(R.drawable.socialbtn1);
                                doingSocial = 7;
                                break;
                            case "socialbtn2":
                                buttonS.setBackgroundResource(R.drawable.socialbtn2);
                                doingSocial = 8;
                                break;
                            case "socialbtn3":
                                buttonS.setBackgroundResource(R.drawable.socialbtn3);
                                doingSocial = 9;
                                break;
                        }
                        break;

                    case "adventure":
                        switch (logos[gl]) {
                            case "adventurebtn1":
                                buttonA.setBackgroundResource(R.drawable.adventurebtn1);
                                doingAdventure = 10;
                                break;
                            case "adventurebtn2":
                                buttonA.setBackgroundResource(R.drawable.adventurebtn2);
                                doingAdventure = 11;
                                break;
                            case "adventurebtn3":
                                buttonA.setBackgroundResource(R.drawable.adventurebtn3);
                                doingAdventure = 12;
                                break;
                        }
                        break;
                }

            }
        }



        cursor.close();
        dbh.close();
        db.close();
    }



    private void setDoneLogo(){
        //get ch_status and logo (+ oder laufende_challenge oder finished_category)
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM challenges WHERE ch_status = ?";

        //alles wo der username dem geholten username entspricht
        Cursor cursor = db.rawQuery(sql, new String[]{"done"});
        int count = cursor.getCount();

        if(count > 0) {
            cursor.moveToFirst();
            String[] ids = new String[12];
            String[] genres = new String[12];

            for (int i = 0; i < count; i++) {
                //im Array an Position i das gegebene Genre speichern
                ids[i] = cursor.getString(0);
                genres[i] = cursor.getString(2);
                cursor.moveToNext();
            }

            boolean creativeDone = ((Arrays.asList(ids).contains("1")) &&
                    (Arrays.asList(ids).contains("2") && (Arrays.asList(ids).contains("3"))));

            boolean healthDone = ((Arrays.asList(ids).contains("4")) &&
                    (Arrays.asList(ids).contains("5") && (Arrays.asList(ids).contains("6"))));

            boolean socialDone = ((Arrays.asList(ids).contains("7")) &&
                    (Arrays.asList(ids).contains("8") && (Arrays.asList(ids).contains("9"))));

            boolean adventureDone = ((Arrays.asList(ids).contains("10")) &&
                    (Arrays.asList(ids).contains("11") && (Arrays.asList(ids).contains("12"))));




            for (int g = 0; g < count; g++) {
                if(genres[g].equals("creative") && !creativeDone) {
                    buttonC.setBackgroundResource(R.drawable.pluszeichen);
                }

                else if(genres[g].equals("creative") && creativeDone) {
                    buttonC.setBackgroundResource(R.drawable.challenge_done);
                }
                else if (genres[g].equals("health") && !healthDone) {
                    buttonH.setBackgroundResource(R.drawable.pluszeichen);
                }

                else if (genres[g].equals("health") && healthDone) {
                    buttonH.setBackgroundResource(R.drawable.challenge_done);
                }
                else if(genres[g].equals("social") && !socialDone) {
                    buttonS.setBackgroundResource(R.drawable.pluszeichen);
                }

                else if(genres[g].equals("social") && socialDone) {
                    buttonS.setBackgroundResource(R.drawable.challenge_done);
                }
                else if(genres[g].equals("adventure") && !adventureDone) {
                    buttonA.setBackgroundResource(R.drawable.pluszeichen);
                }
                else if(genres[g].equals("adventure") && adventureDone) {
                    buttonA.setBackgroundResource(R.drawable.challenge_done);
                }
            }
        }

        cursor.close();
        db.close();
        dbh.close();
    }


    /**
     * sets the images of the logos to the challenge_logos that have the status doing
     */
    public void setLogos(){

        setDoingLogo();
        setDoneLogo();

    }



    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            //IF EDIT-PROFILE-BUTTON IS CLICKED
            if(v.getId() == R.id.button9){
                //change to ChangeProfile
                if(getIntent().hasExtra("username") == true) {
                    //benutzername holen
                    String name = getIntent().getExtras().getString("username");
                    //go to edit profile
                    Intent toEdit = new Intent(Profile.this, ChangeProfile.class);
                    //Benutzername an Edit Profile übergeben
                    toEdit.putExtra("username", name);
                    startActivity(toEdit);
                }
            }

            // put extra , challenge genre
            //show creative Challenge
           if (v.getId() == R.id.button5){
                //check if doing, dann diese challenge --> sonst newChallenge
               String genre = "creative";
               String name = getIntent().getExtras().getString("username");
                if(doingCreative != 0) {
                    int chId = doingCreative;
                    Intent toChC = new Intent(Profile.this, Challenge.class);
                    toChC.putExtra("chId", doingCreative);
                    toChC.putExtra("username", name);
                    toChC.putExtra("genre", genre);
                    startActivity(toChC);
                }else{
                    Intent toNChC = new Intent(Profile.this, ChallengeNew.class);
                    toNChC.putExtra("username", name);
                    toNChC.putExtra("genre", genre);
                    startActivity(toNChC);
                }
           }

            //show healthy Challenge
            if (v.getId() == R.id.button6){
                //check if doing, dann diese challenge --> sonst newChallenge
                int chId = doingHealth;
                String genre = "health";
                String name = getIntent().getExtras().getString("username");
                if(doingHealth != 0) {
                    Intent toChH = new Intent(Profile.this, Challenge.class);
                    toChH.putExtra("chId", chId);
                    toChH.putExtra("username", name);
                    toChH.putExtra("genre", genre);
                    startActivity(toChH);
                }else{
                    Intent toNChH = new Intent(Profile.this, ChallengeNew.class);
                    toNChH.putExtra("username", name);
                    toNChH.putExtra("genre", genre);
                    startActivity(toNChH);
                }
            }

            //show social Challenge
            if (v.getId() == R.id.button7){
                //check if doing, dann diese challenge --> sonst newChallenge
                String genre = "social";
                String name = getIntent().getExtras().getString("username");
                if(doingSocial != 0) {
                    int chId = doingSocial;
                    Intent toChS = new Intent(Profile.this, Challenge.class);
                    toChS.putExtra("chId", doingSocial);
                    toChS.putExtra("username", name);
                    toChS.putExtra("genre", genre);
                    startActivity(toChS);
                }else{
                    Intent toNChS = new Intent(Profile.this, ChallengeNew.class);
                    toNChS.putExtra("username", name);
                    toNChS.putExtra("genre", genre);
                    startActivity(toNChS);
                }
            }

            //show adventure Challenge
            if (v.getId() == R.id.button8){
                //check if doing, dann diese challenge --> sonst newChallenge
                String genre = "adventure";
                String name = getIntent().getExtras().getString("username");
                if(doingAdventure != 0) {
                    int chId = doingAdventure;
                    Intent toChA = new Intent(Profile.this, Challenge.class);
                    toChA.putExtra("chId", doingAdventure);
                    toChA.putExtra("username", name);
                    toChA.putExtra("genre", genre);
                    startActivity(toChA);
                }else{
                    Intent toNChA = new Intent(Profile.this, ChallengeNew.class);
                    toNChA.putExtra("username", name);
                    toNChA.putExtra("genre", genre);
                    startActivity(toNChA);
                }
            }

            //if the user clicks on the progress bar he will be taken to the achievements layout
            if (v.getId() == R.id.progressBar){
                Intent toAchievements = new Intent(Profile.this, Achievements.class);
                String name = getIntent().getExtras().getString("username");
                toAchievements.putExtra("username", name);
                startActivity(toAchievements);
            }
        }
    };


}
