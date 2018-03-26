package db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ellyn on 26.03.2018.
 */

public class ChallengerContract {

    //eindeutiger Name des Content Providers
    public static final String AUTHORITY = "com.example.tarav.ourapp.provider";

    //Basis URI zum Content Provider
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    //Kontrakt fuer User
    public static class User{
        //Unterverzeichnis fuer die Daten
        public static final String CONTENT_DIRECTORY = "user";

        //Uri zu den Daten
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_DIRECTORY);

        //Datentyp fuer Auflistung der Daten
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_DIRECTORY;

        //Datenty fuer einzelne Datensaetze
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_DIRECTORY;


    }
}
