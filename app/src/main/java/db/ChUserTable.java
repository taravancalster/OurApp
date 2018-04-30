package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ellyn on 30.04.2018.
 */

public class ChUserTable {
    private static final String _CREATE = "CREATE TABLE [ch_user] ("+
            "[user_id] INTEGER NOT NULL , "+
            "[ch_id] INTEGER NOT NULL," +
            "[ch_status] VARCHAR NOT NULL)";

    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
    }


}
