package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
