package com.example.covid_19vaccination;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecordDatailActivity extends AppCompatActivity {

    private CircularImageView profileIv;
    private TextView nameTv, phoneTv, addressTv, dobTv, ageTv, guardianTv, categoryTv, vaccineManufacturerTv, vaccineManufacturerTv1,
            vaccineShotTv, vaccineShotTv1, vaccineDateTv, vaccineDateTv1, addedTimeTv, updatedTimeTv;

    private ActionBar actionBar;

    private Context context;

    private MyDbHelper dbHelper;

    private ArrayList<ModelRecord> recordsList;

    private String recordID;

    private AdapterRecord adapterRecord;

    private String id, name, image, phone, address, dob, age, guardian, category, vaccineManufacturer, vaccineManufacturer1,
            vaccineShot, vaccineShot1, vaccineDate, vaccineDate1, addedTime, updatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_datail);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Record Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        dbHelper = new MyDbHelper(this);

        profileIv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        addressTv = findViewById(R.id.addressTv);
        dobTv = findViewById(R.id.dobTv);
        ageTv = findViewById(R.id.ageTv);
        guardianTv = findViewById(R.id.guardianTv);
        categoryTv = findViewById(R.id.categoryTv);
        vaccineManufacturerTv = findViewById(R.id.vaccineManufacturerTv);
        vaccineManufacturerTv1 = findViewById(R.id.vaccineManufacturerTv1);
        vaccineShotTv = findViewById(R.id.vaccineShotTV);
        vaccineShotTv1 = findViewById(R.id.vaccineShotTV1);
        vaccineDateTv = findViewById(R.id.vaccineDateTV);
        vaccineDateTv1 = findViewById(R.id.vaccineDateTV1);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);

        showRecordDatails();
    }

    private void showRecordDatails() {
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID +" =\"" + recordID+"\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                @SuppressLint("Range") String name = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                @SuppressLint("Range") String image = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                @SuppressLint("Range") String phone = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_PHONE));
                @SuppressLint("Range") String address = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS));
                @SuppressLint("Range") String dob = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_AGE));
                @SuppressLint("Range") String age = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_DOB));
                @SuppressLint("Range") String guardian = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_GUARDIAN));
                @SuppressLint("Range") String category = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_CATEGORY));
                @SuppressLint("Range") String vaccineManufacturer = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_MANUFACTURER));
                @SuppressLint("Range") String vaccineManufacturer1 = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_MANUFACTURER1));
                @SuppressLint("Range") String vaccineShot = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_SHOT));
                @SuppressLint("Range") String vaccineShot1 = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_SHOT1));
                @SuppressLint("Range") String vaccineDate = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_DATE));
                @SuppressLint("Range") String vaccineDate1 = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_VACCINE_DATE1));
                @SuppressLint("Range") String timestampAdded = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                @SuppressLint("Range") String timestampUpdated = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP));

                Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
                calendar1.setTimeInMillis(Long.parseLong(timestampAdded));
                String timeAdded = ""+DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1);

                Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
                calendar2.setTimeInMillis(Long.parseLong(timestampUpdated));
                String timeUpdated = ""+DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2);

                nameTv.setText(name);
                phoneTv.setText(phone);
                addressTv.setText(address);
                dobTv.setText(dob);
                ageTv.setText(age);
                guardianTv.setText(guardian);
                categoryTv.setText(category);
                vaccineManufacturerTv.setText(vaccineManufacturer);
                vaccineManufacturerTv1.setText(vaccineManufacturer1);
                vaccineShotTv.setText(vaccineShot);
                vaccineShotTv1.setText(vaccineShot1);
                vaccineDateTv.setText(vaccineDate);
                vaccineDateTv1.setText(vaccineDate1);
                addedTimeTv.setText(timeAdded);
                updatedTimeTv.setText(timeUpdated);
                if (image.equals("null")){
                    profileIv.setImageResource(R.drawable.ic_person_black);
                }
                else {
                    profileIv.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }
        db.close();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}