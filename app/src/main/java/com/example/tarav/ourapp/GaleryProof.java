package com.example.tarav.ourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GaleryProof extends AppCompatActivity {

    Button cameraBtn, homeButton, saveButton, cancelButton ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_proof);

        //Define Buttons
        cameraBtn = (Button)findViewById(R.id.cameraButton);
        homeButton = (Button)findViewById(R.id.homeButtonGalleryProof);
        saveButton = (Button)findViewById(R.id.saveButtonGalleryProof);
        cancelButton = (Button)findViewById(R.id.cancelButtonGalleryProof);
        //Define ImageView
        imageView = (ImageView)findViewById(R.id.imageViewProofPicture);

        //set OnClickListener to the button
        cameraBtn.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cameraButton){
                //Creates an Intent, which opens the Camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);
            }

            if(v.getId() == R.id.homeButtonGalleryProof){
                //go back to Profile
                startActivity(new Intent(GaleryProof.this, Profile.class));
            }

            //if the user clickes the save button the image will be saved in achievements, and the user will be taken to achievements
            if(v.getId() == R.id.saveButtonGalleryProof){
                //replace challengepic in drawable with new URL from taken image
                //???????


                //go back to Profile?
                //startActivity(new Intent(GaleryProof.this, Challenge.class));
            }

            //if the cancel button is clicked, the user will be taken back to profile
            if(v.getId() == R.id.cancelButtonGalleryProof){
                //stays on the same Activity, but deletes the Image from the ImageView
                imageView.setImageDrawable(null);
            }
        }
    };


    //Saves the taken picture and gives it to imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        //Sets the taken picture to the ImageView
        imageView.setImageBitmap(bitmap);
    }
}
