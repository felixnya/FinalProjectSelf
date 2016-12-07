package com.example.robertjackson.finalproject;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_Deadlines extends AppCompatActivity{

    protected static final String ACTIVITY_NAME = "MainActivity";
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String taskName, taskDate;
    deadlinesDatabasehelper dbHelper;
    SQLiteDatabase db;
    int item_id;
    ProgressBar bar;

    String about_message = "Dates & Deadlines Feature:  Version 1.0 by Van Toan Trinh. - Instructions: Add Tasks button is on the upper right hand corner. Please enter a task and the due date in the correct format.";
    List<String> Jan = new ArrayList<String>();
    List<String> Feb = new ArrayList<String>();
    List<String> Mar = new ArrayList<String>();
    List<String> Apr = new ArrayList<String>();
    List<String> May = new ArrayList<String>();
    List<String> Jun = new ArrayList<String>();
    List<String> Jul = new ArrayList<String>();
    List<String> Aug = new ArrayList<String>();
    List<String> Sep = new ArrayList<String>();
    List<String> Oct = new ArrayList<String>();
    List<String> Nov = new ArrayList<String>();
    List<String> Dec = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deadlines);

        dbHelper = new deadlinesDatabasehelper(this);

        //Adding Task to Database and Expandable List View
        expListView = (ExpandableListView) findViewById(R.id.days);




        ImageButton addTaskButton = (ImageButton) findViewById(R.id.addButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(MainActivity_Deadlines.this);
                // Get the layout inflater
                LayoutInflater inflater = MainActivity_Deadlines.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                customBuilder.setView(inflater.inflate(R.layout.add_layout_deadlines, null))
                        // Add action buttons
                        .setPositiveButton(R.string.dialog_button_add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog dial = (Dialog) dialog;
                                EditText task_input = (EditText) dial.findViewById(R.id.newTask);
                                EditText date_input = (EditText) dial.findViewById(R.id.newDate);
                                taskName = task_input.getText().toString();
                                taskDate = date_input.getText().toString();
                                writeMessage(taskName, taskDate);
                                prepareListData(taskDate, taskName);
                                listAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog1 = customBuilder.create();
                dialog1.show();

            }
        });

        prepareListData("  ", "  ");

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //DELETE ITEM in Expandable List View
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        final int groupPosition, final int childPosition, long id) {

                Cursor cursor = (Cursor) parent.getItemAtPosition(listDataChild.get(listDataHeader.get(groupPosition)).indexOf(childPosition));
                item_id = cursor.getInt(cursor.getColumnIndex(deadlinesDatabasehelper.KEY_TASK));
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(MainActivity_Deadlines.this);
                // Get the layout inflater
                LayoutInflater inflater = MainActivity_Deadlines.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                customBuilder.setView(inflater.inflate(R.layout.layout_removetask_deadlines, null))
                        // Add action buttons
                        .setPositiveButton(R.string.dialog_button_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                delete_byID(item_id);
                                listDataChild.get(listDataHeader.get(groupPosition)).remove(childPosition);
                                listAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog1 = customBuilder.create();
                dialog1.show();


                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        //Expanded List View
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Snackbar.make(expListView, "Expanded", Snackbar.LENGTH_LONG).show();
            }
        });

        //Listview Collapsed
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Snackbar.make(expListView, "Collapsed", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    public void delete_byID(int id){
        db.delete("Tasks", "id="+id, null);
    }


    @Override
    public void onResume(){
        super.onResume();
        bar = (ProgressBar) findViewById(R.id.progressBar2);
        bar.setVisibility(View.VISIBLE);
        new read_Database().execute();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter (listAdapter);

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void writeMessage(String t, String d){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(deadlinesDatabasehelper.KEY_TASK, t);
        values.put(deadlinesDatabasehelper.KEY_DATE, d);
        db.insert(deadlinesDatabasehelper.table_name, null, values);
        Log.i(ACTIVITY_NAME, "SQL MESSAGE INSERTED:" + values);

    }

    public List readDatabase(){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Tasks", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            String col_date = cursor.getString( cursor.getColumnIndex("date") );
            String task = cursor.getString(1);
            System.out.println(task);
            prepareListData(col_date, task);

            listAdapter.notifyDataSetChanged();
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex(deadlinesDatabasehelper.KEY_TASK) ) );
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex(deadlinesDatabasehelper.KEY_DATE) ) );
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count = " + cursor.getColumnCount() );
        return listDataHeader;
    }

    private void prepareListData(String month, String task) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("January");
        listDataHeader.add("February");
        listDataHeader.add("March");
        listDataHeader.add("April");
        listDataHeader.add("May");
        listDataHeader.add("June");
        listDataHeader.add("July");
        listDataHeader.add("August");
        listDataHeader.add("September");
        listDataHeader.add("October");
        listDataHeader.add("November");
        listDataHeader.add("December");

        String task_and_date = task + "   Due: " + month;

        // Adding child data
        if(month.substring(0,2).equals("01")){
            Jan.add(task + "   Due: " + month);
        }

        if(month.substring(0,2).contains("02")){
            Feb.add(task_and_date);
        }

        if(month.substring(0,2).contains("03")){
            Mar.add(task_and_date);
        }

        if(month.substring(0,2).contains("04")){
            Apr.add(task);
        }

        if(month.substring(0,2).contains("05")){
            May.add(task);
        }

        if(month.substring(0,2).contains("06")){
            Jun.add(task);
        }

        if(month.substring(0,2).contains("07")){
            Jul.add(task_and_date);
        }

        if(month.substring(0,2).contains("08")){
            Aug.add(task_and_date);
        }

        if(month.substring(0,2).contains("09")){
            Sep.add(task_and_date);
        }

        if(month.substring(0,2).contains("10")){
            Oct.add(task_and_date);
        }

        if(month.substring(0,2).contains("11")){
            Nov.add(task_and_date);
        }

        if(month.substring(0,2).contains("12")){
            Dec.add(task_and_date);
        }


        listDataChild.put(listDataHeader.get(0), Jan); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Feb);
        listDataChild.put(listDataHeader.get(2), Mar);
        listDataChild.put(listDataHeader.get(3), Apr);
        listDataChild.put(listDataHeader.get(4), May);
        listDataChild.put(listDataHeader.get(5), Jun);
        listDataChild.put(listDataHeader.get(6), Jul);
        listDataChild.put(listDataHeader.get(7), Aug);
        listDataChild.put(listDataHeader.get(8), Sep);
        listDataChild.put(listDataHeader.get(9), Oct);
        listDataChild.put(listDataHeader.get(10), Nov);
        listDataChild.put(listDataHeader.get(11), Dec);

    }


    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu_deadlines, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi){
        mi.getItemId();
        switch(mi.getItemId()) {

            case R.id.about:
                Toast toast = Toast.makeText(getApplicationContext(), about_message, Toast.LENGTH_LONG); //this is the ListActivity
                toast.show();
                return true;
        }
        return false;
    }



    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
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

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.child_list_deadlines, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
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
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.group_list_deadlines, null);
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


    private class read_Database extends AsyncTask<String, Integer, String> {
        Context mContext;
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }


        public List readDatabase(){
            publishProgress(25);
            db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Tasks", null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast() ){
                String col_date = cursor.getString( cursor.getColumnIndex("date") );
                String task = cursor.getString(1);
                publishProgress(50);
                System.out.println(task);
                prepareListData(col_date, task);

                listAdapter.notifyDataSetChanged();
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex(deadlinesDatabasehelper.KEY_TASK) ) );
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex(deadlinesDatabasehelper.KEY_DATE) ) );
                publishProgress(75);
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor’s  column count = " + cursor.getColumnCount() );
            publishProgress(100);
            return listDataHeader;
        }


        public String doInBackground(String...params) {

            readDatabase();

            return null;
        }

        public void onProgressUpdate(Integer...value){
            bar.setVisibility(View.VISIBLE);
            bar.setProgress( value[0] );
        }

        @Override
        public void onPostExecute(String result) {
            bar.setVisibility(View.INVISIBLE);
        }
    }


}
