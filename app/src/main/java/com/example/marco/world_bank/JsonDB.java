package com.example.marco.world_bank;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marco.world_bank.entities.JsonDao;

public class JsonDB extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "JsonDB";
    private static final String TABLE_NAME = "jsonTable";


    public JsonDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT, json TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void open(){
        db = getWritableDatabase();
    }
    public void close(){
        db.close();
    }

    public void addJson(JsonDao jsonDao){
        ContentValues contentValues = jsonDao.toContentValues();
        db.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getJson(JsonDao jsonDao){
        String url = jsonDao.getUrl();
        String columns[] = {"json"};
    Cursor cursor = db.query(TABLE_NAME, columns,
            "url = '"+url+"'",null,null,null,null);
        return cursor;
}



    public void deleteTable(){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }


    public Cursor getAllJson() {
        Cursor cursor = db.query(TABLE_NAME, new String[]{"url", "json"},null,
                null,null,null,null);
        return cursor;
    }

    public void deleteAllJson() {
        db.delete(TABLE_NAME,null,null);
    }
}
