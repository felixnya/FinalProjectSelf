package com.example.robertjackson.finalproject;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class StartActivity_Schedual_Planner extends AppCompatActivity {
    final CharSequence str = "";
    String ACTIVITY_NAME = "StartActivity";
    int number_of_clicks = 0;
    boolean thread_started = false;
    int DELAY_BETWEEN_CLICKS_IN_MILLISECONDS = 250;
    CourseDatabaseHelper_Schedual_Planner courseHelper;
    SQLiteDatabase courseData;
    Cursor cursor;
    // Course  curse = new Course();
    Intent data1;
    Toast toast;
    Bundle bundle;
    Button bt1;
    Button college;

    // Snackbar snackBar;
    String str1;
    int week1;  // this is to pass the value of week to onclick local routine.
    int hour1;
    int HOUR = 14;  //  time row and first row , total 14 rows
    int WEEKDAY = 6;  // weekday and first column (time), total 6 columns.
    Button[][] btnTime = new Button[WEEKDAY][HOUR];  //  COLS first, please note
    boolean isThisATablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_schedual_planner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        refreshGui();  //reload the GUI .

        college = (Button) findViewById(R.id.button00);
        //Click this button will start ListViewActivity.
        college.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent listCourse = new Intent(StartActivity_Schedual_Planner.this, ListViewActivity_Schedual_Planner.class);
                startActivity(listCourse);
            }
        });

        //This is to set listener to every button used in time table matrix.
        for (int week = 1; week < 6; week++) {
            for (int hour = 1; hour < 14; hour++) {

                btnTime[week][hour].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ++number_of_clicks;
                        String str;
                        int idb;
                        int temp;

                        //find the information associated with the button , used to pass to inputActivity to show day and hour.
                        for (int i = 0; i < WEEKDAY; i++) {
                            for (int j = 0; j < HOUR; j++) {
                                temp = j + 7;
                                str = "button" + i + temp;

                                int resID = getResources().getIdentifier(str, "id", getPackageName());
                                if (resID == v.getId()) {
                                    week1 = i;
                                    hour1 = temp;
                                }
                            }
                        }

                        //  long max = courseData.getMaximumSize();
                        //  cursor = courseData.query(false, "courseTable",new String[]{"courseName"},"daySchedule = ? AND startTime = ?", new String[]{"Monday","8"},null, null, null, null);

                        //  cursor = courseData.query(false, "courseTable",new String[]{"courseID", "courseName"},null, null ,null, null, null, null);


                        if (!thread_started) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    thread_started = true;
                                    try {
                                        Thread.sleep(DELAY_BETWEEN_CLICKS_IN_MILLISECONDS);
                                        if (number_of_clicks == 1) {   //single click the button.

                                            String weekday = getWeek(week1);

                                            cursor = courseData.query(false, "courseTable", new String[]{"courseID", "courseName", "classRoom", "professorName", "daySchedule", "startTime", "duration"}, " daySchedule = ? AND startTime == ?", new String[]{weekday, hour1 + ""}, null, null, null, null);
                                            if (cursor.getCount() == 1) { // if there is no course, not execute this .
                                                cursor.moveToFirst();
                                                String tempName = cursor.getString(1);
                                                String ProfessorName = cursor.getString(3);
                                                hour1 = hour1 + cursor.getInt(6);
                                                //  String str1 =getString(hour1);//from +","+cursor.getInt(5)+" to "+ hour1;
                                                str1 = tempName + "\n Professor: " + ProfessorName;
                                                    /*\\ + "\n" + "from " + cursor.getInt(5) + " to " + hour1;*/
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        toast = Toast.makeText(getBaseContext(), str1, Toast.LENGTH_SHORT);
                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                                        toast.show();

                                                    }

                                                });
                                            } else {

                                                if (!isThisATablet) {
                                                    View coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
                                                    Snackbar snackbar = Snackbar
                                                            .make(coordinatorLayout, "There is no course at this time slot.", Snackbar.LENGTH_SHORT);
                                                    snackbar.show();
                                                } else // if this is a tablet with linearLayout
                                                {
                                                    // added in ID for linear layout
                                                   // View linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
                                                   // Snackbar snackbar = Snackbar
                                                     //       .make(linearLayout, "There is no course at this time slot.", Snackbar.LENGTH_SHORT);
                                                   // snackbar.show();
                                                }
                                            }
                                            // }
                                        } else if (number_of_clicks == 2) {  //double click to start InputActivity.
                                            //to pass the data to listViewActivity, which will use a fragment. To get the result,
                                            //have to put the data back to a fragment holder (only to store data) without layout.

                                            Intent inputData = new Intent(StartActivity_Schedual_Planner.this, InputFragmentHolder_Schedual_Planner.class);
                                            inputData.putExtra("Week", week1);
                                            inputData.putExtra("Hour", hour1);
                                            inputData.putExtra("requestCode", 11);

                                            if (!isThisATablet) {  //start
                                                startActivityForResult(inputData, 11);
                                            } else //this is a tablet
                                            {
                                                FragmentTransaction trans = getFragmentManager().beginTransaction();
                                                InputActivity_Schedual_Planner newFragment = new InputActivity_Schedual_Planner();
                                                Bundle extras = new Bundle();
                                                extras.putInt("Week", week1);
                                                extras.putInt("Hour", hour1);
                                                extras.putInt("requestCode", 11);
                                                newFragment.setArguments(extras);

                                                trans.replace(R.id.fragmentLocation, newFragment);
                                                trans.addToBackStack("any name");
                                                trans.commit();

                                            }
                                            //startActivity(inputData,bundle);
                                            //55 is my result code:

                                        }
                                        number_of_clicks = 0;
                                        thread_started = false;

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                    }
                });
            }
        }
    }

    public void refreshGui() {

        getButtonArray();

        isThisATablet = findViewById(R.id.fragmentLocation) != null;

        courseHelper = new CourseDatabaseHelper_Schedual_Planner(this);

        courseData = courseHelper.getWritableDatabase();

        cursor = courseData.query(false, "courseTable", new String[]{"courseID", "courseName", "classRoom", "professorName", "daySchedule", "startTime", "duration"}, null, null, null, null, null, null);

        int i = cursor.getCount();
        //loop through everything, make colour white with no text

        CharSequence buttonText;
        for (int ii = 1; ii < WEEKDAY; ii++) {
            for (int jj = 1; jj < HOUR; jj++) {
                buttonText = "";
                btnTime[ii][jj].setText(buttonText);
                btnTime[ii][jj].setBackground(this.getResources().getDrawable(R.drawable.coursebutton_schedual_planner));
                ;
            }
        }

        if (i > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                String temp5 = cursor.getString(4);
                int row = getRow(temp5);
                int col = cursor.getInt(5) - 7;  //startTime - 7 equals the column index number.
                buttonText = cursor.getString(0) + "\n" + cursor.getString(2);
                btnTime[row][col].setText(buttonText);
                btnTime[row][col].setBackgroundColor(0xff33b5e5);

                if (cursor.getInt(6) == 2) {
                    btnTime[row][col + 1].setBackgroundColor(0xff33b5e5);
                }

                cursor.moveToNext();
            }
        }

        if (isThisATablet) {

        }
    }

    public int getRow(String day) {

        int i = 0;
        if (day.equals("Monday")) i = 1;
        if (day.equals("Tuesday")) i = 2;
        if (day.equals("Wednesday")) i = 3;
        if (day.equals("Thursday")) i = 4;
        if (day.equals("Friday")) i = 5;

        return i;
    }

    public String getWeek(int j) {

        String i = null;
        if (j == 1) i = "Monday";
        if (j == 2) i = "Tuesday";
        if (j == 3) i = "Wednesday";
        if (j == 4) i = "Thursday";
        if (j == 5) i = "Friday";

        return i;
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        data1 = data;
        String s = "";
        int rtnWeek;
        int rtnHour;
        int rtnDuration;

        // if((requestCode == 11)&& (resultCode == 55))
        if ((resultCode == 55)) {  // 55 is from StartActivity to InputActity
            rtnWeek = data1.getIntExtra("Weekday", 0);   //get the data and set the button element of button matrix with course information
            rtnHour = data1.getIntExtra("Hourtime", 0);
            rtnDuration = data1.getIntExtra("duration", 1);

            try {
                btnTime[rtnWeek][rtnHour].setText(data1.getStringExtra("courseCode") + "\n" + data1.getStringExtra("classroomNumber"));
                btnTime[rtnWeek][rtnHour].setBackgroundColor(0xff33b5e5);

            } catch (Exception e) {
            }

            if (rtnDuration == 2) {  //if there hours is two hours, make the button below is the same color.

                btnTime[rtnWeek][rtnHour + 1].setBackgroundColor(0xff33b5e5);

            }
        }
    }

    /**
     * This method is to put the button into an array according the button's id in the layout
     */
    public void getButtonArray() {
        String resourceName;
        int resID;
        int temp;

        for (int i = 0; i < WEEKDAY; i++) {

            for (int j = 0; j < HOUR; j++) {

                if (j == 0) { // fill in the first row array, Monday, Tuesday, ........
                    resourceName = "button" + j + i;  // the frist row id is button 0
                    resID = getResources().getIdentifier(resourceName, "id", getPackageName());
                    btnTime[i][j] = (Button) findViewById(resID);
                }
                temp = j + 7;  // the button id is consistent with the real hours, so index + 7 is the name used. such as, at 8Am,
                // the index in the array is 1, but button name is button8.
                resourceName = "button" + i + temp; //
                resID = getResources().getIdentifier(resourceName, "id", getPackageName());
                btnTime[i][j] = (Button) findViewById(resID);

            }
        }
    }

    /**
     * toolbar override method.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_schedual_planner, menu);
        return true;
    }

    /**
     * override method required by Toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            /*
            case R.id.version_schedual_planner:
                Toast.makeText(getApplicationContext(),
                        " Written by Tongyu Wang \nAlgonquin College Time Schedule \n Version 1.0, November 15, 2016",
                        Toast.LENGTH_SHORT).show();
                break;
*/
            case R.id.instruction:

                View promptsView = LayoutInflater.from(StartActivity_Schedual_Planner.this).inflate(R.layout.help_schedual_planner, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        StartActivity_Schedual_Planner.this);
                alertDialogBuilder.setView(promptsView);
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

                break;
        }

        //Return false to allow normal menu processing to proceed,
        //true to consume it here.
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();


        Log.i(ACTIVITY_NAME, "in onStart()");
        //Read what's saved unACTIVITY_NAMEder the string "Number", If nothing, return 0
        //Get an object to write to the file:


    }

    @Override
    protected void onResume() {
        super.onResume();
        // setContentView(R.layout.activity_start);
        refreshGui();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "in onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "in onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "in onDestory()");
    }
}
