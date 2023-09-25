package com.example.covid_19vaccination;

public class Constants {

    public static final String DB_NAME = "MY_RECORD_DB";

    public static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "MY_RECORDS_TABLE";

    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_PHONE = "PHONE";
    public static final String C_ADDRESS= "ADDRESS";
    public static final String C_DOB = "DOB";
    public static final String C_AGE = "DATE";
    public static final String C_GUARDIAN = "HEALTH_FACILITY_NAME";
    public static final String C_CATEGORY = "CATEGORY";
    public static final String C_VACCINE_MANUFACTURER = "VACCINE_MANUFACTURER";
    public static final String C_VACCINE_MANUFACTURER1 = "VACCINE_MANUFACTURER1";
    public static final String C_VACCINE_SHOT = "VACCINE_SHOT";
    public static final String C_VACCINE_SHOT1 = "VACCINE_SHOT1";
    public static final String C_VACCINE_DATE = "VACCINE_DATE";
    public static final String C_VACCINE_DATE1 = "VACCINE_DATE1";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP = "UPDATED_TIME_STAMP";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_PHONE + " TEXT,"
            + C_ADDRESS + " TEXT,"
            + C_DOB + " TEXT,"
            + C_AGE + " TEXT,"
            + C_GUARDIAN + " TEXT,"
            + C_CATEGORY + " TEXT,"
            + C_VACCINE_MANUFACTURER + " TEXT,"
            + C_VACCINE_MANUFACTURER1 + " TEXT,"
            + C_VACCINE_SHOT + " TEXT,"
            + C_VACCINE_SHOT1 + " TEXT,"
            + C_VACCINE_DATE + " TEXT,"
            + C_VACCINE_DATE1 + " TEXT,"
            + C_ADDED_TIMESTAMP + " TEXT,"
            + C_UPDATED_TIMESTAMP + " TEXT"
            + ")";
}
