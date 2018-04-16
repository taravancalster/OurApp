package com.example.tarav.ourapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import db.DbHelper;

public class ChangeProfile extends AppCompatActivity {

    Button buttonSave, buttonCPW, buttonLogO, homeButton;
    ImageView userImage;
    EditText changeUsername;

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


        //set username in the textfield for changing the username
        if(getIntent().hasExtra("username") == true){
            String name = getIntent().getExtras().getString("username");
            changeUsername.setText(name);
        }

    }

    //what happens onClick?
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //back to profile
            if(v.getId() == R.id.homeButtonChangeProfile){
                //give username to profile
                Intent toProfile = new Intent(ChangeProfile.this, Profile.class);
                //Benutzername an Profile übergeben
                toProfile.putExtra("username", changeUsername.getText().toString());
                //zu Profile gehen
                startActivity(toProfile);
            }

            //change picture
            if(v.getId() == R.id.imageView3){
                //takes the user to the Gallery layout
               startActivity(new Intent(ChangeProfile.this, Galery.class));
            }

            //change Username
            if(v.getId() == R.id.saveButton){
                 //get new username
                String newName = changeUsername.getText().toString();
                //check if it is already taken
                //get the database
                DbHelper dbh = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbh.getWritableDatabase();
                //SQLiteDatabase dbRead = dbh.getReadableDatabase();
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
                    //save username
                    ContentValues values = new ContentValues();
                    values.put("username", newName);
                    db.insert("user", null, values);
                    Toast doneToast = Toast.makeText(ChangeProfile.this, "New Username saved!", Toast.LENGTH_SHORT);
                    doneToast.show();
                }
                cursor.close();
                //dbRead.close();
                db.close();
                dbh.close();
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
