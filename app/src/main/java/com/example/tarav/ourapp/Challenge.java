package com.example.tarav.ourapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Challenge extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 228;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4192;
    Button buttonProof, buttonComplete, homeButton;
    TextView challengeCategory ,challengeTitle, challengeDescription;
    ImageView challengePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //define the Buttons
            buttonProof = (Button) findViewById(R.id.proofPicButton);
            buttonComplete = (Button) findViewById(R.id.button16);
            homeButton = (Button) findViewById(R.id.homeButtonChallenge);
        //define TextViews
            challengeCategory = (TextView) findViewById(R.id.textView11);
            challengeTitle = (TextView) findViewById(R.id.textView12);
            challengeDescription = (TextView) findViewById(R.id.textView13);
        //define ImageView
            challengePicture = (ImageView) findViewById(R.id.imageView5);
        //set an OnClickListener to the buttons
            buttonProof.setOnClickListener(onClickListener);
            buttonComplete.setOnClickListener(onClickListener);
            homeButton.setOnClickListener(onClickListener);
    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {

                //TAKE A PROOF PICTURE-BUTTON
                    if (v.getId() == R.id.proofPicButton) {
                        /*//get our camera, as this is an imlicit intent we pass just a string ACTION_IMAGE_CAPTURE that says; we wish to invoke the camera
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                        */

                        //have we been granted permission to use the camera and to write in the external storage?
               //             if((checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
                                //if we have the permissions we want to invoke the camera
                                    invokeCamera();
             //               }

               /*         //aks the user for the previous permission
                            else {
                                //lets request permission
                                    String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE} ;
                                    requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
                            }
                 */
                    }


                 //CHALLENGE COMPLETED-BUTTON
                    if (v.getId() == R.id.button16) {
                        //aks user again if the challenge was completed
                        //mark ChallengesTable as done
                        //put it in Achievements
                        //change to Profile --> empty cross
                        startActivity(new Intent(Challenge.this, Profile.class));
                    }

                //HOME-BUTTON
                    if (v.getId() == R.id.homeButtonChallenge) {
                        startActivity(new Intent(Challenge.this, Profile.class));
                    }
        } ;

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());

        //put together the directory and the timestamp to make a unique image location
            File imageFile = new File(picturesDirectory, "challengerPicture" + timestamp + ".jpg");

            return imageFile;
    }

    //gives us the image path
    //save this in DB?
    //set it as profile picture
    public String getImage(){
        File image = createImageFile();
        String imagePath = image.getPath();

        return imagePath;
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //did we hear from CAMERA_PERMISSION_REQUEST_CODE?
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            //we have heard from our request for camera and write external storage
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                invokeCamera();
            }
            else {
                Toast.makeText(this, R.string.cannotopencamera, Toast.LENGTH_LONG).show();
            }
        }
    }

*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //once the camera closes, this activity is opened up
            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
            }
        }
    }
}