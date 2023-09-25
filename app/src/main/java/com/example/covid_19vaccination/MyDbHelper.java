package com.example.covid_19vaccination;

import static com.example.covid_19vaccination.Constants.CREATE_TABLE;
import static com.example.covid_19vaccination.Constants.C_ADDED_TIMESTAMP;
import static com.example.covid_19vaccination.Constants.C_ADDRESS;
import static com.example.covid_19vaccination.Constants.C_AGE;
import static com.example.covid_19vaccination.Constants.C_CATEGORY;
import static com.example.covid_19vaccination.Constants.C_DOB;
import static com.example.covid_19vaccination.Constants.C_GUARDIAN;
import static com.example.covid_19vaccination.Constants.C_ID;
import static com.example.covid_19vaccination.Constants.C_IMAGE;
import static com.example.covid_19vaccination.Constants.C_NAME;
import static com.example.covid_19vaccination.Constants.C_PHONE;
import static com.example.covid_19vaccination.Constants.C_UPDATED_TIMESTAMP;
import static com.example.covid_19vaccination.Constants.C_VACCINE_MANUFACTURER;
import static com.example.covid_19vaccination.Constants.C_VACCINE_MANUFACTURER1;
import static com.example.covid_19vaccination.Constants.C_VACCINE_SHOT;
import static com.example.covid_19vaccination.Constants.C_VACCINE_SHOT1;
import static com.example.covid_19vaccination.Constants.C_VACCINE_DATE;
import static com.example.covid_19vaccination.Constants.C_VACCINE_DATE1;
import static com.example.covid_19vaccination.Constants.DB_NAME;
import static com.example.covid_19vaccination.Constants.DB_VERSION;
import static com.example.covid_19vaccination.Constants.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public long insertRecord(String name, String image, String phone, String address, String dob, String age, String guardian,
                             String category, String vaccineManufacturer, String vaccineManufacturer1, String vaccineShot,
                             String vaccineShot1, String vaccineDate, String vaccineDate1, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(C_NAME, name);
        values.put(C_IMAGE, image);
        values.put(C_PHONE, phone);
        values.put(C_ADDRESS, address);
        values.put(C_DOB, dob);
        values.put(C_AGE, age);
        values.put(C_GUARDIAN, guardian);
        values.put(C_CATEGORY, category);
        values.put(C_VACCINE_MANUFACTURER, vaccineManufacturer);
        values.put(C_VACCINE_MANUFACTURER1, vaccineManufacturer1);
        values.put(C_VACCINE_SHOT, vaccineShot);
        values.put(C_VACCINE_SHOT1, vaccineShot1);
        values.put(C_VACCINE_DATE, vaccineDate);
        values.put(C_VACCINE_DATE1, vaccineDate1);
        values.put(C_ADDED_TIMESTAMP, addedTime);
        values.put(C_UPDATED_TIMESTAMP, updatedTime);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public void updateRecord(String id, String name, String image, String phone, String address, String dob, String age, String guardian,
                             String category, String vaccineManufacturer, String vaccineManufacturer1, String vaccineShot,
                             String vaccineShot1, String vaccineDate, String vaccineDate1, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(C_NAME, name);
        values.put(C_IMAGE, image);
        values.put(C_PHONE, phone);
        values.put(C_ADDRESS, address);
        values.put(C_DOB, dob);
        values.put(C_AGE, age);
        values.put(C_GUARDIAN, guardian);
        values.put(C_CATEGORY, category);
        values.put(C_VACCINE_MANUFACTURER, vaccineManufacturer);
        values.put(C_VACCINE_MANUFACTURER1, vaccineManufacturer1);
        values.put(C_VACCINE_SHOT, vaccineShot);
        values.put(C_VACCINE_SHOT1, vaccineShot1);
        values.put(C_VACCINE_DATE, vaccineDate);
        values.put(C_VACCINE_DATE1, vaccineDate1);
        values.put(C_ADDED_TIMESTAMP, addedTime);
        values.put(C_UPDATED_TIMESTAMP, updatedTime);

        db.update(Constants.TABLE_NAME, values, Constants.C_ID +" = ?", new String[] {id});
        db.close();

    }

    public ArrayList<ModelRecord> getAllRecords(String orderBy){

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                 @SuppressLint("Range") ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(C_ADDRESS)),
                        ""+cursor.getString(cursor.getColumnIndex(C_DOB)),
                         ""+cursor.getString(cursor.getColumnIndex(C_AGE)),
                         ""+cursor.getString(cursor.getColumnIndex(C_GUARDIAN)),
                        ""+cursor.getString(cursor.getColumnIndex(C_CATEGORY)),
                        ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_MANUFACTURER)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_MANUFACTURER1)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_SHOT)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_SHOT1)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_DATE)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_DATE1)),
                        ""+cursor.getString(cursor.getColumnIndex(C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(C_UPDATED_TIMESTAMP))
                 );

                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }
        db.close();

        return recordsList;
    }


    public ArrayList<ModelRecord> searchRecords(String query){

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + C_NAME +  " LIKE '%" + query +"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                 @SuppressLint("Range") ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(C_ADDRESS)),
                        ""+cursor.getString(cursor.getColumnIndex(C_DOB)),
                         ""+cursor.getString(cursor.getColumnIndex(C_AGE)),
                         ""+cursor.getString(cursor.getColumnIndex(C_GUARDIAN)),
                        ""+cursor.getString(cursor.getColumnIndex(C_CATEGORY)),
                        ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_MANUFACTURER)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_MANUFACTURER1)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_SHOT)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_SHOT1)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_DATE)),
                         ""+cursor.getString(cursor.getColumnIndex(C_VACCINE_DATE1)),
                        ""+cursor.getString(cursor.getColumnIndex(C_ADDED_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(C_UPDATED_TIMESTAMP))
                );

                recordsList.add(modelRecord);
            }while (cursor.moveToNext());
        }
        db.close();

        return recordsList;
    }

    public void deleteData(){

    }

    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    public int getRecordsCount(){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}