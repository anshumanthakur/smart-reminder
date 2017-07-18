package com.example.anshuman.final1;

import android.app.AlarmManager;
import android.annotation.TargetApi;

import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;

public class Main1 extends AppCompatActivity {
    AlarmManager alarmManager;
    TimePicker timePicker;
    recorder r;
    PendingIntent pending;
    Random random;
    Spinner spinner;
    int temp=1;
    DatePicker pickerDate;
    String time;
    DBHandler dbHandler;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        final Intent i = new Intent(this,recorder.class);
        //initialize alarm manager
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        r = new recorder();
        random = new Random();
        dbHandler = new DBHandler(this,null,null,1);


        //initialize date picker
        pickerDate = (DatePicker)findViewById(R.id.datePicker);
        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null
        );


        //initialize timepicker
        timePicker = (TimePicker)findViewById(R.id.timePicker);

        //initialize the text view
        //update_text = (TextView)findViewById(R.id.update_text);
        final Intent intent = new Intent(this,AlarmReciever.class);
        final Intent intent_list = new Intent(this,List.class);



        //create the instance of calender
        final Calendar calendar= Calendar.getInstance();
        //alarm on button
        Button alarm_on = (Button)findViewById(R.id.alarm_on);
        Button next = (Button)findViewById(R.id.button_next);
        next.setOnClickListener(
                new Button.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        //int hour = timePicker.getHour();
                        //int minute = timePicker.getMinute();
                        //i.putExtra("extra1",hour);
                        //i.putExtra("extra2",minute);
                        startActivity(i);
                        temp=1;

                        //Toast.makeText(MainActivity.this,String.valueOf(temp), Toast.LENGTH_SHORT).show();
                    }
                }
        );


        alarm_on.setOnClickListener(
                new Button.OnClickListener(){

                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(pickerDate.getYear(),
                                pickerDate.getMonth(),
                                pickerDate.getDayOfMonth(),
                                timePicker.getHour(),
                                timePicker.getMinute(),
                                00
                        );

                        Bundle bundle = getIntent().getExtras();
                        temp=0;
                        temp = bundle.getInt("extra6");
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        int day = pickerDate.getDayOfMonth();
                        int month = pickerDate.getMonth();
                        int year = pickerDate.getYear();
                        String date = day+"/"+month+"/"+year;

                        int id =hour*10 + minute;

                        //convert into string
                        String hour_string= String.valueOf(hour);
                        String min_string = String.valueOf(minute);
                        if(hour>12){
                            time = hour_string+ ":"+min_string+ " PM";

                        }
                        else{
                            time = hour_string+":"+min_string+" AM";

                        }



                        //TextView update_text = (TextView)findViewById(R.id.update_text);
                        //update_text.setText("alarm set to"+ hour_string+" :"+ min_string);
                        Toast.makeText(Main1.this, "alarm set to"+ timePicker.getHour()+" :"+ timePicker.getMinute()+" "+date, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Main1.this,String.valueOf(temp), Toast.LENGTH_SHORT).show();
                        //put in extra string into intent tells the clock that you pressed the alarm on
                        if(temp==1) {

                            String location = bundle.getString("extra");
                            int choice = -1;
                            String message;
                            //Toast.makeText(MainActivity.this,location, Toast.LENGTH_SHORT).show();
                            message = bundle.getString("extra5");
                            message+= " ";

                            time= time+" on "+date +"\n"+message;
                            intent.putExtra("extra5",message);

                            choice = bundle.getInt("extra3");

                            intent.putExtra("extra1", "alarm on");
                            intent.putExtra("extra2", location);
                            intent.putExtra("extra4", choice);
                        }
                        else if(temp==0){

                            String location=null;
                            int choice = 1;
                            String message ;
                            message = bundle.getString("extra5");
                            message+= " ";

                            time= time+" on "+date +"\n"+message;
                            intent.putExtra("extra5",message+" ");
                            intent.putExtra("extra1", "alarm on");
                            intent.putExtra("extra2", location);
                            intent.putExtra("extra4", choice);
                        }
                        SQLiteDatabase db;
                        db = dbHandler.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DBHandler.COLUMN_TASK,time);
                        db.insertWithOnConflict(DBHandler.TABLE_PRODUCTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        pending = PendingIntent.getBroadcast(getBaseContext(),id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pending);
                        intent_list.putExtra("extra8",id);
                        startActivity(intent_list);

                    }
                }
        );


        // initialize the stop button
        Button alarm_off = (Button)findViewById(R.id.alarm_off);

        alarm_off.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //cancel the pending intent
                        alarmManager.cancel(pending);
                        //put extra string into myintent
                        //tells the clock that you presses the off button
                        intent.putExtra("extra1","alarm off");
                        //stop the ringtone
                        sendBroadcast(intent);
                    }
                }
        );

    }



}

