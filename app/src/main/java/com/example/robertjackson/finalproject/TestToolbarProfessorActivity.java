package com.example.robertjackson.finalproject;
/**
 * Created by Robert Jackson on Nov 24 2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class TestToolbarProfessorActivity extends AppCompatActivity {

    @Override
    /**
     *@name OnCreate
     *@param savedInstanceState
     * @return void
     * used to help establish the action bar
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar_professor_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
