
/**
 * Created by tianjianwei on 16/10/21.
 */

package com.tianjianwei.afriendoftime;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "afot_data";

    // Contacts table name
    private static final String TABLE_RECORDS = "afot_records";

    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TAG = "tag";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TIME_MINUTES = "time_minutes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTENTS_TABLE = "CREATE TABLE " + TABLE_RECORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG + " TEXT,"
                + KEY_CONTENT + " TEXT," + KEY_TIME_MINUTES + " INTEGER PRIMARY KEY" + ")";
        db.execSQL(CREATE_CONTENTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new record
    public void addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG, record.getTag());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME_MINUTES, record.getTimeMinutes());

        Log.d("get RECORD","TAG = "+record.getTag());
        Log.d("get RECORD","CONTENT = "+record.getContent());
        Log.d("get RECORD","TIME MINUTES = "+record.getTimeMinutes());
        Log.d("values","values = "+values.toString());

        // Inserting Row
        db.insert(TABLE_RECORDS, null, values);
        db.close(); // Closing database connection
    }

    // Getting a record
    public Record getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECORDS, new String[] { KEY_ID, KEY_TAG,
                        KEY_CONTENT, KEY_TIME_MINUTES }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Record user = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));

        db.close();
        // return contact
        return user;
    }

    // Getting All Records
    public List<Record> getAllUsers() {
        List<Record> contactList = new ArrayList<Record>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Record user = new Record();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setTag(cursor.getString(1));
                user.setContent(cursor.getString(2));
                user.setTimeMinutes(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return contactList;
    }

    // Getting All Records
    public Cursor getAllRecordsList() {
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_RECORDS, new String[] {KEY_ID, KEY_TAG,
                        KEY_CONTENT, KEY_TIME_MINUTES},
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        // return contact list
        return cursor;
    }

    // Getting All Records
    public ArrayList<String> getAllRecordsListStr() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(TABLE_RECORDS, new String[] {KEY_ID, KEY_TAG,
                        KEY_CONTENT, KEY_TIME_MINUTES},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(2).toString());
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return arrayList;
    }

    public ArrayList<HashMap<String, String>> getRecordObjList(){

        ArrayList<HashMap<String, String>> recordList = new ArrayList<HashMap<String, String>>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                map.put(DatabaseHelper.KEY_ID, cursor.getString(0));
                map.put(DatabaseHelper.KEY_TAG, cursor.getString(1));
                map.put(DatabaseHelper.KEY_CONTENT, cursor.getString(2));
                map.put(DatabaseHelper.KEY_TIME_MINUTES, cursor.getString(3));
				/*
				Log.d("USER","ID = "+cursor.getString(0));
				Log.d("USER","NAME = "+cursor.getString(1));
				Log.d("USER","PH NO = "+cursor.getString(2));
				Log.d("USER","ADDRESS = "+cursor.getString(3));
				Log.d("USER","COUNT = "+cursor.getCount());
				*/
                // adding HashList to ArrayList
                recordList.add(map);
            } while (cursor.moveToNext());
        }

        db.close();
        return recordList;

    }

    // Updating a record
    public int updateUser(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG, record.getTag());
        values.put(KEY_CONTENT, record.getContent());
        values.put(KEY_TIME_MINUTES, record.getTimeMinutes());

        // updating row
        return db.update(TABLE_RECORDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(record.getID()) });
    }

    // Deleting a record
    public void deleteRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORDS, KEY_ID + " = ?",
                new String[] { String.valueOf(record.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getRecordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
