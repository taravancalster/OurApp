package com.example.tarav.ourapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import db.DbHelper;

public class SignUp extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_sign_up);

       //Buttons lösen Listener aus
       Button bRegister = (Button) findViewById(R.id.bRegister);
       Button backBtn = (Button)findViewById(R.id.button4);


       /**
        * go back to Login
        */
       backBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               startActivity(new Intent(SignUp.this, MainActivity.class));
           }
       });


       /**
        * if registration data is correct
        * and if does not exist
        * then go to Profile
        */
       bRegister.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               //inputfields
               EditText email = (EditText) findViewById(R.id.etMail);
               EditText username = (EditText) findViewById(R.id.etName);
               EditText password = (EditText) findViewById(R.id.etPassword);
               EditText password2 = (EditText) findViewById(R.id.etPassword2);

               DbHelper dbh = new DbHelper(getApplicationContext());
               SQLiteDatabase db = dbh.getWritableDatabase();

               //if alle felder sind voll
               if(email != null && username != null && password != null && password2 != null){
                   //if email hat @
                   if(email.getText().toString().contains("@")){
                       //if email exists
                       SQLiteDatabase dbRead = dbh.getReadableDatabase();
                        String suchStr = "email = '" + email.getText().toString() + "'";
                        String[] spalten = new String[] {"username", "email"};
                        Cursor cursor = dbRead.query("user", spalten, suchStr, null, null, null, null);
                        int anzahl = cursor.getCount();
                        cursor.close();
                        if(anzahl == 0){
                           //if pws match
                           if(password.getText().toString().equals(password2.getText().toString())) {
                               //values zuordnen
                               ContentValues values = new ContentValues();
                               values.put("email", email.getText().toString());
                               values.put("username", username.getText().toString());
                               values.put("pw", password.getText().toString());

                               //füge input in die db ein
                               db.insert("user", null, values);
                               db.close();
                               dbh.close();
                               //zu Login
                               startActivity(new Intent(SignUp.this, MainActivity.class));
                           }
                        }
                    }
               }
          }
       });

   }
}
