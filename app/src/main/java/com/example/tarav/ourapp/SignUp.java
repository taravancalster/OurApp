package com.example.tarav.ourapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class SignUp extends AppCompatActivity {

    public DatabaseHelper dbh = new DatabaseHelper(SignUp.this);
    SQLiteDatabase db;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_sign_up);

       final EditText email = (EditText) findViewById(R.id.etMail);
       final EditText username = (EditText) findViewById(R.id.etName);
       final EditText password = (EditText) findViewById(R.id.etPassword);
       final EditText password2 = (EditText) findViewById(R.id.etPassword2);

       final Button bRegister = (Button) findViewById(R.id.bRegister);
       final Button backBtn = (Button)findViewById(R.id.button4);



       /**
        * check if the registration data is correct
        * and he does not exist yet
        * and go to Profile
        */
       bRegister.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               File database = getApplicationContext().getDatabasePath("RegisterAndLogin.db");
               UserMemo user = new UserMemo(username.getText().toString(),
                       email.getText().toString(),
                       password.getText().toString(),
                       password2.getText().toString());

               //check if passwords match
               if (password.getText().toString().equals(password2.getText().toString())){
                   //database exists
                 //  if  (database.exists()){
                       //check that user doesn't allready exist
                       if (!dbh.exists(email.getText().toString(), password.getText().toString())) {
                           dbh.addUser(user);
                           startActivity(new Intent(SignUp.this, MainActivity.class));
                       } Toast.makeText(SignUp.this, "You allready exist!", Toast.LENGTH_SHORT);

                  // }else{
                       //if database does not exist --> build one and add the new user
                   //    dbh.addUser(user);
                   //    startActivity(new Intent(SignUp.this, MainActivity.class));
                  // }
               }
          }
       });

       /**
        * go back to Login
        */
       backBtn.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               startActivity(new Intent(SignUp.this, MainActivity.class));
           }
       });
   }
}
