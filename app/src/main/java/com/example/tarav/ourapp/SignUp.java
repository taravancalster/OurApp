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
import android.widget.Toast;

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
               EditText email = (EditText) findViewById(R.id.etEmail);
               EditText username = (EditText) findViewById(R.id.etName);
               EditText password = (EditText) findViewById(R.id.etPW);
               EditText password2 = (EditText) findViewById(R.id.etPassword2);

               String mail = email.getText().toString();
               String name = username.getText().toString();
               String pw = password.getText().toString();
               String pw2 = password2.getText().toString();



               DbHelper dbh = new DbHelper(getApplicationContext());
               SQLiteDatabase db = dbh.getWritableDatabase();

               //if alle felder sind voll
               if((mail != "") &&(name != "") && (pw != "") && (pw2 != "")){
                   //if email hat @
                   if(mail.contains("@")){
                       //if email exists
                       SQLiteDatabase dbRead = dbh.getReadableDatabase();
                        String suchStr = "SELECT * FROM user WHERE email = ?";
                        Cursor cursor = dbRead.rawQuery(suchStr, new String[]{mail});
                        int anzahl = cursor.getCount();
                        cursor.close();
                        if(anzahl == 0){
                           //if pws match
                           if(pw.equals(pw2)) {
                               //values zuordnen
                               ContentValues values = new ContentValues();
                               values.put("email", mail);
                               values.put("username", name);
                               values.put("pw", pw);

                               //füge input in die db ein
                               cursor.close();
                               db.insert("user", null, values);
                               db.close();
                               dbh.close();
                               //zu Login
                               startActivity(new Intent(SignUp.this, MainActivity.class));
                           }else{
                               Toast toastPW = Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                               toastPW.show();
                           }
                        } else{
                            Toast toastE = Toast.makeText(SignUp.this, "Your email already exists!", Toast.LENGTH_SHORT);
                            toastE.show();
                        }
                    } else{
                       Toast toastNot = Toast.makeText(SignUp.this, "This is not an email!", Toast.LENGTH_SHORT);
                        toastNot.show();
                   }
               } else{
                   Toast toastFill = Toast.makeText(SignUp.this, "Please fill out everything!", Toast.LENGTH_SHORT);
                    toastFill.show();
               }
          }
       });

   }
}
