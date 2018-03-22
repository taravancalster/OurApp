package com.example.tarav.ourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Challenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //define the buttons
        Button buttonProof = (Button)findViewById(R.id.proofPicButton);
        Button buttonComplete = (Button)findViewById(R.id.button16);

        //Creates an ImageView
        //ImageView imageView = new ImageView();

        //set an OnClickListener to the buttons
        buttonProof.setOnClickListener(onClickListener);
        buttonComplete.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.proofPicButton){
                //If the proofPicButton is clicked, the dispatchTakePictureIntent() Method is called up
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);

                //save the picture in the right challenge
            }

            else if(v.getId() == R.id.button16){
                //mark challenge as done
                //put it in Achievements
                //change to Profile --> empty cross
                startActivity(new Intent(Challenge.this, Profile.class));
            }
        }
    };


    //Saves the taken picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

        /*  If we would like to put that image in an ImageView:
        imageView.setImageBitmap(bitmap);
        */
    }
}
