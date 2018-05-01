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

        fillChallenges();
        createUserChTable();

        updateProgressBar();
        checkGivenGenre();
        setLogos();

        //setUserPicture();

    }


    public void checkGivenGenre(){
        if(getIntent().getExtras().getString("genre") != null){
            String genre = getIntent().getExtras().getString("genre");
            switch (genre){
                case "creative":
                    doingCreative = 0;
                    break;
                case "health":
                    doingHealth = 0;
                    break;
                case "social":
                    doingSocial = 0;
                    break;
                case "adventure":
                    doingAdventure = 0;
                    break;
            }
        }
    }

    /**
     * returns the username
     * @return
     */
    public String getGiven(){
            String name = getIntent().getExtras().getString("username");
            return  name;
    }


    /**
     * fills the challengesdb with challenges
     */
    public void fillChallenges(){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        boolean exists = checkTable("challenges");

        if(exists == false) {
            dbh.createChallenges(db);
            dbh.fillTable(db);
        }
        db.close();
        dbh.close();
    }

    /**
     * creates the ch_user Table
     */
    public void createUserChTable(){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        boolean exists = checkTable("ch_user");

        if(exists == false) {
            dbh.createChUser(db);
        }
        db.close();
        dbh.close();
    }


    /**
     * checks if challengesTable exists
     * @return exists
     */
    public boolean checkTable(String table){

        boolean exists = false;

        String t = table;

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        //anstatt challenger sqlite_master?
        //challenges groß oder klein?
        String sql = "SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{t});
        int count = cursor.getCount();

            if(count > 0){
                exists = true;
            }else{
                exists = false;
            }

        cursor.close();
        db.close();
        dbh.close();

        return exists;
    }


    /**
     * setzt das userName Feld
     * username wird vom login übergeben
     */
    public void setUserName(){
            etUsername.setText(getGiven());
    }


    /**
     * setzt das UserPic
     */
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

        cursor.close();
        db.close();
        dbh.close();
    }


    /**
     * zählt die fertigen Challenges in ch_user
     * @return
     */
    public int countDone(){
        //zähle alle fertigen challenges
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();


        String uName = getGiven();
        String uId = getUserId(uName);

        String sql = "SELECT * FROM ch_user WHERE ch_status = ? AND user_id = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{"done", uId});
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
     * returnes userId from user table
     * searches it with username
     *
     * @param username
     * @return
     */
    public String getUserId(String username){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();
        String uId = "0";
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

    /**
     * returns the ch_id from ch_user table
     * where ch_status is doing
     * @return
     */
    public String[] getChallengeIdsDoing(){
        String name = getGiven();
        String uId = getUserId(name);

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();




        String[] ch_ids = new String[4];

        String sql = "SELECT * FROM ch_user WHERE user_id = ? AND ch_status = ?";

        //alles wo der username dem geholten username entspricht
        Cursor cursor = db.rawQuery(sql, new String[]{uId, "doing"});
        int count = cursor.getCount();

        if(count > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < count; i++) {
                ch_ids[i] = cursor.getString(1);
                System.out.println(ch_ids[i]);
            }
        }

        cursor.close();
        db.close();
        dbh.close();

        return ch_ids;
    }



    private void setDoingLogo() {
        String name = getGiven();
        String uId = getUserId(name);

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        //get ch_status and logo (+ oder laufende_challenge oder finished_category)
        DbHelper dbh2 = new DbHelper(getApplicationContext());
        SQLiteDatabase db2 = dbh.getReadableDatabase();

        String[] ch_ids = new String[4];
        String[] genres = new String[4];
        String[] logos = new String[4];

        String sqlChIds = "SELECT * FROM ch_user WHERE user_id = ? AND ch_status = ?";
        Cursor cursorChIds = db.rawQuery(sqlChIds, new String[]{uId, "doing"});
        int countChIds = cursorChIds.getCount();

        if(countChIds > 0){
            cursorChIds.moveToFirst();

            for(int i = 0; i < countChIds; i++){
                ch_ids[i] = cursorChIds.getString(1);
                System.out.println(ch_ids[i]);

                String chIdNow = ch_ids[i];
                String sqlGenreAndLogo = "SELECT * FROM challenges WHERE ch_id = ?";
                Cursor cursorGenreAndLogo = db.rawQuery(sqlGenreAndLogo, new String[]{chIdNow});
                int countGenreAndLogo = cursorGenreAndLogo.getCount();

                if(countGenreAndLogo > 0){
                    cursorGenreAndLogo.moveToFirst();

                    genres[i] = cursorGenreAndLogo.getString(2);
                    logos[i] = cursorGenreAndLogo.getString(4);

                    System.out.println(genres[i]);
                    System.out.println(logos[i]);

                    cursorGenreAndLogo.moveToNext();
                }
                cursorChIds.moveToNext();
            }
        }

        db2.close();
        dbh2.close();

        cursorChIds.close();
        db.close();
        dbh.close();

        //int resId = Achievements.this.getResources().getIdentifier(logos[0], "drawable", Achievements.this.getPackageName());
        //imgChallenge1.setImageResource(resId);



        //count inhalt von chIds
        int countorGl = 0;
        for(int s = 0; s < 4; s++) {
            if (genres[s] != null) {
                countorGl++;
            }
        }
        //creative, health, social, adventure
        //get the genre --> get the logos for each of the 4 possible buttons
        for (int gl = 0; gl < countorGl ; gl++) {
            switch (genres[gl]) {
                case "creative":
                    int resId1 = Profile.this.getResources().getIdentifier(logos[gl], "drawable", Profile.this.getPackageName());
                    buttonC.setBackgroundResource(resId1);
                    switch (ch_ids[gl]){
                        case "1":
                            doingCreative = 1;
                            break;
                        case "2":
                            doingCreative = 2;
                            break;
                        case "3":
                            doingCreative = 3;
                            break;
                    }
                   /*
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
                    */
                    break;

                case "health":
                    int resId2 = Profile.this.getResources().getIdentifier(logos[gl], "drawable", Profile.this.getPackageName());
                    buttonH.setBackgroundResource(resId2);
                    switch (ch_ids[gl]){
                        case "4":
                            doingHealth = 4;
                            break;
                        case "5":
                            doingHealth = 5;
                            break;
                        case "6":
                            doingHealth = 6;
                            break;
                    }
                    /*
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
                    */
                    break;

                case "social":
                    int resId3 = Profile.this.getResources().getIdentifier(logos[gl], "drawable", Profile.this.getPackageName());
                    buttonS.setBackgroundResource(resId3);
                    switch (ch_ids[gl]){
                        case "7":
                            doingSocial = 7;
                            break;
                        case "8":
                            doingSocial = 8;
                            break;
                        case "9":
                            doingSocial = 9;
                            break;
                    }
                    /*
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
                    */
                    break;

                case "adventure":
                    int resId4 = Profile.this.getResources().getIdentifier(logos[gl], "drawable", Profile.this.getPackageName());
                    buttonA.setBackgroundResource(resId4);
                    switch (ch_ids[gl]){
                        case "10":
                            doingCreative = 10;
                            break;
                        case "11":
                            doingCreative = 11;
                            break;
                        case "12":
                            doingCreative = 12;
                            break;
                    }
                    /*
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
                    */
                    break;
            }

        }
    }


    /**
     * returns the ch_id from ch_user table
     * where ch_status is doing
     * @return
     */
    public String[] getChallengeIdsDone(){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String name = getGiven();
        String uId = getUserId(name);

        String[] ch_ids = new String[12];

        String sql = "SELECT * FROM ch_user WHERE user_id = ? AND ch_status = ?";

        //alles wo der username dem geholten username entspricht
        Cursor cursor = db.rawQuery(sql, new String[]{uId, "done"});
        int count = cursor.getCount();

        if(count > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < count; i++) {
                ch_ids[i] = cursor.getString(1);
            }
        }

        cursor.close();
        db.close();
        dbh.close();

        return ch_ids;
    }




    private void setDoneLogo(){
        String uName = getGiven();
        String uId = getUserId(uName);

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        DbHelper dbh2 = new DbHelper(getApplicationContext());
        SQLiteDatabase db2 = dbh2.getReadableDatabase();

        String sqlChIds = "SELECT * FROM ch_user WHERE user_id = ? AND ch_status = ?";
        Cursor cursorGetChallenges = db.rawQuery(sqlChIds, new String[]{uId, "done"});
        int countChallenges = cursorGetChallenges.getCount();

        //ch_id zur prüfung der challenges und  genre für button
        String[] genres = new String[12];
        String[] chIds = new String[12];

        if(countChallenges > 0){
            cursorGetChallenges.moveToFirst();

            for(int i = 0; i < countChallenges; i++){
                chIds[i] = cursorGetChallenges.getString(1);
                System.out.println(chIds[i]);

                String chIdNow = chIds[i];

                String sqlGenre = "SELECT * FROM challenges WHERE ch_id = ?";
                Cursor cursorGenre = db2.rawQuery(sqlGenre, new String[]{chIdNow});
                int countGenre = cursorGenre.getCount();

                if(countGenre > 0){
                    cursorGenre.moveToFirst();

                    genres[i] = cursorGenre.getString(2);
                    System.out.println(genres[i]);

                    cursorGenre.moveToNext();
                }
                cursorGetChallenges.moveToNext();
            }
        }

        db2.close();
        dbh2.close();

        cursorGetChallenges.close();
        db.close();
        dbh.close();


        boolean creativeDone = ((Arrays.asList(chIds).contains("1")) &&
                (Arrays.asList(chIds).contains("2") && (Arrays.asList(chIds).contains("3"))));

        boolean healthDone = ((Arrays.asList(chIds).contains("4")) &&
                (Arrays.asList(chIds).contains("5") && (Arrays.asList(chIds).contains("6"))));

        boolean socialDone = ((Arrays.asList(chIds).contains("7")) &&
                (Arrays.asList(chIds).contains("8") && (Arrays.asList(chIds).contains("9"))));

        boolean adventureDone = ((Arrays.asList(chIds).contains("10")) &&
                (Arrays.asList(chIds).contains("11") && (Arrays.asList(chIds).contains("12"))));


        //count inhalt von chIds
        int countorG = 0;
        for(int s = 0; s < 12; s++) {
            if (genres[s] != null) {
                countorG++;
            }
        }


        for (int g = 0; g < countorG; g++) {
            switch (genres[g]) {
                case "creative":
                    if (creativeDone) {
                        buttonC.setBackgroundResource(R.drawable.challenge_done);
                    } else {
                        buttonC.setBackgroundResource(R.drawable.pluszeichen);
                    }
                    break;
                case "health":
                    if (healthDone) {
                        buttonH.setBackgroundResource(R.drawable.challenge_done);
                    } else {
                        buttonH.setBackgroundResource(R.drawable.pluszeichen);
                    }
                    break;
                case "social":
                    if (socialDone) {
                        buttonS.setBackgroundResource(R.drawable.challenge_done);
                    } else {
                        buttonS.setBackgroundResource(R.drawable.pluszeichen);
                    }
                    break;
                case "adventure":
                    if (adventureDone) {
                        buttonA.setBackgroundResource(R.drawable.challenge_done);
                    } else {
                        buttonA.setBackgroundResource(R.drawable.pluszeichen);
                    }
                    break;
            }
        }
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
               String name = getGiven();
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
                String name = getGiven();
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
                String name = getGiven();
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
                String name = getGiven();
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
                String name = getGiven();
                toAchievements.putExtra("username", name);
                startActivity(toAchievements);
            }
        }
    };


}
