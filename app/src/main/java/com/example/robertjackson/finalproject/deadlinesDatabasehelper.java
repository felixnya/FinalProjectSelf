package com.example.robertjackson.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class deadlinesDatabasehelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "Tasks.db";
    static int VERSION_NUM = 1;
    static String table_name = "Tasks";
    static String KEY_ID = "id";
    static String KEY_TASK = "task";
    static String KEY_DATE = "date";

    public deadlinesDatabasehelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String CREATE_TABLE = "CREATE TABLE " + table_name + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TASK + " TASK, " + KEY_DATE + " DATE"
                + ")";
        db.execSQL(CREATE_TABLE);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
// Creating tables again
        onCreate(db);
    }
}
