package com.example.robertjackson.finalproject;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class InputFragmentHolder_Schedual_Planner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_fragment_holder_schedual_planner);



        FragmentTransaction trans = getFragmentManager().beginTransaction();
        InputActivity_Schedual_Planner newFragment = new InputActivity_Schedual_Planner();
        newFragment.setArguments(getIntent().getExtras()); //pass intent extras to bundle
        trans.replace(R.id.fragmentLocation, newFragment);
        trans.addToBackStack("any name");
        trans.commit();
    }


}
