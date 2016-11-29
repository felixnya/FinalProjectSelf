package com.example.robertjackson.finalproject;
/**
 * Created by Robert Jackson on Nov 24 2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * @name TestToolbar
 * Toolbar class
 */
public class TestToolbar extends AppCompatActivity {

    @Override
    /**
    *@name OnCreate
    *@param savedInstanceState
     * @return void
     * used to help establish the action bar
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}