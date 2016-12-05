package com.example.robertjackson.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.robertjackson.finalproject.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class ProfessorListActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ProfessorListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private View dialogView;
    ListView listView;
    EditText profName;
    EditText className;
    EditText classTime;
    EditText classDay;
    Button addProf;
    Button deleteProf;
    Button cancelButton;

    ArrayList<String> profInfo = new ArrayList<>();//message history string array

    SQLiteDatabase ProfsDB;
    ProfDatabaseHelperProfessorActivity helper;
    ContentValues contentValues = new ContentValues();
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list_activity);

        //ADD PROF VARIABLES
        profName = (EditText) findViewById(R.id.profNameEdit);//text field
        className = (EditText) findViewById(R.id.classNameEdit);//text field
        classTime = (EditText) findViewById(R.id.classTimeEdit);//text field
        classDay = (EditText) findViewById(R.id.classDaysEdit);//text field
        addProf = (Button) findViewById(R.id.addProf);//send button
        deleteProf = (Button) findViewById(R.id.deleteButton);//delete button
        cancelButton = (Button) findViewById(R.id.cancel);//cancel button

        //LISTVIEW VARIABLES
        listView = (ListView) findViewById(R.id.profListView);//list view
        final ChatAdapter adapter = new ChatAdapter(this);//ChatAdapter Object
        listView.setAdapter(adapter);
        //DATABASE VARIABLES
        helper = new ProfDatabaseHelperProfessorActivity(this);
        ProfsDB = helper.getWritableDatabase();
        //POPULATE LISTVIEW
        cursor = ProfsDB.query(ProfDatabaseHelperProfessorActivity.TABLE_NAME, ProfDatabaseHelperProfessorActivity.MESSAGE_FIELDS, null, null, null, null, null);//queries Chats for messages
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {//Name, Class, Time
            String newMessage = cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_NAME)) + "\n" + cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_CLASS)) + " - " + cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_TIME));
            profInfo.add(newMessage);
            cursor.moveToNext();
        }//while cursor check

        if (findViewById(R.id.professor_detail_container) != null) {
            mTwoPane = true;
        }
        //VARIABLE USED FOR BUTTON METHODS
        final String name = profName.getText().toString();
        final String pClass = className.getText().toString();
        final String pTime = classTime.getText().toString();
        final String pDays = classDay.getText().toString();
        //ADD PROF BUTTON
        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//onClick, send button adds text in EditText to ArrayList and database
                //add info to ArrayList
                profInfo.add(name);
                //add text fields to contentValues
                contentValues.put(ProfDatabaseHelperProfessorActivity.KEY_NAME, name);
                contentValues.put(ProfDatabaseHelperProfessorActivity.KEY_CLASS, pClass);
                contentValues.put(ProfDatabaseHelperProfessorActivity.KEY_TIME, pTime);
                contentValues.put(ProfDatabaseHelperProfessorActivity.KEY_DAYS, pDays);
                //Add contentValues to database
                helper.getWritableDatabase();
                ProfsDB.insert(ProfDatabaseHelperProfessorActivity.TABLE_NAME, null, contentValues);
                //add to listView
                adapter.notifyDataSetChanged();
                //reset fields
                profName.setText("");
                className.setText("");
                classTime.setText("");
                classDay.setText("");
            } //onClick
        });//setOnClickListener

        //DELETE PROF BUTTON
        deleteProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    cursor = ProfsDB.query(ProfDatabaseHelperProfessorActivity.TABLE_NAME, ProfDatabaseHelperProfessorActivity.MESSAGE_FIELDS, null, null, null, null, null);//queries Chats for messages
                    //Move cursor to first row, check if value of cursor matches value of profName field
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast() && cursor.toString() == name)
                    {
                        String newMessage = cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_NAME)) +"\n"+ cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_CLASS)) + "   " + cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_TIME));
                        //Update listview
                        profInfo.remove(newMessage);
                        adapter.notifyDataSetChanged();
                        //Update database

                        Log.i(ACTIVITY_NAME, "deleted" + cursor.getString(cursor.getColumnIndex(ProfDatabaseHelperProfessorActivity.KEY_NAME)));
                        cursor.moveToNext();
                    }//while
                } catch (Exception e){
                    Log.e(ACTIVITY_NAME, e.toString());
                }
            }//onClick
        });//setOnClick

        //CANCEL BUTTON
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //RESET FIELDS TO BLANK
            public void onClick(View v) {
                profName.setText("");
                className.setText("");
                classTime.setText("");
                classDay.setText("");
            }
        });
    }//onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu m){
        try {
            getMenuInflater().inflate(R.menu.menu_professor_activity, m);
            return true;
        } catch (Exception e) {
            Log.e("Exception ", e.toString());
        }
        return false;
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroy(){
        ProfsDB.close();
        super.onDestroy();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_professor_activity, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        arguments.putString(ProfessorDetailFragmentProfessorActivity.ARG_ITEM_ID, holder.mItem.id);

                        ProfessorDetailFragmentProfessorActivity fragment = new ProfessorDetailFragmentProfessorActivity();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.professor_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProfessorDetailActivityProfessorActivity.class);
                        intent.putExtra(ProfessorDetailFragmentProfessorActivity.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            DummyContent.DummyItem mItem;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }//SimpleItemRecycler

    //TOOLBAR BUTTONS
    public boolean onOptionsItemSelected(MenuItem mi) {
        try {
            int id = mi.getItemId();
            switch (id) {
                //BACK BUTTON
                case R.id.action_one:
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Are you sure you want to leave this activity?").
                            setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.create();
                    builder1.show();
                    break;
                //INFO BUTTON
                case R.id.about:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.dialog_box_professor_activity, null);
                    builder2.setView(dialogView);
                    EditText et = (EditText) dialogView.findViewById(R.id.dialogboxText);
                    et.setText("Enter the appropriate information into the text fields and apply changes using the buttons. \n\n" +
                            "The add button will enter the information into the database and make it appear on the screen.\n" +
                            "The user is able to delete a professor's information by entering that professor's name in the name field and" +
                            "pressing the delete button.\n" +
                            "The cancel button will erase all information in the text fields\n\n\n" +
                            "Student: Matt White \nStudent Number: 040813116");
                    AlertDialog dialog = builder2.create();
                    dialog.show();

                    break;
            }
            return true;
        } catch (Exception e) {
            Log.e(ACTIVITY_NAME, e.toString());
        }
        return false;
    }

    //LISTVIEW ADAPTER
    class ChatAdapter extends ArrayAdapter<String> {

        ChatAdapter(Context ctx) {
            super(ctx, 0);
        }//constructor

        public int getCount() {//return number of rows that will be in listView
            return profInfo.size();
        }

        public String getItem(int position) {//returns the item to show in the list at the specified position
            return profInfo.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {//returns the layout that will be positioned at the specified position in the list
            LayoutInflater inflater = ProfessorListActivity.this.getLayoutInflater();
            View result;

            result = inflater.inflate(R.layout.detail_professor_fragment_activity, null);

            TextView name = (TextView)result.findViewById(R.id.detail_profName);
            final String nameText = getItem(position);
            name.setText(nameText);//get the string at position

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ProfessorDetailFragmentProfessorActivity.ARG_ITEM_ID, nameText);
                        ProfessorDetailFragmentProfessorActivity fragment = new ProfessorDetailFragmentProfessorActivity();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.professor_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProfessorDetailActivityProfessorActivity.class);
                        intent.putExtra(ProfessorDetailFragmentProfessorActivity.ARG_ITEM_ID, nameText);

                        context.startActivity(intent);
                    }
                }
            });//result.setOnClick
            return result;
        }//getView
    }//ChatAdapter

}//MessageListActivity
