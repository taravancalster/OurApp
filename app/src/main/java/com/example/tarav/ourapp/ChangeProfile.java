package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        //define the buttons
        Button buttonBack = (Button)findViewById(R.id.homeButton);
        Button buttonCPic = (Button)findViewById(R.id.button14);
        Button buttonSave = (Button)findViewById(R.id.button11);
        Button buttonCPW = (Button)findViewById(R.id.button12);
        Button buttonLogO = (Button)findViewById(R.id.button13);

        //set an OnClickListener to the buttons
        buttonBack.setOnClickListener(onClickListener);
        buttonCPic.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
        buttonCPW.setOnClickListener(onClickListener);
        buttonLogO.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //back to profile
            if(v.getId() == R.id.homeButton){
                //save changes

                //go back to profile
                startActivity(new Intent(ChangeProfile.this, Profile.class));
            }


            //change Pic
            if(v.getId() == R.id.button14){
                // go to galery
                startActivity(new Intent(ChangeProfile.this, Galery.class));
            }

            //change Username
            if(v.getId() == R.id.button11){
              //get new username

                //save username

                //give username to profile
            }

            //change password
            if(v.getId() == R.id.button12){
                startActivity(new Intent(ChangeProfile.this, ChangePW.class));
            }

            //Log out!
            if(v.getId() == R.id.button13){
                //change settings

                //go to Main
                startActivity(new Intent(ChangeProfile.this, MainActivity.class));
            }
        }
    };
}
