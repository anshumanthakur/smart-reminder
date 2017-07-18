package com.example.anshuman.final1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    DBHandler dbHandler;
    private ArrayAdapter mAdapter;
    private ListView myListView;
    intent1 alarmObject;
    PendingIntent pending;
    AlarmManager alarmManager;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final Intent i = new Intent(this,Main1.class);
        UpdateUI();
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(this,AlarmReciever.class);

        pending = PendingIntent.getBroadcast(getBaseContext(),id,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        //String[] items = {"one","two","three"};
        //ListAdapter myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        //ListView myListView = (ListView)findViewById(R.id.myListView);
        //myListView.setAdapter(myAdapter);

        String message="snjanjkaadnakdmakmd";
        id=0;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i.putExtra("extra6",0);
                        i.putExtra("extra5"," ");

                        startActivity(i);


                        UpdateUI();
                    }
                }
        );


    }
    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(DBHandler.TABLE_PRODUCTS, DBHandler.COLUMN_TASK + "=?", new String[]{task});
        db.close();
        UpdateUI();
        final Intent intent = new Intent(this,AlarmReciever.class);
        //pending = PendingIntent.getBroadcast(getBaseContext(),id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Bundle bundle = getIntent().getExtras();
        //id = bundle.getInt("extra8");
        //cancel the pending intent
        alarmManager.cancel(pending);
        //put extra string into myintent
        //tells the clock that you presses the off button
        intent.putExtra("extra1","alarm off");
        //stop the ringtone
        sendBroadcast(intent);
        //alarmObject.turn_off();
    }
    public void UpdateUI() {
        myListView = (ListView)findViewById(R.id.myListView);

        dbHandler = new DBHandler(this,null,null,1);

        final Intent i = new Intent(this,intent1.class);

        ArrayList<String> tasklist = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.query(DBHandler.TABLE_PRODUCTS, new String[]{DBHandler.COLUMN_ID, DBHandler.COLUMN_TASK},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DBHandler.COLUMN_TASK);
            tasklist.add(cursor.getString(idx));
        }
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.item_alarm,R.id.task_title);
        } else {
            mAdapter.clear();
            mAdapter.addAll(tasklist);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter = new ArrayAdapter<>(this, R.layout.item_alarm, R.id.task_title, tasklist);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String items = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(MainActivity.this, items, Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                });
        cursor.close();
        db.close();
    }
}
