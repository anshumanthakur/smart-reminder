package com.example.anshuman.final1;

/**
 * Created by anshuman on 06-05-2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_PRODUCTS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "taskname";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE "  +  TABLE_PRODUCTS  + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT " +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS);
        onCreate(db);
    }


    public void addProduct(tasks task){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK,task.get_taskname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    public String databaseToString(){
        String dbString="";
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT *FROM " + TABLE_PRODUCTS + " WHERE 1, ";
        //cursor point to location in result
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            if(c.getString(c.getColumnIndex("taskname"))!=null){
                dbString=c.getString(c.getColumnIndex("taskname"));

            }
        }
        db.close();
        return dbString;
    }


}
