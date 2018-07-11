package com.example.marco.world_bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import com.example.marco.world_bank.entities.Cache;
import com.example.marco.world_bank.entities.Country;
import com.example.marco.world_bank.entities.ImageDao;
import com.example.marco.world_bank.entities.Indicator;
import com.example.marco.world_bank.entities.JsonDao;
import com.example.marco.world_bank.entities.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Using the SQLiteAssetHelper library allows to
 * use an existing SQLite Database, that is WorldBankDB
 * in this case.
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    //private static String DB_PATH = "/data/data/com.example.marco.world_bank/databases/";
    private SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "WorldBankDB.db";
    //Table name
    private static final String COUNTRY = "Country";
    private static final String TOPIC = "Topic";
    private static final String INDICATOR = "Indicator";
    private static final String IMAGE = "Image";
    private static final String JSON_TABLE = "JsonTable";

    private static final String ID_COUNTRY = "idCountry";
    private static final String ISO_COUNTRY = "isoCountry";
    private static final String NAME_COUNTRY = "nameCountry";

    private static final String ID_TOPIC = "idTopic";
    private static final String VALUE_TOPIC = "valueTopic";
    private static final String SOURCE_TOPIC= "sourceTopic";

    private static final String ID_INDICATOR = "idIndicator";
    private static final String NAME_INDICATOR = "nameIndicator";
    private static final String SOURCE_INDICATOR = "sourceIndicator";
    private static final String ID_TOPIC_INDICATOR = "idTopicIndicator";

    private static final String KEY_NAME = "image_name";
    private static final String KEY_IMAGE = "image_data";

    private static final String URL = "url";
    private static final String NAME_COUNTRY_JSON = "nameCountryJson";
    private static final String NAME_INDICATOR_JSON = "nameIndicatorJson";
    private static final String JSON = "json";


    private static final String CREATE_TABLE_COUNTRY = "CREATE TABLE "+COUNTRY+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ID_COUNTRY+" TEXT, "+ISO_COUNTRY+" TEXT, "+NAME_COUNTRY+" TEXT)";
    private static final String CREATE_TABLE_TOPIC = "CREATE TABLE "+TOPIC+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ID_TOPIC+" TEXT, "+VALUE_TOPIC+" TEXT, "+SOURCE_TOPIC+" TEXT)";
    private static final String CREATE_TABLE_INDICATOR = "CREATE TABLE "+INDICATOR+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ID_INDICATOR+" TEXT, "+NAME_INDICATOR+" TEXT, "+SOURCE_INDICATOR+" TEXT, "+ID_TOPIC_INDICATOR+" TEXT)";
    private static final String CREATE_TABLE_JSON_TABLE = "CREATE TABLE "+JSON_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+URL+" TEXT, "+NAME_COUNTRY_JSON+" TEXT, "+NAME_INDICATOR_JSON+" TEXT, "+JSON+" TEXT)";
    private static final String CREATE_TABLE_IMAGE ="CREATE TABLE "+IMAGE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_IMAGE+" BLOB)";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COUNTRY);
        db.execSQL(CREATE_TABLE_TOPIC);
        db.execSQL(CREATE_TABLE_INDICATOR);
        db.execSQL(CREATE_TABLE_JSON_TABLE);
        db.execSQL(CREATE_TABLE_IMAGE);
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS "+TOPIC);
        db.execSQL("DROP TABLE IF EXISTS "+INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS "+JSON_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+IMAGE);
        onCreate(db);
    }

    public void open(){
        db = getWritableDatabase();
    }

    public List<Country> getAllCounty() {
        Cursor cursor = db.query(COUNTRY,new String[]{ID_COUNTRY,ISO_COUNTRY,NAME_COUNTRY},
                null,null,null,null,NAME_COUNTRY);
        String id;
        String iso;
        String name;
        List<Country> countries = new ArrayList<>();
        while (cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex(ID_COUNTRY));
            iso = cursor.getString(cursor.getColumnIndex(ISO_COUNTRY));
            name = cursor.getString(cursor.getColumnIndex(NAME_COUNTRY));
            Country country = new Country(id,iso,name);
            countries.add(country);
        }
        return countries;
    }

    public Cursor getJson(JsonDao jsonDao) {
        String url = jsonDao.getUrl();
        Cursor cursor = db.query(JSON_TABLE, new String[]{JSON},
                "url = '"+url+"'",null,null,null,null);
        return cursor;
    }

    public String getNameIndicatorByIndicatorId(String indicatorId) {
        Cursor cursor = db.query(INDICATOR,new String[]{NAME_INDICATOR},ID_INDICATOR+"=?",
                new String[]{indicatorId},null,null,null);
        String nameIndicator = null;
        if(cursor.moveToNext()){
            nameIndicator = cursor.getString(cursor.getColumnIndex(NAME_INDICATOR));
        }
        return nameIndicator;
    }

    public void addJson(JsonDao jsonDao1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(URL,jsonDao1.getUrl());
        contentValues.put(NAME_COUNTRY_JSON,jsonDao1.getCountryName());
        contentValues.put(NAME_INDICATOR_JSON,jsonDao1.getIndicatorName());
        contentValues.put(JSON,jsonDao1.getJson());
        db.insert(JSON_TABLE,null,contentValues);
    }

    public List<Topic> getAllTopic() {
        Cursor cursor = db.query(TOPIC,new String[]{ID_TOPIC,VALUE_TOPIC,SOURCE_TOPIC},
                null,null,null,null,VALUE_TOPIC);
        String id;
        String value;
        String source;
        List<Topic> topics = new ArrayList<>();
        while (cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex(ID_TOPIC));
            value = cursor.getString(cursor.getColumnIndex(VALUE_TOPIC));
            source = cursor.getString(cursor.getColumnIndex(SOURCE_TOPIC));
            Topic topic = new Topic(id,value,source);
            topics.add(topic);
        }
        return topics;
    }

    public List<Indicator> getIndicatorByTopicId(String topicId) {
        Cursor cursor = db.query(INDICATOR,new String[]{ID_INDICATOR,NAME_INDICATOR,SOURCE_INDICATOR}
        ,ID_TOPIC_INDICATOR+"=?", new String[]{topicId},null,null,NAME_INDICATOR);

        List<Indicator> indicators = new ArrayList<>();
        String id;
        String name;
        String source;
        while (cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex(ID_INDICATOR));
            name = cursor.getString(cursor.getColumnIndex(NAME_INDICATOR));
            source = cursor.getString(cursor.getColumnIndex(SOURCE_INDICATOR));
            Indicator indicator = new Indicator(id,name,source);
            indicators.add(indicator);
        }
        return indicators;
    }

    public String getCountryNameByIso(String isoCode2) {
        Cursor cursor = db.query(COUNTRY,new String[]{NAME_COUNTRY},ISO_COUNTRY+"=?",
                new String[]{isoCode2},null,null,null);
        String countryName = null;
        if (cursor.moveToNext()){
            countryName = cursor.getString(cursor.getColumnIndex(NAME_COUNTRY));
        }
        return countryName;
    }

    public boolean checkExistImage(String image_name) {
        Cursor cursor = db.query(IMAGE,new String[]{KEY_NAME},KEY_NAME+"=?",
                new String[]{image_name},null,null,null);
        if (cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(image_name)){
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public void addImage(ImageDao imageDao) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, imageDao.getImage_name());
        cv.put(KEY_IMAGE, imageDao.getImage_data());
        db.insert( IMAGE, null, cv );
    }

    public List<Cache> getAllImg() {
        Cursor cursor = db.query(IMAGE,new String[]{KEY_NAME},null,null,
                null,null,null);
        String keyName;
        String[] keyNameSplit;
        List<Cache> caches = new ArrayList<>();
        while (cursor.moveToNext()){
            keyName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            keyNameSplit = keyName.split("\\^");
            Country country = new Country(null,null,keyNameSplit[0]);
            Indicator indicator = new Indicator(null,keyNameSplit[1],null);
            Cache cache = new Cache(country,indicator,null);
            caches.add(cache);
        }
        return caches;
    }

    public List<Cache> getAllJson() {
        Cursor cursor = db.query(JSON_TABLE,new String[]{URL,NAME_COUNTRY_JSON,NAME_INDICATOR_JSON},
                null,null, null,null,null);
        String url;
        String nameCountry;
        String nameIndicator;
        List<Cache> caches = new ArrayList<>();
        while (cursor.moveToNext()){
            url = cursor.getString(cursor.getColumnIndex(URL));
            nameCountry = cursor.getString(cursor.getColumnIndex(NAME_COUNTRY_JSON));
            nameIndicator = cursor.getString(cursor.getColumnIndex(NAME_INDICATOR_JSON));
            Country country = new Country(null,null,nameCountry);
            Indicator indicator = new Indicator(null,nameIndicator,null);
            Cache cache = new Cache(country,indicator,url);
            caches.add(cache);
        }
        return caches;
    }

    public byte[] getImg(ImageDao imageDao) {
        String image_name = imageDao.getImage_name();
        Cursor cursor = db.query(IMAGE,new String[]{KEY_IMAGE},null,null,
                null,null,null);
        byte[] bytes = new byte[0];
        if (cursor.moveToNext()){
            bytes = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
        }
        return bytes;
    }

    public void addCountry(Country country) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COUNTRY,country.getId());
        contentValues.put(ISO_COUNTRY,country.getIso2Code());
        contentValues.put(NAME_COUNTRY,country.getName());
        db.insert(COUNTRY,null,contentValues);
    }

    public void addTopic(Topic topic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_TOPIC,topic.getId());
        contentValues.put(VALUE_TOPIC,topic.getValue());
        contentValues.put(SOURCE_TOPIC,topic.getSourceNote());
        db.insert(TOPIC,null,contentValues);
    }

    public void addIndicator(Indicator indicator) {
        for (int i =0;i<indicator.getTopics().size();i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_INDICATOR,indicator.getId());
            contentValues.put(NAME_INDICATOR,indicator.getName());
            contentValues.put(SOURCE_INDICATOR,indicator.getSourceNote());
            contentValues.put(ID_TOPIC_INDICATOR,indicator.getTopics().get(i).getId());
            db.insert(INDICATOR,null,contentValues);
        }

    }

    public void deleteAllIndicator() {
        db.delete(INDICATOR,null,null);
    }

    public void deleteAllJson() {
        db.delete(JSON_TABLE,null,null);
    }

    public void deleteAllImage() {
        db.delete(IMAGE,null,null);
    }
}
