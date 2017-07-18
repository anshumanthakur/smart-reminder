package com.example.anshuman.final1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class setRingtone extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int choice;
    EditText myinput;
    DBHandler dbHandler;
    String message = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ringtone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(setRingtone.this);
        dbHandler = new DBHandler(this,null,null,1);



// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //about the reminder
        myinput = (EditText)findViewById(R.id.remin_text);

        TextView set = (TextView)findViewById(R.id.set_text);
        set.setText("choose your ringtone");
        final Intent i = new Intent(setRingtone.this,Main1.class);
        Button done = (Button)findViewById(R.id.button_set);
        done.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i.putExtra("extra3",choice);
                        message =  myinput.getText().toString();
                        message+= ".";
                        i.putExtra("extra5",message);
                        startActivity(i);

                    }
                }
        );


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        choice = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

