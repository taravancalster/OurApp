package db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ellyn on 19.03.2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String _DB_FILE_NAME = "challenger.db";
    private static final int _DB_VERSION = 1;



    //Konstruktor
    public DbHelper(Context context) {

        super(context, _DB_FILE_NAME, null, _DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        UserTable.createTable(db);
        ChUserTable.createTable(db);
        //AchievementsTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createChallenges(SQLiteDatabase db){
        ChallengesTable.createTable(db);
    }

    public void fillTable(SQLiteDatabase db){
        ChallengesTable.fillTable(db);
    }


}
