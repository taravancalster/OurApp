package com.example.tarav.ourapp;

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

import db.DbHelper;

public class ChallengeNew extends AppCompatActivity {

    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;
    Button tryButton, notNowButton;
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
                    String chDescription = cursor.getString(4);
                    String chLogo = cursor.getString(5);


                    challengeCategory.setText(chGenre);
                    challengeTitle.setText(chName);
                    challengeDescription.setText(chDescription);

                    //packagename???
                    int id = getResources().getIdentifier("res:drawable/" + chLogo + ".png", "drawable", getPackageName());
                    challengePicture.setImageResource(id);

                }
            }
            cursor.close();
            db.close();
            dbh.close();
        }

    }

    private int showChallenge(String genre){
        int challengeId = 0;

        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM challenges WHERE ch_genre = ? AND ch_status = ?";


        switch (genre){
            case "creative":
                Cursor cursorC = db.rawQuery(sql, new String[]{"creative", "no"});
                int countC = cursorC.getCount();
                switch (countC){
                    case 3:
                        challengeId = 1;
                        break;
                    case 2:
                        challengeId = 2;
                        break;
                    case 1:
                        challengeId = 3;
                        break;
                }
                cursorC.close();
                break;
            case "health":
                Cursor cursorH = db.rawQuery(sql, new String[]{"health"});
                int countH = cursorH.getCount();
                switch (countH){
                    case 3:
                        challengeId = 4;
                        break;
                    case 2:
                        challengeId = 5;
                        break;
                    case 1:
                        challengeId = 6;
                        break;
                }
                cursorH.close();
                break;
            case "social":
                Cursor cursorS = db.rawQuery(sql, new String[]{"social"});
                int countS = cursorS.getCount();
                switch (countS){
                    case 3:
                        challengeId = 7;
                        break;
                    case 2:
                        challengeId = 8;
                        break;
                    case 1:
                        challengeId = 9;
                        break;
                }
                cursorS.close();
                break;
            case "adventure":
                Cursor cursorA = db.rawQuery(sql, new String[]{"adventure"});
                int countA = cursorA.getCount();
                switch (countA){
                    case 3:
                        challengeId = 10;
                        break;
                    case 2:
                        challengeId = 11;
                        break;
                    case 1:
                        challengeId = 12;
                        break;
                }
                cursorA.close();
                break;
        }

        db.close();
        dbh.close();

        return challengeId;
    }



    private String getGenre(){
        //genre abfragen
            String gen = getIntent().getExtras().getString("genre");
            return gen;
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //if tryButton is clicked: change to Challenge
            if(v.getId() == R.id.button20){
                Intent toCh = new Intent(ChallengeNew.this, Challenge.class);
                String name = getIntent().getExtras().getString("username");
                int chId = challengeId;
                toCh.putExtra("username", name);
                toCh.putExtra("chId", chId);
                startActivity(toCh);

                //set new challenge in profile
            }

            //if notNotButton is clicked: change to Profile
            if (v.getId() == R.id.button19){
                Intent toCh = new Intent(ChallengeNew.this, Profile.class);
                String name = getIntent().getExtras().getString("username");
                toCh.putExtra("username", name);
                startActivity(toCh);
            }
        }
    };
}
