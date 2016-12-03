package com.example.robertjackson.finalproject;

import android.provider.BaseColumns;

/**
 * Created by Robert Jackson on 10/14/2016.
 * Static fields for database, simply for ease of use.
 * TableData.java
 */

class TableData_Project_Planner {

    public TableData_Project_Planner() {

    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String DATABASE_NAME = "final.db";


        public static final String TABLE_ONE_NAME = "Project";
        public static final String KEY_ONE_ID = "ProjectID";
        public static final String KEY_ONE_PNAME = "ProjectName";
        public static final String KEY_ONE_PCODE = "ProjectClassCode";
        public static final String KEY_ONE_PSTART = "ProjectStart";
        public static final String KEY_ONE_PEND = "ProjectEnd";

        public static final String TABLE_TWO_NAME = "Student";
        public static final String KEY_TWO_FIRSTNAME = "FirstName";
        public static final String KEY_TWO_LASTNAME = "LastName";
        public static final String KEY_TWO_PROJECTNAME = "ProjectName";
    }
}
