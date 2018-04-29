package com.example.tarav.ourapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;


public class Galery extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 238;
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 0;

    private Button cameraBtn, galleryBtn, homeButton, saveButton, cancelButton;
    private ImageView imageView;

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

    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

            //CAMERA
                if(v.getId() == R.id.cameraButton){
                   invokeCamera();
                }


            //GALLERY
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
                if(v.getId() == R.id.saveButtonGallery){

                    //save

                    //go back to ChangeProfile
                    //startActivity(new Intent(Galery.this, ChangeProfile.class));
                }


            // CANCEL
                if(v.getId() == R.id.cancelButtonGallery){
                    //stays on the same Activity, but deletes the Image from the ImageView
                    imageView.setImageDrawable(null);
                }
        }
    };
    private void invokeCamera(){

        //get a file reference
        Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", createImageFile());

        //get our camera, as this is an imlicit intent we pass just a string ACTION_IMAGE_CAPTURE that says; we wish to invoke the camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //tell the camera where to save the image. We want to save the image at EXTRA_OUTPUT location
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        //tell the camera to request WRITE permission
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private File createImageFile() {

        //get public pictures directory where all apps can access
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //make a timestamp that makes unique name
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());

        //put together the directory and the timestamp to make a unique image location
        File imageFile = new File(picturesDirectory, "challengerPicture" + timestamp + ".jpg");

        return imageFile;
    }


    public String getImagePath(){
        File imageFile = createImageFile();
        String imagePath = imageFile.getPath();
        return imagePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap, imageGallery, imageCamera;

        if (resultCode == RESULT_OK){
            //if we are here, everything processed succesfully


            if (requestCode == CAMERA_REQUEST_CODE){
              // Toast.makeText(this, "Image saved!" + getImagePath(), Toast.LENGTH_LONG).show();

                String imagePath = getImagePath();
                bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
            }


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
                    imageGallery = BitmapFactory.decodeStream(inputStream);
                    //what do we do with the bitmap?
                    //We give it to an ImageView, to represent it
                    imageView.setImageBitmap(imageGallery);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show a message to the user indicating that the image is unable to open
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getPictureName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(new Date());
        return "Challenger" + timeStamp + ".jpg";
    }

}
