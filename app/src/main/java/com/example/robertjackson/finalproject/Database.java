package com.example.robertjackson.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Robert Jackson on Nov 24 2016.
 * Database.java
 */

/**
 * @name Database
 * @return void
 */
class Database extends SQLiteOpenHelper {
    private static final int VERSION_NUM = 201;
    private String CREATE_ONE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_ONE_NAME + "(" + TableData.TableInfo.KEY_ONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TableData.TableInfo.KEY_ONE_PNAME + " TEXT, " + TableData.TableInfo.KEY_ONE_PCODE + " TEXT," + TableData.TableInfo.KEY_ONE_PSTART + " TEXT," +
            TableData.TableInfo.KEY_ONE_PEND + " TEXT);";

    private String CREATE_TWO_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_TWO_NAME + "( "
            + TableData.TableInfo.KEY_TWO_PROJECTNAME + " TEXT, " + TableData.TableInfo.KEY_TWO_FIRSTNAME + " TEXT," + TableData.TableInfo.KEY_TWO_LASTNAME + " TEXT);";

    public Database(Context ctx) {
        super(ctx, TableData.TableInfo.DATABASE_NAME, null, VERSION_NUM);
        Log.d("Database Operations", "Database Created");
    }

    /**
     * @return void
     * @name onCreate
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_ONE_QUERY);
            db.execSQL(CREATE_TWO_QUERY);
            Log.d("Database Operations", "Tables is Created");
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
    }

    /**
     * @return void
     * @name onUpgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_ONE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_TWO_NAME + ";");
            Log.d("Database Operations", "Tables upgraded");
            onCreate(db);
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
    }

    /**
     * @return void
     * @name onInsertProject
     */
    public void onInsertProject(Database cDh, String pName, String cCode, String pStart, String pEnd) {
        try {
            SQLiteDatabase SQL = cDh.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.TableInfo.KEY_ONE_PNAME, pName);
            cv.put(TableData.TableInfo.KEY_ONE_PCODE, cCode);
            cv.put(TableData.TableInfo.KEY_ONE_PSTART, pStart);
            cv.put(TableData.TableInfo.KEY_ONE_PEND, pEnd);
            SQL.insert(TableData.TableInfo.TABLE_ONE_NAME, null, cv);

            Log.d("Database Operations", "One raw project inserted");
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
    }

    /**
     * @return void
     * @name onInsertStudent
     */
    public void onInsertStudent(Database cDh, String studentFirstname, String studentLastname, String projectName) {
        try {
            SQLiteDatabase SQL = cDh.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TableData.TableInfo.KEY_TWO_PROJECTNAME, projectName);
            cv.put(TableData.TableInfo.KEY_TWO_FIRSTNAME, studentFirstname);
            cv.put(TableData.TableInfo.KEY_TWO_LASTNAME, studentLastname);
            SQL.insert(TableData.TableInfo.TABLE_TWO_NAME, null, cv);

            Log.d("Database Operations", "One raw student inserted");

        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
    }

    public void onDeleteStudent(Database cDh, String studentProjectName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TableData.TableInfo.TABLE_TWO_NAME + " WHERE " + TableData.TableInfo.KEY_TWO_PROJECTNAME + "='" + studentProjectName + "';");
            Log.d("Database Operations", "student dropped");
            db.close();
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }

    }

    public void onDeleteProject(Database cDh, String projectName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TableData.TableInfo.TABLE_ONE_NAME + " WHERE " + TableData.TableInfo.KEY_ONE_PNAME + "='" + projectName + "';");
            Log.d("Database Operations", "project dropped");
            db.close();
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }

    }

    /**
     * @return Cursor
     * @name getProjectInfo
     */
    public Cursor getProjectInfo(Database cdh) {
        try {
            SQLiteDatabase SQ = cdh.getReadableDatabase();
            String[] message = {TableData.TableInfo.KEY_ONE_PNAME, TableData.TableInfo.KEY_ONE_PCODE, TableData.TableInfo.KEY_ONE_PSTART,
                    TableData.TableInfo.KEY_ONE_PEND};
            Log.d("Database Operations", "Project information received");
            return SQ.query(TableData.TableInfo.TABLE_ONE_NAME, message, null, null, null, null, null);
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
        return null;
    }

    /**
     * @return Cursor
     * @name getStudentInfo
     */
    public Cursor getStudentInfo(Database cdh) {
        try {
            SQLiteDatabase SQ = cdh.getReadableDatabase();
            String[] message = {TableData.TableInfo.KEY_TWO_FIRSTNAME, TableData.TableInfo.KEY_TWO_LASTNAME, TableData.TableInfo.KEY_TWO_PROJECTNAME};
            Log.d("Database Operations", "Student Information received");
            return SQ.query(TableData.TableInfo.TABLE_TWO_NAME, message, null, null, null, null, null);
        } catch (Exception e) {
            Log.e("SQL DATABASE ERROR", e.toString());
        }
        return null;
    }

}