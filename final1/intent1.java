package com.example.anshuman.final1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.GestureDetector.*;

public class intent1 extends AppCompatActivity implements OnDoubleTapListener {
    PendingIntent pending;
    AlarmManager alarmManager;
    Button alarm_off;
    GestureDetector gestureDetector;
    private GestureDetectorCompat gesture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent i = new Intent(intent1.this,List.class);
        Bundle bundle = getIntent().getExtras();
        String message="snjanjkaadnakdmakmd";
        message = bundle.getString("extra5");


        final Intent intent = new Intent(this,AlarmReciever.class);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        TextView update_text = (TextView)findViewById(R.id.message_text);
        update_text.setText(message);



        alarm_off = (Button)findViewById(R.id.button);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List l;
                l = new List();
                //cancel the pending intent
                alarmManager.cancel(pending);
                //put extra string into myintent
                //tells the clock that you presses the off button
                intent.putExtra("extra1","alarm off");
                //stop the ringtone
                sendBroadcast(intent);
                //l.deleteTask(v);
                startActivity(i);



            }
        });

        gesture = new GestureDetectorCompat(this, new OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }


        });

        gesture.setOnDoubleTapListener(new OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                final Intent i = new Intent(intent1.this,List.class);

                //cancel the pending intent
                alarmManager.cancel(pending);
                //put extra string into myintent
                //tells the clock that you presses the off button
                intent.putExtra("extra1","alarm off");
                //stop the ringtone
                sendBroadcast(intent);
                //l.deleteTask(v);
                startActivity(i);
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gesture.onTouchEvent(event);
        return true;
    }




    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
