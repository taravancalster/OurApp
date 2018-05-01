package com.example.tarav.ourapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import db.DbHelper;

public class ChallengeNew extends AppCompatActivity {

    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;
    Button tryButton, notNowButton;

    int doingCreative = 0;
    int doingHealth = 0;
    int doingSocial = 0;
    int doingAdventure = 0;

    int challengeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_new);

        //define TextViews
        challengeCategory = (TextView) findViewById(R.id.textView17);
        challengeTitle = (TextView) findViewById(R.id.textView18);
        challengeDescription = (TextView) findViewById(R.id.textView19);
        //define ImageView
        challengePicture = (ImageView) findViewById(R.id.imageView4);
        //define Buttons
        tryButton = (Button) findViewById(R.id.button20);
        notNowButton = (Button) findViewById(R.id.button19);

        //add OnClick events
        tryButton.setOnClickListener(onClickListener);
        notNowButton.setOnClickListener(onClickListener);


        //genre feststellen
        String genre = getGenre();
        //challengeId holen
        challengeId = showChallenge(genre);

        if(challengeId == 0){
            //do something....
            Toast toast = Toast.makeText(ChallengeNew.this, "id = none", Toast.LENGTH_SHORT);
            toast.show();
        }else{
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
                    String chDescription = cursor.getString(3);
                    String chLogo = cursor.getString(4);


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

    }


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

    private int showChallenge(String genre){
        int challengeId = 0;

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        //get chIds of challenges that are not in the ch_user table
        String uId = getUserId(getGiven()); //NAME
        String sql = "SELECT * FROM ch_user WHERE user_id = ?";

        String[] chIds = new String[12];

        Cursor cursor = db.rawQuery(sql, new String[]{uId});
        int count = cursor.getCount();

        for(int i = 0; i < 12; i++) {
            if (count > 0) {
                cursor.moveToFirst();
                    //im Array an Position i das gegebene Genre und das logo speichern
                    chIds[i] = cursor.getString(1);
                    cursor.moveToNext();

                    System.out.println(chIds[i]);
            }
        }
        cursor.close();


            //alle die nicht aufgelistet sind zählen, nach bestimmten ids und dann switch mit genre zuweisung
            boolean doneOrDoingC1 = ((Arrays.asList(chIds).contains("1")));
            boolean doneOrDoingC2 = ((Arrays.asList(chIds).contains("2")));
            boolean doneOrDoingC3 = (Arrays.asList(chIds).contains("3"));

            boolean doneOrDoingH1 = ((Arrays.asList(chIds).contains("1")));
            boolean doneOrDoingH2 = ((Arrays.asList(chIds).contains("2")));
            boolean doneOrDoingH3 = (Arrays.asList(chIds).contains("3"));

            boolean doneOrDoingS1 = ((Arrays.asList(chIds).contains("1")));
            boolean doneOrDoingS2 = ((Arrays.asList(chIds).contains("2")));
            boolean doneOrDoingS3 = (Arrays.asList(chIds).contains("3"));

            boolean doneOrDoingA1 = ((Arrays.asList(chIds).contains("1")));
            boolean doneOrDoingA2 = ((Arrays.asList(chIds).contains("2")));
            boolean doneOrDoingA3 = (Arrays.asList(chIds).contains("3"));


        if(genre.equals("creative")){
            if(!doneOrDoingC1 && !doneOrDoingC2 && !doneOrDoingC3){
                challengeId = 1;
            }
            else if(doneOrDoingC1 && !doneOrDoingC2 && !doneOrDoingC3){
                challengeId = 2;
            }
            else if(doneOrDoingC1 && doneOrDoingC2 && doneOrDoingC3){
                challengeId = 3;
            }
        }
        else if(genre.equals("health")){
            if(!doneOrDoingH1 && !doneOrDoingH2 && !doneOrDoingH3){
                challengeId = 4;
            }
            else if(doneOrDoingH1 && !doneOrDoingH2 && !doneOrDoingH3){
                challengeId = 5;
            }
            else if(doneOrDoingH1 && doneOrDoingH2 && doneOrDoingH3){
                challengeId = 6;
            }
        }
        else if(genre.equals("social")){
            if(!doneOrDoingS1 && !doneOrDoingS2 && !doneOrDoingS3){
                challengeId = 7;
            }
            else if(doneOrDoingS1 && !doneOrDoingS2 && !doneOrDoingS3){
                challengeId = 8;
            }
            else if(doneOrDoingS1 && doneOrDoingS2 && doneOrDoingS3){
                challengeId = 9;
            }
        }
        else if(genre.equals("adventure")){
            if(!doneOrDoingA1 && !doneOrDoingA2 && !doneOrDoingA3){
                challengeId = 10;
            }
            else if(doneOrDoingA1 && !doneOrDoingA2 && !doneOrDoingA3){
                challengeId = 11;
            }
            else if(doneOrDoingA1 && doneOrDoingA2 && doneOrDoingA3){
                challengeId = 12;
            }
        }


        db.close();
        dbh.close();

        return challengeId;
    }


    private String getGiven(){
        String name = getIntent().getExtras().getString("username");
        return name;
    }

    private String getGenre(){
        //genre abfragen
            String gen = getIntent().getExtras().getString("genre");
            return gen;
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

    /**
     * setzt die ausgewählte challenge auf den status "doing"
     * wenn auf "Try" gedrückt wurde
     * @param chId
     */
    private void saveChallengesAsDoing(int chId){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        String uName = getGiven();

        String uId = getUserId(uName);

        ContentValues values = new ContentValues();
        values.put("ch_status", "doing");
        values.put("ch_id", chId);
        values.put("user_id", uId);

        //doing bei status eintragen, wo die id passt
        db.insert("ch_user", null, values);

        db.close();
        dbh.close();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //if tryButton is clicked: change to Profile AND save challenge as doing
            if(v.getId() == R.id.button20){

                saveChallengesAsDoing(challengeId);

                Intent toP = new Intent(ChallengeNew.this, Profile.class);
                String name = getGiven();
                toP.putExtra("username", name);
                startActivity(toP);

                //set new challenge in profile
            }

            //if notNotButton is clicked: change to Profile
            if (v.getId() == R.id.button19){
                Intent toCh = new Intent(ChallengeNew.this, Profile.class);
                String name = getGiven();
                toCh.putExtra("username", name);
                startActivity(toCh);
            }
        }
    };
}
