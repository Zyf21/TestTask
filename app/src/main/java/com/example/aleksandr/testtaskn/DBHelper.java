package com.example.aleksandr.testtaskn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aleksandr on 30.04.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BestDatabase";
    public static final String TABLE_PLASES = "plases";

    public static final String KEY_ID = "_id";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGTITUDE= "longtitude";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_WEATHER = "weather";
    public static final String KEY_DATEANDTIME = "dateandtime";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PLASES + "(" + KEY_ID
                + " integer primary key,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_WEATHER + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGTITUDE + " TEXT,"
                + KEY_DATEANDTIME + " TEXT"

                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PLASES );

        onCreate(db);

    }


}
