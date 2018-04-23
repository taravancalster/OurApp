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
            "[ch_logo] VARCHAR NOT NULL)";

    private static final String _INSERT_CREATIVE1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
           "VALUES ('Poetic', 'creative', 'no', 'Write a poem about this month.', 'creativebtn1')";

    private static final String _INSERT_CREATIVE2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('You', 'creative', 'no', 'Draw yourself.', 'creativebtn2')";

    private static final String _INSERT_CREATIVE3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Dance!', 'creative', 'no', 'Invent a dance for your favorite song.', 'creativebtn3')";

    private static final String _INSERT_HEALTH1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Be Strong!', 'health', 'no', 'Abandon one of your bad habits for one day.', 'healthbtn1')";

    private static final String _INSERT_HEALTH2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Work Your Way Up', 'health', 'no', 'Always take the stairs for one week.', 'healthbtn2')";

    private static final String _INSERT_HEALTH3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Go Vegan!', 'health', 'no', 'Try and cook a vegan meal.', 'healthbtn3')";

    private static final String _INSERT_SOCIAL1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Smile!', 'social', 'no', 'Today smile at everyone.', 'socialbtn1')";

    private static final String _INSERT_SOCIAL2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Letter', 'social', 'no', 'Write a letter to a good friend.', 'socialbtn2')";

    private static final String _INSERT_SOCIAL3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Games', 'social', 'no', 'Host a games night.', 'socialbtn3')";

    private static final String _INSERT_ADVENTURE1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Explore', 'adventure', 'no', 'Take a different way to get to work, universsity or school.', 'adventurebtn1')";

    private static final String _INSERT_ADVENTURE2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('The Outdoors', 'adventure', 'no', 'Dance in the rain or walk in the sunshine - Just spend an hour outside.', 'adventurebtn2')";

    private static final String _INSERT_ADVENTURE3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('Hometown', 'adventure', 'no', 'Learn something new about your hometown.', 'adventurebtn3')";



    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
        db.execSQL(_INSERT_CREATIVE1);
        db.execSQL(_INSERT_CREATIVE2);
        db.execSQL(_INSERT_CREATIVE3);
        db.execSQL(_INSERT_HEALTH1);
        db.execSQL(_INSERT_HEALTH2);
        db.execSQL(_INSERT_HEALTH3);
        db.execSQL(_INSERT_SOCIAL1);
        db.execSQL(_INSERT_SOCIAL2);
        db.execSQL(_INSERT_SOCIAL3);
        db.execSQL(_INSERT_ADVENTURE1);
        db.execSQL(_INSERT_ADVENTURE2);
        db.execSQL(_INSERT_ADVENTURE3);

    }

}
