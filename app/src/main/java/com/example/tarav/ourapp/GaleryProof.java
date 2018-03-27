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

    Button cameraBtn, homeButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_proof);

        //Define Buttons
        cameraBtn = (Button)findViewById(R.id.cameraButton);
        homeButton = (Button)findViewById(R.id.homeButtonGalleryProof);
        //Define ImageView
        imageView = (ImageView)findViewById(R.id.imageViewProofPicture);

        //set OnClickListener to the button
        cameraBtn.setOnClickListener(onClickListener);
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
