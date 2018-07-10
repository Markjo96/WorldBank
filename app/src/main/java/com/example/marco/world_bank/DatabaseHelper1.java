package com.example.marco.world_bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marco.world_bank.entities.ImageDao;

public class DatabaseHelper1 extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "imageDB";

    // Table Names
    private static final String DB_TABLE = "table_image";

    // column names
    private static final String KEY_NAME = "image_name";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE "+DB_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, image_name TEXT, image_data BLOB)";

    public DatabaseHelper1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
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

    public void addEntry( String name, byte[] image) throws SQLiteException {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,    name);
        cv.put(KEY_IMAGE,   image);
        db.insert( DB_TABLE, null, cv );
    }

    public Cursor getAllImg(){
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_IMAGE},null,
                null,null,null,null);
        return cursor;
    }

    public Cursor getImg(ImageDao imageDao) {
        String image_name = imageDao.getImage_name();
        Cursor cursor = db.query(DB_TABLE,new String[]{KEY_IMAGE},null,null,
                null,null,null);
        return cursor;
    }

    public boolean checkExist(String image_name){
        Cursor cursor = db.query(DB_TABLE,new String[]{KEY_NAME},KEY_NAME+"=?",
                new String[]{image_name},null,null,null);
        if (cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(image_name)){
                return true;
            }
        }
        return false;
    }
}
