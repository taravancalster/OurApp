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

import db.DbHelper;

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


    public void setLogos(){

            //get ch_status and logo (+ oder laufende_challenge oder finished_category)
            DbHelper dbh = new DbHelper(getApplicationContext());
            String sql = "SELECT * FROM challenges WHERE ch_status = ?";
            SQLiteDatabase db = dbh.getWritableDatabase();
            //alles wo der username dem geholten username entspricht
            Cursor cursor = db.rawQuery(sql, new String[]{"doing"});
            int count = cursor.getCount();

            if(count > 0){
                cursor.moveToFirst();

                String [] genres = new String[4];
                String [] logos = new String[4];

                for(int i = 0; i > count; i++) {
                    //im Array an Position i das gegebene Genre und die passende ch_id speichern speichern
                    genres[i] = cursor.getString(3);
                    logos[i] = cursor.getString(5);
                }

                //bei welchem genre?


                String category;
                //creative, health, social, adventure
                Context context = buttonC.getContext();


                switch(genres[0]){
                    case "creative":
                        String logo0 = logos[0];
                        int id = context.getResources().getIdentifier(logo0, "drawable", context.getPackageName());
                        buttonC.setBackgroundResource(id);
                        break;

                    case "health":

                        break;

                    case "social":

                        break;

                    case "adventure":

                        break;
                }

                //für jede id das logo aus db holen
                //an richtige position setzen

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
