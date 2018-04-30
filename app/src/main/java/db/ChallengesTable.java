package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ellyn on 23.04.2018.
 */

public class ChallengesTable {

    private static final String _CREATE = "CREATE TABLE [challenges] ("+
            "[ch_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            "[ch_name] VARCHAR NOT NULL, "+
            "[ch_genre] VARCHAR NOT NULL, "+
            "[ch_status] VARCHAR NOT NULL, " +
            "[ch_description] VARCHAR NOT NULL, " +
            "[ch_logo] VARCHAR NOT NULL)";

    private static final String _INSERT_CREATIVE1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
           "VALUES ('POETIC', 'creative', 'no', 'Write a poem about this month. Take some time thinking about a few lines with rhymes that resume up this month.', 'creativebtn1')";

    private static final String _INSERT_CREATIVE2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('YOU', 'creative', 'no', ' Draw yourself. A quick look in the mirror, a piece of paper and a pencil is all you need. Put in some effort and who knows, you might discover a real Picasso or Dalí inside you. ', 'creativebtn2')";

    private static final String _INSERT_CREATIVE3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('DANCE!', 'creative', 'no', ' Invent a dance for your favorite song. Nobody has to see it, but it’s time to shake your hips and have some fun. Turn up the volume and just dance to a song you really like.', 'creativebtn3')";

    private static final String _INSERT_HEALTH1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('BE STRONG!', 'health', 'no', 'Abandon one of your bad habits for a day. It’s all up to you, it could be quit smoking for one day or not watching Netflix 24 hours. What is important is that you stay away from a bad habit for a bit. ', 'healthbtn1')";

    private static final String _INSERT_HEALTH2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('WORK YOUR WAY UP', 'health', 'no', ' Always take the stairs for one week. In this week you are not allowed to use elevators or escalators. Every time you get the opportunity to take the stairs instead you must do it. ', 'healthbtn2')";

    private static final String _INSERT_HEALTH3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('GO VEGAN!', 'health', 'no', ' Cook a vegan meal. Search for some vegan dish, get all the delicious ingredients and then it’s time to try out something new in the kitchen. * In case you’re already vegan, try out a new recipe, that one you always wanted to try but you never did.', 'healthbtn3')";

    private static final String _INSERT_SOCIAL1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('SMILE!', 'social', 'no', 'Today smile at everyone. You’re going to be really surprised how nice strangers react to just a simple smile. “Smile and the world will smile with you” they say, right? So let’s give it a try.', 'socialbtn1')";

    private static final String _INSERT_SOCIAL2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('LETTER', 'social', 'no', 'Write a letter to a good friend. Everyone gets excited when they get a nice unexpected letter! So it’s your turn to surprise a good friend by putting together some nice words and sending a letter.', 'socialbtn2')";

    private static final String _INSERT_SOCIAL3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('GAMES NIGHT', 'social', 'no', 'Host a games night. Invite some friends and get ready to have a great time. Everyone loves a nice evening with some card or board games, so now there’s no excuse for not doing it.', 'socialbtn3')";

    private static final String _INSERT_ADVENTURE1 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('EXPLORE NEW WAYS', 'adventure', 'no', 'Take a different way to get to work, university or school. No matter where you go today, it’s time to make a little change in your routine. It might take you a little longer, but you also might discover something very nice.', 'adventurebtn1')";

    private static final String _INSERT_ADVENTURE2 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('OUTDOOR EXPLORER', 'adventure', 'no', 'It’s time to spend an hour outside, so dance in the rain or walk in the sunshine. Just step outside your door and have a nice walk.', 'adventurebtn2')";

    private static final String _INSERT_ADVENTURE3 = "INSERT INTO challenges (ch_name, ch_genre, ch_status, ch_description, ch_logo)" +
            "VALUES ('HOMETOWN FACTS', 'adventure', 'no', 'Learn something new about your hometown. Take a book or open a browser and search for your hometown and start looking for a funny fact that you didn’t know about.', 'adventurebtn3')";



    public static void createTable(SQLiteDatabase db){
        db.execSQL(_CREATE);
    }

/*
    public String getSQLString_C1(){
        return _INSERT_CREATIVE1;
    }
    public String getSQLString_C2(){
        return _INSERT_CREATIVE2;
    }
    public String getSQLString_C3(){
        return _INSERT_CREATIVE3;
    }

    public String getSQLString_H1(){
        return _INSERT_HEALTH1;
    }
    public String getSQLString_H2(){
        return _INSERT_HEALTH2;
    }
    public String getSQLString_H3(){
        return _INSERT_HEALTH3;
    }

    public String getSQLString_S1(){
        return _INSERT_SOCIAL1;
    }
    public String getSQLString_S2(){
        return _INSERT_SOCIAL2;
    }
    public String getSQLString_S3(){
        return _INSERT_SOCIAL3;
    }

    public String getSQLString_A1(){
        return _INSERT_ADVENTURE1;
    }
    public String getSQLString_A2(){
        return _INSERT_ADVENTURE2;
    }
    public String getSQLString_A3(){
        return _INSERT_ADVENTURE3;
    }
    */

    public static void fillTable(SQLiteDatabase db){
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
