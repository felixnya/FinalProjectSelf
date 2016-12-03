package com.example.robertjackson.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity_Schedual_Planner extends Activity {
    String ACTIVITY_NAME = "InputActivity";
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    Map<String, List<Course_Schedual_Planner>> listDataChild;
    List<Course_Schedual_Planner> monday;
    List<Course_Schedual_Planner> tuesday;
    List<Course_Schedual_Planner> wednesday;
    List<Course_Schedual_Planner> thursday;
    List<Course_Schedual_Planner> friday;
    ProgressBar progressBar;
    CourseDatabaseHelper_Schedual_Planner courseHelper;
    SQLiteDatabase courseData;
    ContentValues cValue = new ContentValues();
    Cursor cursor;
    Button btnAC;

    int number_of_clicks =0;
    boolean isThisATablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_schedual_planner);

        isThisATablet = findViewById(R.id.fragmentLocation) != null;


        progressBar = new ProgressBar(ListViewActivity_Schedual_Planner.this);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        btnAC = (Button)findViewById(R.id.buttonAC);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Course_Schedual_Planner>>();

        // preparing list data
        new PrepareListData().execute("");

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        listAdapter.notifyDataSetChanged();
        // setting list adapter
        expListView.setAdapter(listAdapter);


        btnAC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ListViewActivity_Schedual_Planner.this, StartActivity_Schedual_Planner.class);
                ListViewActivity_Schedual_Planner.this.startActivity(intent);
            }
            //finish();

        });

    }

    public class PrepareListData extends AsyncTask<String, Integer, String> {


        @Override
        public String doInBackground(String... args) {

            String tmpid = "";
            String tmpCourse = "";
            String tmpRoom = "";
            String tmpProfessor = "";
            int tmpDuration = 0;
            int tmpStart =0;
            int tmpEnd =0;
            String tmpStartEnd;
            String whichday = "";

            monday = new ArrayList<Course_Schedual_Planner>();
            tuesday = new ArrayList<Course_Schedual_Planner>();;
            wednesday = new ArrayList<Course_Schedual_Planner>();;
            thursday = new ArrayList<Course_Schedual_Planner>();;
            friday = new ArrayList<Course_Schedual_Planner>();;
            progressBar = (ProgressBar)findViewById(R.id.progressBar);

            // Adding child data

            String day1 = getResources().getString(R.string.mon);
            String day2 = getResources().getString(R.string.tue);
            String day3 = getResources().getString(R.string.wed);
            String day4 = getResources().getString(R.string.thu);
            String day5 = getResources().getString(R.string.fri);


            listDataHeader.add(day1);
            listDataHeader.add(day2);
            listDataHeader.add(day3);
            listDataHeader.add(day4);
            listDataHeader.add(day5);

            try {
                courseHelper = new CourseDatabaseHelper_Schedual_Planner(ListViewActivity_Schedual_Planner.this);

                courseData = courseHelper.getWritableDatabase();
                cursor = courseData.query(true, "courseTable", new String[]{"courseID", "courseName", "classRoom", "professorName", "daySchedule", "startTime", "duration"}, null, null, null, null, null, null);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(300);
                publishProgress(25);
            } catch (Exception e) {
            }

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {


                    tmpid = cursor.getString(0);  //
                    tmpCourse = cursor.getString(1);//
                    tmpRoom = cursor.getString(2);//
                    tmpProfessor = cursor.getString(3);//
                    whichday = cursor.getString(4);
                    tmpStart = cursor.getInt(5);
                    tmpDuration = cursor.getInt(6);
                    tmpEnd = tmpStart + tmpDuration;
                    tmpStartEnd = tmpStart + ":00  to " + tmpEnd + ":00";//
                    switch (whichday) {
                        case "Monday":
                            monday.add(new Course_Schedual_Planner(tmpid, tmpCourse, tmpRoom, tmpProfessor, whichday, tmpStartEnd, tmpStart));
                            break;
                        case "Tuesday":
                            tuesday.add(new Course_Schedual_Planner(tmpid, tmpCourse, tmpRoom, tmpProfessor, whichday, tmpStartEnd, tmpStart));
                            break;
                        case "Wednesday":
                            wednesday.add(new Course_Schedual_Planner(tmpid, tmpCourse, tmpRoom, tmpProfessor, whichday, tmpStartEnd, tmpStart));
                            break;
                        case "Thursday":
                            thursday.add(new Course_Schedual_Planner(tmpid, tmpCourse, tmpRoom, tmpProfessor, whichday, tmpStartEnd, tmpStart));
                            break;
                        case "Friday":
                            friday.add(new Course_Schedual_Planner(tmpid, tmpCourse, tmpRoom, tmpProfessor, whichday, tmpStartEnd, tmpStart));
                            break;
                        default:

                    }
                    cursor.moveToNext();

                }
            }

            try {
                Thread.sleep(300);
                publishProgress(50);
            } catch (Exception e) {
            }
            Collections.sort(monday, new Comparator<Course_Schedual_Planner>() {

                @Override
                public int compare(Course_Schedual_Planner o1, Course_Schedual_Planner o2) {
                    return o1.getStart() - o2.getStart();
                }
            });

            listDataChild.put(listDataHeader.get(0), monday); // Header, Child data
            listDataChild.put(listDataHeader.get(1), tuesday);
            listDataChild.put(listDataHeader.get(2), wednesday);
            listDataChild.put(listDataHeader.get(3), thursday);
            listDataChild.put(listDataHeader.get(4), friday);
            try {
                Thread.sleep(300);
                publishProgress(75);
            } catch (Exception e) {
            }


            return "";
        }

        @Override
        public void onProgressUpdate (Integer...value){

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        public void onPostExecute (String str){

            try {
                Thread.sleep(300);
                progressBar.setProgress(100);
            } catch (Exception e) {
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }

            progressBar.setVisibility(View.INVISIBLE);
        }

    }



    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private Map<String, List<Course_Schedual_Planner>> _listDataChild;



        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     Map<String, List<Course_Schedual_Planner>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Course_Schedual_Planner getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final Course_Schedual_Planner childText = (Course_Schedual_Planner) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_schedual_planner, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            TextView txtListChild2 = (TextView) convertView
                    .findViewById(R.id.lblListItem2);

            TextView txtListChild3 = (TextView) convertView
                    .findViewById(R.id.lblListItem3);

            TextView txtListChild4 = (TextView) convertView
                    .findViewById(R.id.lblListItem4);

            TextView txtListChild5 = (TextView) convertView
                    .findViewById(R.id.lblListItem5);

            String code = getResources().getString(R.string.code);
            String name = getResources().getString(R.string.name);
            String loca = getResources().getString(R.string.locatoin);
            String inst = getResources().getString(R.string.instructor);

            txtListChild.setText(childText.getStartEndTime());
            txtListChild2.setText(code+childText.getCourseId());
            txtListChild3.setText(name+childText.getCourseName());
            txtListChild4.setText(loca+childText.getClassRoom());
            txtListChild5.setText(inst+childText.getInstructorName());




            convertView.setOnClickListener(new View.OnClickListener() {


                String week = childText.getDaySchedule();
                int week1 =getRow(week);
                int hour1 = childText.getStart();
                @Override
                public void onClick(View v) {
                    ++number_of_clicks;

                    if(number_of_clicks ==  2) {
                        Intent inputData = new Intent(ListViewActivity_Schedual_Planner.this, InputFragmentHolder_Schedual_Planner.class);
                        inputData.putExtra("Week", week1);
                        inputData.putExtra("Hour", hour1);
                        inputData.putExtra("requestCode", 15);
                        startActivityForResult(inputData, 15);
                        number_of_clicks=0;


                    }




                }
            });
            return convertView;
        }

        public int getRow(String day){

            int i=0;
            if(day.equals("Monday"))i=1;
            if(day.equals("Tuesday"))i=2;
            if(day.equals("Wednesday"))i=3;
            if(day.equals("Thursday"))i=4;
            if(day.equals("Friday"))i=5;

            return i;
        }


        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_schedual_planner, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);







            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        listAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart(){
        super.onStart();


        Log.i(ACTIVITY_NAME, "in onStart()");
        //Read what's saved unACTIVITY_NAMEder the string "Number", If nothing, return 0
        //Get an Course to write to the file:




    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "in onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "in onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "in onDestory()");
    }





}






