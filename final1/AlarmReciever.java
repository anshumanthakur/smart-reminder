package com.example.anshuman.final1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by anshuman on 07-03-2017.
 */

public class AlarmReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e( "we are in the reciever ","yay");

        //fetch extra string from intent
        String your_string = intent.getExtras().getString("extra1");
        String chString = intent.getExtras().getString("extra2");
        int choice = intent.getExtras().getInt("extra4");
        String message = intent.getExtras().getString("extra5");
        Log.e("what is the key ",your_string+"  //"+ chString );
        Intent service_intent = new Intent(context,RingtoneService.class);

        //pass the extra string to main activity thriugh this class
        service_intent.putExtra("extra1",your_string);
        service_intent.putExtra("extra2",chString);
        service_intent.putExtra("extra4",choice);
        service_intent.putExtra("extra5",message);

        //start the ringtone service
        context.startService(service_intent);

    }
}
