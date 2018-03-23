package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ChangeProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        //define the buttons
        Button buttonBack = (Button)findViewById(R.id.homeButton);
        Button buttonSave = (Button)findViewById(R.id.saveButton);
        Button buttonCPW = (Button)findViewById(R.id.changePwButton);
        Button buttonLogO = (Button)findViewById(R.id.logoutButton);
        //define view
        ImageView userImage = (ImageView)findViewById(R.id.imageView3);
        //define text
        EditText changeUsername = (EditText)findViewById(R.id.editText7);

        //set an OnClickListener to the buttons
        buttonBack.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
        buttonCPW.setOnClickListener(onClickListener);
        buttonLogO.setOnClickListener(onClickListener);
        //set an OnClickListener to the ImageView
        userImage.setOnClickListener(onClickListener);
        //set an OnClickListener to the text
        changeUsername.setOnClickListener(onClickListener);

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

            //change picture
            if(v.getId() == R.id.imageView3){
                //takes the user to the Gallery layout
               startActivity(new Intent(ChangeProfile.this, Galery.class));
            }

            //change Username
            if(v.getId() == R.id.editText7){
                 //get new username

                //check if at already is taken

                //save username

                //give username to profile
            }

            //change password
            if(v.getId() == R.id.changePwButton){
                startActivity(new Intent(ChangeProfile.this, ChangePW.class));
            }

            //Log out!
            if(v.getId() == R.id.logoutButton){
                //change settings

                //go to Main
                startActivity(new Intent(ChangeProfile.this, MainActivity.class));
            }
        }
    };
}
