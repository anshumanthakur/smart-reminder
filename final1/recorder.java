package com.example.anshuman.final1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.IOException;
import java.util.Random;
import java.util.Calendar;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.widget.ImageView;
import android.widget.Toast;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class recorder extends AppCompatActivity {
    Main1 main;
    MediaPlayer media_song;
    Context context;
    AlarmManager alarmManager;
    Button buttonStart, buttonStop,buttonskip,setAlarm ;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    MediaPlayer mediaPlayer ;
    public static final int RequestPermissionCode = 1;
    PendingIntent pending;

    MediaRecorder recorder = new MediaRecorder();
    String location;
    ImageView image;
    ImageView playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recorder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent i = new Intent(this,Main1.class);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Bundle bundle = getIntent().getExtras();
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        buttonskip = (Button)findViewById(R.id.button_skip);
        setAlarm = (Button)findViewById(R.id.alarm_set);
        random = new Random();
        //final int hour = bundle.getInt("extra1");
        //final int minute = bundle.getInt("extra2");


        //start recording
        image= (ImageView)findViewById(R.id.imageView);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(checkPermission()) {

                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                        MediaRecorderReady();
                        location= String.valueOf(AudioSavePathInDevice);

                        //Toast.makeText(recorder.this, location, Toast.LENGTH_SHORT).show();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        Toast.makeText(recorder.this, "Recording started",
                                Toast.LENGTH_LONG).show();
                    } else {
                        requestPermission();
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    try {
                        mediaRecorder.stop();
                    }
                    catch(RuntimeException stopException){
                        //handle cleanup here
                    }

                    Toast.makeText(recorder.this, "Recording Completed",
                            Toast.LENGTH_LONG).show();

                }
                v.onTouchEvent(event);
                return true;

            }
        });

        playButton= (ImageView)findViewById(R.id.imageView2);
        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(recorder.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent setR = new Intent(recorder.this,setRingtone.class);
                //setR.putExtra("extra5",hour);
                //setR.putExtra("extra6",minute);
                startActivity(setR);

            }
        });
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i.putExtra("extra",location);
                i.putExtra("extra6",1);
                startActivity(i);

            }
        });


    }
    public PendingIntent returnPending(){
        return pending;
    }


    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(recorder.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }
}
