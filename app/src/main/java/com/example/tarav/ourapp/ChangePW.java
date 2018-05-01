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

public class ChangePW extends AppCompatActivity {

    Button buttonSave, buttonCancel, homeButton;
    EditText opw, pw1, pw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        //define the buttons
        buttonSave = (Button)findViewById(R.id.button17);
        buttonCancel = (Button)findViewById(R.id.button18);
        homeButton = (Button)findViewById(R.id.homeButtonChangePw);

        //set an OnClickListener to the buttons
        buttonSave.setOnClickListener(onClickListener);
        buttonCancel.setOnClickListener(onClickListener);
        homeButton.setOnClickListener(onClickListener);

        opw = (EditText) findViewById(R.id.editText8);
        pw1 = (EditText) findViewById(R.id.editText9);
        pw2 = (EditText) findViewById(R.id.editText10);


    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button17){

                //user suchen mit dem alten pw + username
                //get username for search
                if(getIntent().hasExtra("username") == true){
                    String name = getIntent().getExtras().getString("username");

                    //get opw
                    String oldPW = opw.getText().toString();
                    String newPw1 = pw1.getText().toString();
                    String newPw2 = pw2.getText().toString();

                    //get the database
                    DbHelper dbh = new DbHelper(getApplicationContext());
                    SQLiteDatabase db = dbh.getWritableDatabase();

                    //get user with this username and pw
                    String sql = "SELECT * FROM user WHERE username = ? AND pw = ?";
                    Cursor cursor = db.rawQuery(sql, new String[]{name, oldPW});

                    //schauen ob es einen passenden user gibt
                    int count = cursor.getCount();
                    cursor.close();

                    //wenn ja
                    if(count > 0){
                        //schauen ob die pw uebereinstimmen
                        if(newPw1.equals(newPw2)) {
                            //neues pw an db uebergeben
                            ContentValues values = new ContentValues();
                            values.put("pw", pw1.getText().toString());
                            db.update("user", values, "username = ?", new String[]{name} );
                            db.close();
                            dbh.close();

                            //zu changeprofile mit username uebergabe
                            Intent toChangeProfile = new Intent(ChangePW.this, ChangeProfile.class);
                            //Benutzername an Profile übergeben
                            toChangeProfile.putExtra("username", name);
                            //zu Profile gehen
                            startActivity(toChangeProfile);
                        }else{
                            Toast toastPW = Toast.makeText(ChangePW.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                            toastPW.show();
                        }
                    }


                    //giving username back in Intent and go to profile
                    Intent toChangePW = new Intent(ChangePW.this, Profile.class);
                    //Benutzername an Profile übergeben
                    toChangePW.putExtra("username", name);
                    startActivity(toChangePW);



                }

            }

            if(v.getId() == R.id.button18){
                //go back to Change Profile
                startActivity(new Intent(ChangePW.this, ChangeProfile.class));
            }

            if(v.getId() == R.id.homeButtonChangePw){
                //go back to Profile
                startActivity(new Intent(ChangePW.this, Profile.class));
            }
        }
    };
}
