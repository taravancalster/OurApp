package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ellyn on 23.04.2018.
 */

public class ChallengesTable {

    private static final String _CREATE = "CREATE TABLE [challenges] ("+
            "[ch_id] INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+
            "[ch_name] VARCHAR NOT NULL , "+
            "[ch_genre] VARCHAR NOT NULL , "+
            "[ch_status] VARCHAR NOT NULL ), " +
            "[ch_description] VARCHAR NOT NULL), " +
            "[logo_url] VARCHAR NOT NULL)";

    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
    }

}
