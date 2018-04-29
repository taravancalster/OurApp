package com.example.tarav.ourapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

        if(getIntent().hasExtra("username") == true){
            String name = getIntent().getExtras().getString("username");
            etUsername.setText(name);
        }

    }


    public void setUserPicture(){
        // userPicture.setImageResource(R.drawable.*Muss Bild von Datenbank rein*);
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

    /**
     * sets the images of the logos to the challenge_logos that have the status doing
     */
    public void setLogos(){

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

            }

            //bei welchem genre?


            String category;
            //creative, health, social, adventure

            //get the genre --> get the logos for each of the 4 possible buttons
            for(int gl = 0; gl <=4; gl++ ) {


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
                                buttonH.setBackgroundResource(R.drawable.healthbtn1);
                                doingHealth = 4;
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
                break;
            }
        }else{
            //wenn es keine mit doing gibt
            Toast toastNoDoing = Toast.makeText(Profile.this, "No challenges accepted", Toast.LENGTH_SHORT);
            toastNoDoing.show();
        }

        cursor.close();
        dbh.close();
        db.close();
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
                    Intent toCh = new Intent(Profile.this, Challenge.class);
                    toCh.putExtra("chId", doingCreative);
                    toCh.putExtra("username", name);
                    toCh.putExtra("genre", genre);
                    startActivity(toCh);
                }else{
                    Intent toNCh = new Intent(Profile.this, ChallengeNew.class);
                    toNCh.putExtra("username", name);
                    toNCh.putExtra("genre", genre);
                    startActivity(toNCh);
                }
           }

            //show healthy Challenge
            if (v.getId() == R.id.button6){
                //check if doing, dann diese challenge --> sonst newChallenge
                String genre = "health";
                String name = getIntent().getExtras().getString("username");
                if(doingHealth != 0) {
                    Intent toCh = new Intent(Profile.this, Challenge.class);
                    toCh.putExtra("chId", doingHealth);
                    toCh.putExtra("username", name);
                    toCh.putExtra("genre", genre);
                    startActivity(toCh);
                }else{
                    Intent toNCh = new Intent(Profile.this, ChallengeNew.class);
                    toNCh.putExtra("username", name);
                    toNCh.putExtra("genre", genre);
                    startActivity(toNCh);
                }
            }

            //show social Challenge
            if (v.getId() == R.id.button7){
                //check if doing, dann diese challenge --> sonst newChallenge
                String genre = "social";
                String name = getIntent().getExtras().getString("username");
                if(doingSocial != 0) {
                    Intent toCh = new Intent(Profile.this, Challenge.class);
                    toCh.putExtra("chId", doingSocial);
                    toCh.putExtra("username", name);
                    toCh.putExtra("genre", genre);
                    startActivity(toCh);
                }else{
                    Intent toNCh = new Intent(Profile.this, ChallengeNew.class);
                    toNCh.putExtra("username", name);
                    toNCh.putExtra("genre", genre);
                    startActivity(toNCh);
                }
            }

            //show adventure Challenge
            if (v.getId() == R.id.button8){
                //check if doing, dann diese challenge --> sonst newChallenge
                String genre = "adventure";
                String name = getIntent().getExtras().getString("username");
                if(doingAdventure != 0) {
                    Intent toCh = new Intent(Profile.this, Challenge.class);
                    toCh.putExtra("chId", doingAdventure);
                    toCh.putExtra("username", name);
                    toCh.putExtra("genre", genre);
                    startActivity(toCh);
                }else{
                    Intent toNCh = new Intent(Profile.this, ChallengeNew.class);
                    toNCh.putExtra("username", name);
                    toNCh.putExtra("genre", genre);
                    startActivity(toNCh);
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
