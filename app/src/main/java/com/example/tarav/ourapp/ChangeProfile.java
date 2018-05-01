package com.example.tarav.ourapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import db.DbHelper;

public class ChangeProfile extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int PROFILE_REQUEST = 21;
    Button buttonSave, buttonCPW, buttonLogO, homeButton;
    ImageView userImage;
    EditText changeUsername;
    boolean wasClicked = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        //define the buttons
        buttonSave = (Button)findViewById(R.id.saveButton);
        buttonCPW = (Button)findViewById(R.id.changePwButton);
        buttonLogO = (Button)findViewById(R.id.logoutButton);
        homeButton = (Button)findViewById(R.id.homeButtonChangeProfile);
        //define view
        userImage = (ImageView)findViewById(R.id.imageView3);
        //define text
        changeUsername = (EditText)findViewById(R.id.editText7);

        //set an OnClickListener to the buttons
        buttonSave.setOnClickListener(onClickListener);
        buttonCPW.setOnClickListener(onClickListener);
        buttonLogO.setOnClickListener(onClickListener);
        homeButton.setOnClickListener(onClickListener);
        //set an OnClickListener to the ImageView
        userImage.setOnClickListener(onClickListener);
        //set an OnClickListener to the text
        //changeUsername.setOnClickListener(onClickListener);


        changeUsername.setText(getGiven());

        //setPic();

    }



    public String getGiven(){
        String name = getIntent().getExtras().getString("username");
        return name;
    }
/*
    public void setPic(){


        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM user WHERE username = ? AND pic != ?";
        Cursor cursor = db.rawQuery(sql, new String[]{getGiven(), ""});
        int count = cursor.getCount();

        if(count > 0){
            cursor.moveToFirst();
            String pic = cursor.getString(4);
            Uri picURI = Uri.parse(pic);

            try{
                InputStream inputStream = getContentResolver().openInputStream(picURI);
                //get a bitmap from the stream
                Bitmap imageGallery = BitmapFactory.decodeStream(inputStream);
                //what do we do with the bitmap?
                //We give it to an ImageView, to represent it
                userImage.setImageBitmap(imageGallery);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            userImage.setImageResource(R.drawable.profilepic);
        }

        cursor.close();
        db.close();
        dbh.close();

    }
*/

    /**
     * gibt true zurück, wenn user noch kein pic hat
     * @return
     */
    /*
    public boolean checkPicFilled(){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getReadableDatabase();

        String sql = "SELECT * FROM user WHERE username = ? AND pic = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{getGiven(), ""});
        int count = cursor.getCount();

        cursor.close();
        db.close();
        dbh.close();

        if(count > 0){
            return false;
        }else{
            return true;
        }

    }
    */

    /**
     * speichert URI als Stirng in der userTabelle
     * @param uri
     */
    public void saveUri(String uri){
        DbHelper dbh = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("pic", uri);

        db.update("user", values, "username = ?", new String[]{getGiven()});

        db.close();
        dbh.close();
    }


    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //back to profile
            if(v.getId() == R.id.homeButtonChangeProfile){

                //gives back right username for profile
                if(wasClicked){
                    String newN = changeUsername.getText().toString();
                    //give username to profile
                    Intent toProfile = new Intent(ChangeProfile.this, Profile.class);
                    //Benutzername an Profile übergeben
                    toProfile.putExtra("username", newN);
                    //zu Profile gehen
                    startActivity(toProfile);
                }else{
                    //alten Benutzernamen abfragen und in variable speichern
                    String oldN = getIntent().getExtras().getString("username");
                    //give username to profile
                    Intent toProfile = new Intent(ChangeProfile.this, Profile.class);
                    //Benutzername an Profile übergeben
                    toProfile.putExtra("username", oldN);
                    //zu Profile gehen
                    startActivity(toProfile);
                }



            }

            //change picture
            if(v.getId() == R.id.imageView3){
                //takes the user to the Gallery layout
              // startActivity(new Intent(ChangeProfile.this, Galery.class));

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

            //change Username
            if(v.getId() == R.id.saveButton){

                wasClicked = true;

                 //get new username
                String newName = changeUsername.getText().toString();
                //check if it is already taken
                //get the database
                DbHelper dbh = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbh.getWritableDatabase();
                //get all usernames that equal the newName
                String sql = "SELECT * FROM user WHERE username = ?";
                Cursor cursor = db.rawQuery(sql, new String[]{newName});

                int count = cursor.getCount();

                if(count > 0){
                    cursor.moveToFirst();
                    String namE = cursor.getString(1);
                    Toast existToast = Toast.makeText(ChangeProfile.this, namE  + "is allready taken", Toast.LENGTH_SHORT);
                    existToast.show();
                }else {
                    String oldName = getIntent().getExtras().getString("username");
                    //save username
                    ContentValues values = new ContentValues();
                    values.put("username", newName);
                    db.update("user", values, "username = ?", new String[] {oldName}  );
                    Toast doneToast = Toast.makeText(ChangeProfile.this, "New Username saved!", Toast.LENGTH_SHORT);
                    doneToast.show();
                }
                cursor.close();
                db.close();
                dbh.close();
            }



            //change password
            if(v.getId() == R.id.changePwButton){

                if(getIntent().hasExtra("username") == true){
                    String name = getIntent().getExtras().getString("username");
                    Intent toChangePW = new Intent(ChangeProfile.this, ChangePW.class);
                    //Benutzername an Profile übergeben
                    toChangePW.putExtra("username", name);
                    startActivity(toChangePW);
                }
            }


            //Log out!
            if(v.getId() == R.id.logoutButton){
                //change settings

                //go to Main
                startActivity(new Intent(ChangeProfile.this, MainActivity.class));
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap, imageGallery, imageCamera;

       // if (resultCode == RESULT_OK){
            //if we are here, everything processed succesfully

            if (requestCode == IMAGE_GALLERY_REQUEST){
                //if we are here, we are hearing back from the image gallery
                //The adress of the image on the SD card
                Uri imageURI = data.getData();

                String uri = imageURI.toString();

                saveUri(uri);

                //declare a stream to read the image data from the SD card
                InputStream inputStream;

                //we are getting an input stream, based on the URI of the image
                try {
                    //imageURI
                    inputStream = getContentResolver().openInputStream(imageURI);
                    //get a bitmap from the stream
                    imageGallery = BitmapFactory.decodeStream(inputStream);
                    //what do we do with the bitmap?
                    //We give it to an ImageView, to represent it
                    userImage.setImageBitmap(imageGallery);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show a message to the user indicating that the image is unable to open
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }


      //  }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getPictureName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(new Date());
        return "Challenger" + timeStamp + ".jpg";
    }

}
