package com.example.saisrikanth.serviceapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srikh on 4/9/2016.
 */
//DB Creation steps
public class DBCreate extends SQLiteOpenHelper {
    final private static String NAME = "DBProject4";
    final private static Integer VERSION = 1;
    final private Context mCtx;
    final static String TABLE_NAME = "History";
    final static String songNumber = "songNumber";
    final static String requestMade = "requestMade";
    final static String currentState = "currentState";
    final static String date = "date";
    final static String time = "time";
    final static String NumberId = "NumberId";
    final static String[] columns = { NumberId, songNumber, date, time, requestMade, currentState };

    final private static String dbCreate =
            "CREATE TABLE History (" + NumberId + " INTEGER PRIMARY KEY AUTOINCREMENT, " + songNumber + " TEXT NOT NULL," + "date," + "time," + "requestMade," + "currentState)";

    public DBCreate(Context context) {
        super(context, NAME, null, VERSION);
        this.mCtx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbCreate);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    void deleteDatabase() {
        mCtx.deleteDatabase(NAME);
    }
}
