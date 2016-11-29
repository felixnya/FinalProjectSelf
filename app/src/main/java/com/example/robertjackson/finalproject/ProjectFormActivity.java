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
 * ProjectFormActivity.java
 */

/**
 * @name ProjectFormActivity
 * @return void
 * Activity for project input form
 */
public class ProjectFormActivity extends AppCompatActivity {
    private View projectView;
    private Context ctx = this;
    private Database tempDb = new Database(ctx);
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;

    /**
     * @param savedInstanceState
     * @return void
     * On creation of activity sets content view including edit fields and buttons
     * on button click it takes all the items in each edit field and inserts it into a database
     * @name onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Project Form");
        setSupportActionBar(toolbar);

        Button b1 = (Button) findViewById(R.id.projectFormButton1);
        Button b2 = (Button) findViewById(R.id.projectFormButton2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    et1 = (EditText) findViewById(R.id.pEt1);
                    et2 = (EditText) findViewById(R.id.pEt2);
                    et3 = (EditText) findViewById(R.id.pEt3);
                    et4 = (EditText) findViewById(R.id.pEt4);
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
                    } else if (et4.getText().toString().equals("")) {
                        et4.setError("This field can not be blank");
                        return;
                    }
                    insertQuery iq = new insertQuery();
                    iq.execute(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString());
                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (et1.getText().toString().equals("")) {
                        et1.setError("This field can not be blank");
                        return;
                    }
                    et1 = (EditText) findViewById(R.id.pEt1);
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
     * Inflates toolbar
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
                    projectView = inflater.inflate(R.layout.dialog_box, null);
                    builder.setView(projectView);
                    EditText et = (EditText) projectView.findViewById(R.id.dialogboxText);
                    et.setText("ProjectFormActivity, a 4 edit field activity, you must have a string in each, as it will" +
                            "create an alert error telling you to fill in the field. You must also format the date exactly, as it will" +
                            "then judge the format against the current date, to determine if the project is late or not." +
                            "Just fill in the fields and insert or delete a project." +
                            "\n\n\nStudent: Robert Jackson Student Number: 040627795");
                    AlertDialog dialog = builder.create();
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
                bar = (ProgressBar) findViewById(R.id.progressBarProject);
                bar.setVisibility(View.VISIBLE);
                bar.setProgress(progress[0]);
                SystemClock.sleep(300);
                if (progress[0] == 100) {
                    bar.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }

        protected void onPostExecute(String result) {
            try {
                Toast.makeText(getBaseContext(), "Project " + et1.getText().toString() + " has been inserted.", Toast.LENGTH_LONG).show();
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
                tempDb.onInsertProject(tempDb, params[0], params[1], params[2], params[3]);
                return "Insert Successful";
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return "Insert Failed";
        }
    }

    public class deleteQuery extends AsyncTask<String, Integer, String> {
        ProgressBar bar;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            try {
                bar = (ProgressBar) findViewById(R.id.progressBarProject);
                bar.setVisibility(View.VISIBLE);
                bar.setProgress(progress[0]);
                SystemClock.sleep(300);
                if (progress[0] == 100) {
                    bar.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
        }

        protected void onPostExecute(String result) {
            try {
                Toast.makeText(getBaseContext(), "Project " + et1.getText().toString() + " has been inserted.", Toast.LENGTH_LONG).show();
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
                tempDb.onDeleteProject(tempDb, params[0]);
                return "Delete successful";
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return "Delete Failed";
        }
    }
}
