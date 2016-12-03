package com.example.robertjackson.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by wty on 2016-10-10.
 */
public class CourseDatabaseHelper_Schedual_Planner extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "courseTable";
    public static final String COURSE_ID = "courseID";
    public static final String COURSE_NAME = "courseName";
    public static final String CLASS_ROOM = "classRoom";
    public static final String PROFESSOR_NAME = "professorName";
    public static final String DAY_SCHEDULE = "daySchedule";
    public static final String START_TIME = "startTime";
    public static final String DURATION = "duration";


    public static final String DATABASE_NAME = "CourseDatabase.db";
    public static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    // private static final String DATABASE_CREATE = "CREATE TABLE comments ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +"comment TEXT );";
    //protected static final String DATABASE_CREATE = "CREATE TABLE "+ ChatDatabaseHelper.TABLE_NAME + " ( " + ChatDatabaseHelper.KEY_ID +" INTEGER AUTOINCREMENT PRIMARY KEY , " + ChatDatabaseHelper.KEY_MESSAGE + " TEXT);";


    public CourseDatabaseHelper_Schedual_Planner(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        // db.execSQL(DATABASE_CREATE);

        //Log.i("debug ", DATABASE_CREATE);

        //  db.execSQL(DATABASE_CREATE);

        db.execSQL("CREATE TABLE " + CourseDatabaseHelper_Schedual_Planner.TABLE_NAME + " (" + CourseDatabaseHelper_Schedual_Planner.COURSE_ID + " text primary key , "
                + CourseDatabaseHelper_Schedual_Planner.COURSE_NAME + " text , "
                + CourseDatabaseHelper_Schedual_Planner.CLASS_ROOM + " text , "
                + CourseDatabaseHelper_Schedual_Planner.PROFESSOR_NAME + " text , "
                + CourseDatabaseHelper_Schedual_Planner.DAY_SCHEDULE + " text , "
                + CourseDatabaseHelper_Schedual_Planner.START_TIME + " integer , "
                + CourseDatabaseHelper_Schedual_Planner.DURATION + " integer );"
        );

        Log.i("CourseDatabaseHelper", "Calling onCreate");

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("CourseDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);

        Log.i("CourseDatabaseHelper", "Calling onUpgrade");

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("CourseDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);

        Log.i("CourseDatabaseHelper", "Calling onDowngrade");

    }


}