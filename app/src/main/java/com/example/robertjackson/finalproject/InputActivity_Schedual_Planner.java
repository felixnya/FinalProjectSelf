package com.example.robertjackson.finalproject;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InputActivity_Schedual_Planner extends Fragment {
    String ACTIVITY_NAME = "InputActivity";
    CourseDatabaseHelper_Schedual_Planner courseHelper;
    SQLiteDatabase courseData;
    ContentValues cValue = new ContentValues();
    Cursor cursor;
    ArrayList<Course_Schedual_Planner> courseArrayList= new ArrayList<Course_Schedual_Planner>();

    Button buttonweek;
    EditText textDay;
    EditText textStart;
    EditText textCourseID;
    EditText textCourseName;
    EditText textClassroom;
    EditText textProfessor;
    int requestCode;

    private RadioGroup radioGroup;
    private RadioButton radioHourButton;

    private RadioButton radio1;
    private RadioButton radio2;
    View fragmentView;
    Cursor cursor1;
    Button weekTime;
    Button btnAdd;
    Button btnDel;
    Button btnRtn;
    int week;
    int hour;
    int duration =1;
    String dayText = "";

    int hourtemp=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseHelper = new CourseDatabaseHelper_Schedual_Planner(getActivity());

        courseData = courseHelper.getWritableDatabase();

        boolean it = courseData.isOpen();


    }



    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        fragmentView = inflater.inflate(R.layout.activity_input_schedual_planner, null);
        Bundle args = this.getArguments();
        radioGroup = (RadioGroup)fragmentView.findViewById(R.id.radiohourgroup);

        textDay = (EditText)fragmentView.findViewById(R.id.editTextDay) ;
        textStart = (EditText)fragmentView.findViewById(R.id.editTextStart);
        textCourseID =(EditText)fragmentView.findViewById(R.id.editText1);
        textCourseName=(EditText)fragmentView.findViewById(R.id.editText2);
        textClassroom=(EditText)fragmentView.findViewById(R.id.editText3);
        textProfessor=(EditText)fragmentView.findViewById(R.id.editText4);


        radio1 =(RadioButton)fragmentView.findViewById(R.id.radio_1h) ;
        radio2=(RadioButton)fragmentView.findViewById(R.id.radio_2h);



        week = args.getInt("Week", 0);
        hour = args.getInt("Hour", 0);
        duration = args.getInt("Dura",1);
        requestCode = args.getInt("requestCode",0);
        // int hour1 =0;
        // hour1 = hour+7;
        // String resourceName = "button"+week+hour1;
        // int resID = getResources().getIdentifier(resourceName, "id", getPackageName());
        btnAdd = (Button) fragmentView.findViewById(R.id.button);
        btnDel = (Button)fragmentView.findViewById(R.id.buttonDel);
        btnRtn = (Button)fragmentView.findViewById(R.id.buttonRtn);
        //  weekTime = (Button)findViewById(R.id.buttonWeek);



        String buttonText ="";
        switch(week){
            case 1:
                dayText = "Monday";
                break;
            case 2:
                dayText = "Tuesday";
                break;
            case 3:
                dayText = "Wednesday";
                break;
            case 4:
                dayText = "Thursday";
                break;
            case 5:
                dayText = "Friday";
                break;

            default: dayText = "Invalid day";
                break;

        }

        //  hourtemp = hour+7;

        int tmpStart= 8;
        String tmpid ="";
        String tmpCourse="";
        String tmpRoom="";
        String tmpProfessor="";
        String tmpDay="";
        int tmpDuration =duration;


        cursor = courseData.query(false, "courseTable",new String[]{"courseID", "courseName", "classRoom", "professorName", "daySchedule", "startTime", "duration"}," daySchedule = ? AND startTime == ?", new String[]{dayText, hour+""}, null, null, null, null);
        if(cursor.getCount()==1) { // if there is no course, not execute this one click.
            cursor.moveToFirst();

            tmpid  = cursor.getString(0);
            tmpCourse = cursor.getString(1);
            tmpRoom = cursor.getString(2);
            tmpProfessor = cursor.getString(3);
            tmpDay = cursor.getString(4);
            tmpStart = cursor.getInt(5);
            tmpDuration =cursor.getInt(6);

            //  hour = hour + cursor.getInt(6);
        }

        textDay.setText(dayText);
        textStart.setText(hour+"");
        textCourseID.setText(tmpid);
        textCourseName.setText(tmpCourse);
        textClassroom.setText(tmpRoom);
        textProfessor.setText(tmpProfessor);

        if(tmpDuration==1)
            radioGroup.check(radio1.getId());
        else
            radioGroup.check(radio2.getId());


        btnDel.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog( getActivity() );
            dialog.setContentView(R.layout.delete_schedual_planner);
            dialog.setTitle("Delete a Course");
            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);

            text.setText("Do You Want to Delete This Course? ");
            // ImageView image = (ImageView) dialog.findViewById(R.id.image);
            //   image.setImageResource(R.drawable.ic_launcher);
            Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
            Button dialogButtonCancel=(Button) dialog.findViewById(R.id.dialogButtonCancel);
            // if button is clicked, close the custom dialog
            dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(cursor.getCount()==1) {
                        int i= courseData.delete(CourseDatabaseHelper_Schedual_Planner.TABLE_NAME, " daySchedule = ? AND startTime == ?", new String[]{dayText, hour + ""});
                        if(i==1) {
                            textCourseID.setText("");
                            textCourseName.setText("");
                            textClassroom.setText("");
                            textProfessor.setText("");

                        }

                    }

                    dialog.dismiss();
                    if(requestCode==11) {
//                        Intent intent = new Intent(InputActivity.this, StartActivity.class);
                        //                      InputActivity.this.startActivity(intent);
                        // getActivity().finish();
                        Intent intent = new Intent(getActivity(), StartActivity_Schedual_Planner.class);
                        InputActivity_Schedual_Planner.this.startActivity(intent);
                    }

                    if(requestCode==15) {
                        Intent intent = new Intent(getActivity(), ListViewActivity_Schedual_Planner.class);
                        InputActivity_Schedual_Planner.this.startActivity(intent);
                        //getActivity().finish();
                    }
                }
            });

            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            } );

            dialog.show();

        }
        });

        btnRtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(requestCode==11) {
                    Intent intent = new Intent(getActivity(), StartActivity_Schedual_Planner.class);
                    InputActivity_Schedual_Planner.this.startActivity(intent);
                    //  getActivity().finish();
                }

                if(requestCode==15) {
                   /* Intent intent = new Intent(InputActivity.this, ListViewActivity.class);
                    InputActivity.this.startActivity(intent);
                    */
                    getActivity().finish();
                }
                //finish();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioHourButton = (RadioButton) fragmentView.findViewById(selectedId);

                if (radioHourButton.getText().equals("1 hour"))
                    duration = 1;
                else
                    duration = 2;


                String day =textDay.getText().toString();
                String startTime = textStart.getText().toString();

                String courseid = textCourseID.getText().toString();
                String coursename = textCourseName.getText().toString();
                String classroom = textClassroom.getText().toString();
                String professor = textProfessor.getText().toString();



                cValue.put(CourseDatabaseHelper_Schedual_Planner.COURSE_ID, courseid);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.COURSE_NAME, coursename);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.CLASS_ROOM, classroom);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.PROFESSOR_NAME, professor);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.DAY_SCHEDULE, day);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.START_TIME, startTime);
                cValue.put(CourseDatabaseHelper_Schedual_Planner.DURATION, duration);

                //   String day1 = cursor.getString(4);
                //  String hour1 = cursor.getString(5);


                try {
                    if(cursor.getCount()==1 && day.equals(dayText)&&startTime.equals(hour+"")){
                        courseData.delete(CourseDatabaseHelper_Schedual_Planner.TABLE_NAME, " daySchedule = ? AND startTime = ?", new String[]{dayText, hour + ""});

                    }
                    courseData.insertOrThrow(CourseDatabaseHelper_Schedual_Planner.TABLE_NAME, " null ", cValue);
                    cursor1 = courseData.query(false, CourseDatabaseHelper_Schedual_Planner.TABLE_NAME, new String[]{CourseDatabaseHelper_Schedual_Planner.COURSE_ID, CourseDatabaseHelper_Schedual_Planner.COURSE_NAME}, null, null, null, null, null, null);
                    cursor1.moveToFirst();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i = cursor1.getCount();
                String temps = cursor1.getString(0);
                String temp2 = cursor1.getString(1);

                // messageAdapter.notifyDataSetChanged();
                // chatEditText.setText("");

                if(requestCode==15) {
//                    Intent intent = new Intent(InputActivity.this, ListViewActivity.class);
                    //                  InputActivity.this.startActivity(intent);
                    Intent intent = new Intent(getActivity(), ListViewActivity_Schedual_Planner.class);
                    InputActivity_Schedual_Planner.this.startActivity(intent);
                    // getActivity().finish();
                }else {
                    // Intent backIntent = new Intent();
                    Intent backIntent = new Intent(getActivity(), StartActivity_Schedual_Planner.class);
                    backIntent.putExtra("courseCode", ((EditText) fragmentView.findViewById(R.id.editText1)).getText().toString());
                    // backIntent.putExtra("courseName",((EditText) findViewById(R.id.editText2)).getText().toString());
                    backIntent.putExtra("classroomNumber", ((EditText) fragmentView.findViewById(R.id.editText3)).getText().toString());
                    // backIntent.putExtra("professorName",((EditText) findViewById(R.id.editText4)).getText().toString());
                    backIntent.putExtra("Weekday", getRow(day));
                    backIntent.putExtra("Hourtime", Integer.parseInt(startTime)-7);
                    backIntent.putExtra("duration", duration);

                    //55 is my result code:
                    getActivity().setResult(55, backIntent);
                    courseData.close();


                    InputActivity_Schedual_Planner.this.startActivity(backIntent);
                    //getActivity().finish();
                }

            }


        });
        return fragmentView;
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

/*
    @Override
    protected void onStart(){
        super.onStart();


        Log.i(ACTIVITY_NAME, "in onStart()");
        //Read what's saved unACTIVITY_NAMEder the string "Number", If nothing, return 0
        //Get an object to write to the file:




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
*/

}
