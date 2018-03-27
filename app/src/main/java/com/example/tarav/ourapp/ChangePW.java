package com.example.tarav.ourapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePW extends AppCompatActivity {

    Button buttonSave, buttonCancel, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        //define the buttons
        buttonSave = (Button)findViewById(R.id.button17);
        buttonCancel = (Button)findViewById(R.id.button18);
        homeButton = (Button)findViewById(R.id.homeButtonChangePw);

        //set an OnClickListener to the buttons
        buttonSave.setOnClickListener(onClickListener);
        buttonCancel.setOnClickListener(onClickListener);
        homeButton.setOnClickListener(onClickListener);

    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button17){
                //save the new password

                //go back to changeProfile
                startActivity(new Intent(ChangePW.this, ChangeProfile.class));
            }

            if(v.getId() == R.id.button18){
                //go back to Change Profile
                startActivity(new Intent(ChangePW.this, ChangeProfile.class));
            }

            if(v.getId() == R.id.homeButtonChangePw){
                //go back to Profile
                startActivity(new Intent(ChangePW.this, Profile.class));
            }
        }
    };
}
