package com.example.android.proje;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class SurveyActivity extends Activity {
    private ListView listView1;
    final List<Questions> questions = new ArrayList<Questions>();//Questin class for holding information about questions and questions
    Database mydatabase;
    SQLiteDatabase dbw, dbr;
    private   int tb_number;
    Context CTX = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveyscren);

        Intent intent = getIntent();
        String survey = (intent.getStringExtra("survey"));
        tb_number=(Integer.parseInt(survey));
        Log.v("mytag","++++"+tb_number);
        initalize();
        if (setquestions())//load question from database to Questions
            setList();

    }

    
    void setList() {//Create list using SurveyAdaper class
        SurveyAdapter adapter = new SurveyAdapter(this, R.layout.listitem, questions);
        listView1 = (ListView) findViewById(R.id.list);
        Button btnScan = new Button(this);
        btnScan.setBackground(Drawable.createFromPath("@drawable/rouned_corner_shadow"));
        btnScan.setText("Scan Inventory");
        // Adding Load More button to list view at bottom
        listView1.addFooterView(btnScan);//To the bottom of listview
        listView1.setAdapter(adapter);
        btnScan.setOnClickListener(handleClick);

    }
    private View.OnClickListener handleClick = new View.OnClickListener(){//button click
        public void onClick(View arg0) {
            Button btn = (Button)arg0;


            }

        }
    };


///Add questions from database to questions class
    boolean setquestions() {
        try {

            String[] colums = new String[]{Database.COLUMN_ID, Database.COLUMN__QUESTİONNUMBER, Database.COLUMN__QUESTİONTEXT, Database.COLUMN__QUESTİONTYPE};
            Cursor cursor = null;
            cursor = query(colums, null);
            cursor.moveToNext();
            for (int i = 1; i <= cursor.getCount(); i++) {
                int id = cursor.getInt(0);
                Log.v("log_tag", id + "");
                String question = cursor.getString(2);
                Log.v("log_tag", question);

                int type = cursor.getInt(3);
                Log.v("log_tag", "" + type);
                cursor.moveToNext();
                //you dont need  use database adding question,use below line add question for questinonlist
                questions.add(new Questions("question:" + (id), (id)," "+ question, type));
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    boolean update_Result()
    {

        for (int i = 0; i < questions.size(); i++){
            try {
                Log.v("mytag","R"+questions.get(i).getResult());
                Log.v("mytag","id"+questions.get(i).getNumber());
                update_Question(questions.get(i).getNumber(),questions.get(i).getResult());
                Log.v("mytag", "ekle");
            } catch (MyOwnException e) {
                Log.v("mytag", "update eror");
                e.printStackTrace();
                return  false;
            }
        }
        return true;
    }


    void initalize() {//İnitilazi Database
        mydatabase = new Database(this,1);
        dbw = mydatabase.getWritableDatabase();
        dbr = mydatabase.getReadableDatabase();

    }

    public boolean update_Question(long rowId, int result) throws MyOwnException {
        ContentValues args = new ContentValues();
        args.put(Database.COLUMN__RESULT, result);
        return dbw.update(mydatabase.returnTablename(tb_number), args, mydatabase.COLUMN_ID + "=" + rowId, null) > 0;
    }

    private Cursor query(String[] colums, String where) {
        switch (tb_number) {
            case 1 :
                return dbr.query(Database.TABLE_NAME, colums, null, null, null, null, null);
            case 2 :
                return dbr.query(Database.TABLE_NAME2, colums, null, null, null, null, null);
            case 3 :
                return dbr.query(Database.TABLE_NAME3, colums, null, null, null, null, null);
        }

        return null;
    }

    boolean controlSurveyinDatabase() {
        try {

            String[] colums = new String[]{Database.COLUMN_ID, Database.COLUMN__QUESTİONNUMBER, Database.COLUMN__QUESTİONTEXT};
            Cursor cursor = query(colums, null);
            if (cursor.getCount() == 0) {
                Log.v("log_tag", "false");
                return false;
            } else {
                Log.v("log_tag", "true");
                return true;

            }
        } catch (Exception e) {
            Log.v("log_tag", e.toString());
            return false;
        }

    }

    boolean controlcheckquestion() {//check all question is checked

        for (int i = 0; i < questions.size(); i++) {

            if (questions.get(i).getResult() != -1)
                Log.v("myoutput", questions.get(i).getResult() + "");
            else {
                Toast.makeText(SurveyActivity.this, "La gapcuk tum sorulari işaretle ", Toast.LENGTH_LONG).show();
                return false;
            }


        }
        return true;

    }

    class MyOwnException extends Exception {
        public MyOwnException(String msg){
            super(msg);
        }
    }



}