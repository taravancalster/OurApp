package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ellyn on 19.03.2018.
 */

public class ChallengeTable {
    private static final String _CREATE = "CREATE TABLE [user] ("+
                                            "[user_id] INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
                                            "[username] VARCHAR NOT NULL , "+
                                            "[email] VARCHAR NOT NULL , "+
                                            "[pw] VARCHAR NOT NULL )";

    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
    }
}
