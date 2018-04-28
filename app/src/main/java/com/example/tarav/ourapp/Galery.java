package com.example.tarav.ourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Galery extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    private Button cameraBtn, galleryBtn, homeButton, saveButton, cancelButton;
    private ImageView imageView;
    private boolean cameraBtnClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        //Define Buttons
        cameraBtn = (Button)findViewById(R.id.cameraButton);
        galleryBtn = (Button)findViewById(R.id.galleryButton);
        homeButton = (Button)findViewById(R.id.homeButtonGallery);
        saveButton = (Button) findViewById(R.id.saveButtonGallery);
        cancelButton = (Button) findViewById(R.id.cancelButtonGallery);
        //Define ImageView
        imageView = (ImageView)findViewById(R.id.imageViewUserPicture);

        //set OnClickListeners
        cameraBtn.setOnClickListener(onClickListener);
        galleryBtn.setOnClickListener(onClickListener);
        homeButton.setOnClickListener(onClickListener);
        saveButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);

        cameraBtnClicked = false;
    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //CAMERA

            //Opens the camera if the camera button is clicked
            if(v.getId() == R.id.cameraButton){
                //sets boolean to true
                cameraBtnClicked = true;
                //Invokes the camera using an Intent
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);
            }


            //GALLERY

            //Opens the users gallery form its phone when the gallery button is clicked
            if(v.getId() == R.id.galleryButton){
                //Invokes the gallery using an implicit Intent
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                //Where do we want to find the data?
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //Get a URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //Set the data and type.
                //Data : where we want to look for this media / Type: what media do we want to look for
                //With "image/*" we get all image types
                photoPickerIntent.setDataAndType(data, "image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
            }


            //HOME

            if(v.getId() == R.id.homeButtonGallery){
                //go back to Profile
                startActivity(new Intent(Galery.this, Profile.class));
            }

            // SAVE IMAGE

            //if the user clickes the save button the image will be saved, and the user will be taken to changeProfile
            if(v.getId() == R.id.saveButtonGallery){
                //replace profilepic in drawable with new URL from taken image
                //???????


                //go back to Profile?
                //startActivity(new Intent(Galery.this, ChangeProfile.class));
            }

            // CANCEL

            //if the cancel button is clicked, the user will be taken back to change profile
            if(v.getId() == R.id.cancelButtonGallery){
                //stays on the same Activity, but deletes the Image from the ImageView
                imageView.setImageDrawable(null);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (cameraBtnClicked ==true) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //Sets the taken picture to the ImageView
            imageView.setImageBitmap(bitmap);
        }

        else if (resultCode == RESULT_OK){
            //if we are here, everything processed succesfully
            if (requestCode == IMAGE_GALLERY_REQUEST){
                //if we are here, we are hearing back from the image gallery

                //The adress of the image on the SD card
                Uri imageURI = data.getData();

                //declare a stream to read the image data from the SD card
                InputStream inputStream;

                //we are getting an input stream, based on the URI of the image
                try {
                    inputStream = getContentResolver().openInputStream(imageURI);

                    //get a bitmap from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //what do we do with the bitmap?
                    //We give it to an ImageView, to represent it
                    imageView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show a message to the user indicating that the image is unable to open
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }


            }
        }
    }

    /*
    //Saves the taken picture and gives it to imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        //Sets the taken picture to the ImageView
        imageView.setImageBitmap(bitmap);
    }
    */

}
