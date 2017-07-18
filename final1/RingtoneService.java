package com.example.anshuman.final1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by anshuman on 07-03-2017.
 */

public class RingtoneService extends Service {
    Main1 main;
    MediaPlayer media_song;
    int startID;
    boolean isrunning;
    recorder r;
    String location;


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    public int onStartCommand(Intent intent,int flags,int startID){
        Log.i("LocalService","Recieved start id "+ startID+ ":" + intent);
        final Intent i = new Intent(RingtoneService.this,intent1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //fetch the extra string values
        String state = intent.getExtras().getString("extra1");
        location = intent.getExtras().getString("extra2");
        int choice = intent.getExtras().getInt("extra4");
        String message = intent.getExtras().getString("extra5");
        Vibrator v = (Vibrator)RingtoneService.this.getSystemService(RingtoneService.this.VIBRATOR_SERVICE);





        Log.e("Ringtone extra is ",state );

        assert  state!=null;
        switch (state) {
            case "alarm on":
                startID = 1;
                break;
            case "alarm off":
                 startID = 0;
                Log.e("state id is",state );
                break;
            default:
                startID = 0;
                break;
        }
        //int choice= main.ReturnChoice();
        //if else statements
        //if there is no music playing and the user pressed the alarm on
        //turn on the alarm,music starts playing
        if(!this.isrunning && startID==1){
            Log.e("there is no music","and you want start="+message);
            //create instance of media player
            if(location== null) {
                if (choice == 0) {
                    media_song = MediaPlayer.create(this, R.raw.wake_up);
                    media_song.start();
                    //Toast.makeText(this, "playing ringtone" + location, Toast.LENGTH_SHORT).show();
                    media_song.setVolume(1,1);

                } else if (choice == 1) {
                    media_song = MediaPlayer.create(this, R.raw.company);
                    media_song.start();
                    //Toast.makeText(this, "playing ringtone" + location, Toast.LENGTH_SHORT).show();

                } else if (choice == 2) {
                    media_song = MediaPlayer.create(this, R.raw.samsung);
                    media_song.start();
                    //Toast.makeText(this, "playing ringtone" + location, Toast.LENGTH_SHORT).show();

                }



            }else {
                media_song = new MediaPlayer();

                try {
                    media_song.setDataSource(location);
                    media_song.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                media_song.start();
                //Toast.makeText(this, "playing recording and path="+location, Toast.LENGTH_SHORT).show();

            }
            this.isrunning=true;
            this.startID=0;
            v.vibrate(10000);
            i.putExtra("extra5",message);
            startActivity(i);


        }
        //alarm is playing and user pressed off
        //turn of the alarm
        else if (this.isrunning && startID==0){
            Log.e("there is music","and you want stop");
            //stop the ringtone
            media_song.stop();
            media_song.reset();
            v.cancel();
            this.isrunning=false;
            this.startID=0;


        }
        //music not playing and pressed stop button
        //do nothing
        //make the app bug proof
        else if (!this.isrunning && startID==0){
            Log.e("there is no music","and you want stop");
            this.isrunning=false;
            this.startID=0;
        }
        //if music playing =true and presses on button
        //do nothing
        else if (this.isrunning && startID==1){
            Log.e("there is music","and you want start");
            this.isrunning=true;
            this.startID=1;

        }

        return START_NOT_STICKY;

    }
    public void onDestroy(){
        //tells the user we have stopped
        Toast.makeText(this, "on Destroy called ", Toast.LENGTH_SHORT).show();
    }
}
