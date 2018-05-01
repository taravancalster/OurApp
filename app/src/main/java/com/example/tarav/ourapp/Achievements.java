package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import db.DbHelper;

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
        progressbar = (ProgressBar) findViewById(R.id.progressBar2);

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
        setDoneChallenges();

    }


    /**
     * returns the username
     *
     * @return
     */
    public String getGiven() {
        String name = getIntent().getExtras().getString("username");
        return name;
    }


    /**
     * returnes userId from user table
     * searches it with username
     *
     * @param username
     * @return
     */
    public String getUserId(String username) {
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();
        String uId = "0";
        String sql = "SELECT * FROM user WHERE username = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{username});
        int count = cursor.getCount();

        if (count > 0) {
            cursor.moveToFirst();
            uId = cursor.getString(0);
        }

        cursor.close();
        db.close();
        dbh.close();
        return uId;
    }

    /**
     * zählt die fertigen Challenges in ch_user
     *
     * @return
     */
    public int countDone() {
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
    public void updateProgressBar() {
        //progressbar.setProgress(completedChallenges);

        int doneChallenges = countDone();

        progressbar.setProgress(doneChallenges);
        progressText.setText(doneChallenges + "/12");

    }


    public void setDoneChallenges() {
        //holt sich donechallenges_ids
        String uName = getGiven();
        String uId = getUserId(uName);

        //für die challengeIds
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        //für je pic und title
        DbHelper dbh2 = new DbHelper(getApplicationContext());
        SQLiteDatabase db2 = dbh2.getReadableDatabase();

        String getChallengeIds = "SELECT * FROM ch_user WHERE user_id = ? AND ch_status = ?";
        Cursor cursorGetChallenges = db.rawQuery(getChallengeIds, new String[]{uId, "done"});
        int countChallenges = cursorGetChallenges.getCount();

        String[] chIds = new String[12];
        String[] logos = new String[12];
        String[] chNames = new String[12];

        if (countChallenges > 0) {
            cursorGetChallenges.moveToFirst();

            for (int i = 0; i < countChallenges; i++) {
                chIds[i] = cursorGetChallenges.getString(1);
                System.out.println(chIds[i]);

                //hole ch_name und ch_logo je ch_id

                String chIdNow = chIds[i];
                String getChallengePicAndTitle = "SELECT * FROM challenges WHERE ch_id = ?";
                Cursor cursorGetPicAndTitle = db2.rawQuery(getChallengePicAndTitle, new String[]{chIdNow});
                int countPicAndTitle = cursorGetPicAndTitle.getCount();

                if (countPicAndTitle > 0) {
                    cursorGetPicAndTitle.moveToFirst();

                    logos[i] = cursorGetPicAndTitle.getString(4);
                    chNames[i] = cursorGetPicAndTitle.getString(1);

                    System.out.println(logos[i]);
                    System.out.println(chNames[i]);

                    cursorGetPicAndTitle.moveToNext();
                }
                cursorGetChallenges.moveToNext();
            }

        }

        db2.close();
        dbh2.close();

        cursorGetChallenges.close();
        db.close();
        dbh.close();


        if(logos[0] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[0], "drawable", Achievements.this.getPackageName());
            imgChallenge1.setImageResource(resId);
            txtChallenge1.setText(chNames[0]);
        }
        else if(logos[1] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[1], "drawable", Achievements.this.getPackageName());
            imgChallenge2.setImageResource(resId);
            txtChallenge2.setText(chNames[1]);
        }
        else if(logos[2] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[2], "drawable", Achievements.this.getPackageName());
            imgChallenge3.setImageResource(resId);
            txtChallenge3.setText(chNames[2]);
        }
        else if(logos[3] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[3], "drawable", Achievements.this.getPackageName());
            imgChallenge4.setImageResource(resId);
            txtChallenge4.setText(chNames[3]);
        }
        else if(logos[4] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[4], "drawable", Achievements.this.getPackageName());
            imgChallenge5.setImageResource(resId);
            txtChallenge5.setText(chNames[4]);
        }
        else if(logos[5] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[5], "drawable", Achievements.this.getPackageName());
            imgChallenge6.setImageResource(resId);
            txtChallenge6.setText(chNames[5]);
        }
        else if(logos[6] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[6], "drawable", Achievements.this.getPackageName());
            imgChallenge7.setImageResource(resId);
            txtChallenge7.setText(chNames[6]);
        }
        else if(logos[7] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[7], "drawable", Achievements.this.getPackageName());
            imgChallenge8.setImageResource(resId);
            txtChallenge8.setText(chNames[7]);
        }
        else if(logos[8] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[8], "drawable", Achievements.this.getPackageName());
            imgChallenge9.setImageResource(resId);
            txtChallenge9.setText(chNames[8]);
        }
        else if(logos[9] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[9], "drawable", Achievements.this.getPackageName());
            imgChallenge10.setImageResource(resId);
            txtChallenge10.setText(chNames[9]);
        }
        else if(logos[10] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[10], "drawable", Achievements.this.getPackageName());
            imgChallenge11.setImageResource(resId);
            txtChallenge11.setText(chNames[10]);
        }
        else if(logos[11] != null) {
            int resId = Achievements.this.getResources().getIdentifier(logos[11], "drawable", Achievements.this.getPackageName());
            imgChallenge12.setImageResource(resId);
            txtChallenge12.setText(chNames[11]);
        }




        //setzt die reihe runter die Bilder auf das richtige bild und die titel
        //alles andere bleibt ein Fragezeichen


    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //IF HOME-BUTTON IS CLICKED

            if (v.getId() == R.id.homeButtonAchievements) {
                String name = getGiven();
                Intent toHome = new Intent(Achievements.this, Profile.class);
                //Benutzername an Profile übergeben
                toHome.putExtra("username", name);
                startActivity(toHome);
            }

        }
    };
}