package com.example.robertjackson.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainMenu_Project_Planner extends AppCompatActivity {
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_project_planner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Algonquin Planner");
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu m) {
        try {
            getMenuInflater().inflate(R.menu.main_menu_project_planner, m);
            return true;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        try {
            int id = mi.getItemId();

            switch (id) {
                case R.id.action_one:
                    Intent ffs = new Intent(getApplicationContext(), ProjectListActivity_Project_Planner.class);
                    startActivity(ffs);

                    break;
                case R.id.action_two:
                    Intent ffs2 = new Intent(getApplicationContext(), StartActivity_Schedual_Planner.class);
                    startActivity(ffs2);

                    break;
                case R.id.action_three:
                    Intent ffs3 = new Intent(getApplicationContext(), ProfessorListActivity.class);
                    startActivity(ffs3);

                    break;
                case R.id.about:

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    mainView = inflater.inflate(R.layout.dialog_box_project_planner, null);
                    builder.setView(mainView);
                    EditText et = (EditText) mainView.findViewById(R.id.dialogboxText);
                    et.setText("\n\n\nStudent: Robert Jackson Student Number: 040627795");

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
}
