package com.example.robertjackson.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Robert Jackson on Nov 24 2016.
 * StudentFormActivity.java
 */

/**
 * @name StudentFormActivity
 * @return void
 * Student form activity
 */
public class StudentFormActivity extends AppCompatActivity {
    private View studentView;
    private Context ctx = this;
    private Database tempDb = new Database(ctx);


    private EditText et1, et2, et3;
    private Button b1, b2;

    /**
     * @param savedInstanceState
     * @return void
     * on create for student activity, used to setcontent view with layout, and to
     * place buttons and edit text fields, button takes information from all fields, and then
     * places them in the database.
     * @name onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Student Form");
        setSupportActionBar(toolbar);

        b1 = (Button) findViewById(R.id.studentButton);
        b2 = (Button) findViewById(R.id.studentButton2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    et1 = (EditText) findViewById(R.id.sEt1);
                    et2 = (EditText) findViewById(R.id.sEt2);
                    et3 = (EditText) findViewById(R.id.sEt3);
                    if (et1.getText().toString().equals("")) {
                        et1.setError("This field can not be blank");
                        return;
                    } else if (et2.getText().toString().equals("")) {
                        et2.setError("This field can not be blank");
                        et2.setFocusable(true);
                        return;
                    } else if (et3.getText().toString().equals("")) {
                        et3.setError("This field can not be blank");
                        return;
                    }

                    for (char c2 : et2.getText().toString().toCharArray()) {
                        if (Character.isDigit(c2)) {
                            et2.setError("This field can not have a numeric");
                            et2.setFocusable(true);
                            return;
                        }
                    }
                    for (char c3 : et3.getText().toString().toCharArray()) {
                        if (Character.isDigit(c3)) {
                            et3.setError("This field can not have a numeric");
                            return;
                        }
                    }
                    insertQuery iq = new insertQuery();
                    iq.execute(et1.getText().toString(), et2.getText().toString(), et3.getText().toString());
                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    et1 = (EditText) findViewById(R.id.sEt1);
                    if (et1.getText().toString().equals("")) {
                        et1.setError("This field can not be blank");
                        return;
                    }

                    for (char c2 : et2.getText().toString().toCharArray()) {
                        if (Character.isDigit(c2)) {
                            et2.setError("This field can not have a numeric");
                            et2.setFocusable(true);
                            return;
                        }
                    }
                    for (char c3 : et3.getText().toString().toCharArray()) {
                        if (Character.isDigit(c3)) {
                            et3.setError("This field can not have a numeric");
                            return;
                        }
                    }
                    deleteQuery dq = new deleteQuery();
                    dq.execute(et1.getText().toString());
                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }
            }
        });

    }

    /**
     * @param m
     * @return boolean
     * inflates the toolbar menu
     * @name onCreateOptionsMenu
     */

    public boolean onCreateOptionsMenu(Menu m) {
        try {
            getMenuInflater().inflate(R.menu.toolbar_menu, m);
            return true;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return false;
    }

    /**
     * @param mi
     * @return boolean
     * Inflates layout based on option selected for a menu, for example, inflates an
     * about alert box for when the about item is selected.
     * @name onOptionsItemSelected
     */
    public boolean onOptionsItemSelected(MenuItem mi) {
        try {
            int id = mi.getItemId();

            switch (id) {
                case R.id.action_one:
                    finish();

                    break;
                case R.id.about:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    studentView = inflater.inflate(R.layout.dialog_box, null);

                    builder.setView(studentView);
                    EditText et = (EditText) studentView.findViewById(R.id.dialogboxText);
                    et.setText("StudentFormActivity. It is an activity with 3 edit text fields. Each of these fields has error checking" +
                            "that you must have a string, not an empty one. As well as a non numeric for your names." +
                            "With that being said, enter in the information of the student you wish to insert or delete. "+
                            "\n\n\nStudent: Robert Jackson Student Number: 040627795");


                    AlertDialog dialog = builder.create();
                    dialog.setIcon(R.drawable.info);
                    dialog.show();

                    break;

            }
            return true;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return false;
    }

    public class insertQuery extends AsyncTask<String, Integer, String> {
        ProgressBar bar;

        @Override
        protected void onProgressUpdate(Integer... progress) {

            try {
                bar = (ProgressBar) findViewById(R.id.progressBarStudent);
                bar.setVisibility(View.VISIBLE);
                bar.setProgress(progress[0]);
                if (progress[0] == 100) {
                    bar.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }

        protected void onPostExecute(String result) {
            try {
                Toast.makeText(getBaseContext(), "Student " + et2.getText().toString() + " " + et3.getText().toString() + " has been inserted.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }

        protected String doInBackground(String... params) {
            try {
                int progress = 0;
                while (progress < 100) {
                    publishProgress(progress += 20);
                    SystemClock.sleep(300);
                }
                tempDb.onInsertStudent(tempDb, params[0], params[1], params[2]);
                return "Do in backround Success";
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return "Do in backround failed";
        }
    }

    public class deleteQuery extends AsyncTask<String, Integer, String> {
        ProgressBar bar;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            try {
                bar = (ProgressBar) findViewById(R.id.progressBarStudent);
                bar.setVisibility(View.VISIBLE);
                bar.setProgress(progress[0]);
                if (progress[0] == 100) {
                    bar.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }

        protected void onPostExecute(String result) {
            try {
                Toast.makeText(getBaseContext(), "Student " + et2.getText().toString() + " " + et3.getText().toString() + " has been deleted.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }


        protected String doInBackground(String... params) {
            try {
                int progress = 0;
                while (progress < 100) {
                    publishProgress(progress += 20);
                    SystemClock.sleep(300);
                }
                tempDb.onDeleteStudent(tempDb, params[0]);
                return "do in backround failed";
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }

            return "do in backround success";
        }
    }
}