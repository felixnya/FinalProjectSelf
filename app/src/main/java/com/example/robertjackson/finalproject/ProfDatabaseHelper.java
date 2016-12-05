package com.example.robertjackson.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProfDatabaseHelper extends SQLiteOpenHelper {
    public static final String ACTIVITY_NAME = "ProfDB HELPER";
    private static final int VERSION_NUM = 3;
    //DATABASE VARIABLES
    public static final String DATABASE_NAME = "professors.db";
    public static final String TABLE_NAME = "professors_Table";
    //TABLE VARIABLES
    private final static String KEY_ROWID = "_id";
    public final static String KEY_NAME = "KEY_NAME";
    public final static String KEY_CLASS = "KEY_CLASS";
    public final static String KEY_TIME = "KEY_TIME";
    public final static String KEY_DAYS = "KEY_DAYS";
    public final static String[] MESSAGE_FIELDS = {KEY_ROWID, KEY_NAME, KEY_CLASS, KEY_TIME, KEY_DAYS};

    //CREATE TABLE IF NOT EXISTS Messages_Table (KEY_ROWID INTEGER PRIMARY KEY AUTOINCREMENT, KEY_NAME text, KEY_CLASS text, KEY_TIME text);
    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " text, " + KEY_CLASS + " text, " + KEY_TIME + " text, " + KEY_DAYS + " text);";

    public ProfDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase professors){
        try {
            professors.execSQL(CREATE_TABLE);
            Log.i(ACTIVITY_NAME, " onCreate");
        } catch (Exception e){
            Log.e(ACTIVITY_NAME, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase professors, int oldVersion, int newVersion) {
        try {
            professors.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(professors);
            Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
        } catch (Exception e){
            Log.e(ACTIVITY_NAME, e.toString());
        }
    }//onUpgrade

}//end Class ChatDatabaseHelper
