package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ellyn on 23.04.2018.
 */

public class AchievementsTable {

    private static final String _CREATE = "CREATE TABLE [achievements] ("+
            "[achievement_id] INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
            "[achievement_date] DATE NOT NULL , "+
            "[ch_id] INTEGER NOT NULL , "+
            "[user_id] INTEGER NOT NULL, " +
            "[proofImage_url] VARCHAR)";

    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
    }

}
