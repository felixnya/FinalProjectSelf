package com.example.robertjackson.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class ProfessorDetailFragmentProfessorActivity extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    private String mItem;

    public ProfessorDetailFragmentProfessorActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getString(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("ProfessorProfessorActivity Details");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.professor_detail_activity, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.professor_detail)).setText(mItem);
        }

        return rootView;
    }
}
