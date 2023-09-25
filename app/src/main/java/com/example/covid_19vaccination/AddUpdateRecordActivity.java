package com.example.covid_19vaccination;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddUpdateRecordActivity extends AppCompatActivity {

    String [] items = {"BCG","HepB","QPB","IPB","PENTA","PCV","PPV","MMR","MR","Td","HPV","JE","Influenza Vaccine",
    "Pfizer-BioNTech","Moderna (mRNA-1273)","Oxford/AstraZeneca","J&J","Sinopharm","Sinovac"};

    String[] sexOptions = {"Male", "Female"};

    String[] vaccineDoses = {"1st Vaccine Shot", "2nd Vaccine Shot", "3rd Vaccine Shot"};

    AutoCompleteTextView vaccineManEt, categoryEt, vaccineShotEt, vaccineManEt1, vaccineShotEt1;
    ArrayAdapter<String> adapterItems, adapterItems1, adapterItems2, adapterItems3, adapterItems4;

    private CircularImageView profileIv;
    private EditText nameEt, phoneEt, addressEt, dobEt, guardianEt, vaccineDateEt, vaccineDateEt1;
    private TextView ageEt;
    private FloatingActionButton saveBtn;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;
    private String id, name, phone, address, dob, category, vaccineManufacturer, vaccineManufacturer1, vaccineShot, vaccineShot1, vaccineDate, vaccineDate1, age,
            guardian, addedTime, updatedTime;
    private boolean isEditMode = false;

    private MyDbHelper dbHelper;

    private ActionBar actionBar;

    private View background;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_record);

        background = findViewById(R.id.background);

        if (savedInstanceState == null) {
            background.setVisibility(View.VISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()){

                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                       circularRevealActivity();
                       background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }

        actionBar = getSupportActionBar();

        actionBar.setTitle("COVID-19 Vaccination");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        categoryEt = findViewById(R.id.categoryEt);
        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_item,sexOptions);
        categoryEt.setAdapter(adapterItems1);

        categoryEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        vaccineManEt = findViewById(R.id.vaccineManEt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        vaccineManEt.setAdapter(adapterItems);

        vaccineManEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        vaccineManEt1 = findViewById(R.id.vaccineManEt1);
        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_item,items);
        vaccineManEt1.setAdapter(adapterItems2);

        vaccineManEt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        vaccineShotEt = findViewById(R.id.vaccineShotEt);
        adapterItems3 = new ArrayAdapter<String>(this,R.layout.list_item,vaccineDoses);
        vaccineShotEt.setAdapter(adapterItems3);

        vaccineShotEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        vaccineShotEt1 = findViewById(R.id.vaccineShotEt1);
        adapterItems4 = new ArrayAdapter<String>(this,R.layout.list_item,vaccineDoses);
        vaccineShotEt1.setAdapter(adapterItems4);

        vaccineShotEt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        profileIv = (CircularImageView) findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        addressEt = findViewById(R.id.addressEt);
        dobEt = findViewById(R.id.dobEt);
        vaccineDateEt = findViewById(R.id.vaccineDateEt);
        vaccineDateEt1 = findViewById(R.id.vaccineDateEt1);
        ageEt = findViewById(R.id.ageEt);
        guardianEt = findViewById(R.id.guardianEt);
        saveBtn = (FloatingActionButton) findViewById(R.id.saveBtn);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode){

            actionBar.setTitle("Update Data");
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            phone = intent.getStringExtra("PHONE");
            address = intent.getStringExtra("ADDRESS");
            dob = intent.getStringExtra("DOB");
            age = intent.getStringExtra("AGE");
            guardian = intent.getStringExtra("GUARDIAN");
            category = intent.getStringExtra("CATEGORY");
            vaccineManufacturer = intent.getStringExtra("VACCINE_MANUFACTURER");
            vaccineManufacturer1 = intent.getStringExtra("VACCINE_MANUFACTURER1");
            vaccineShot = intent.getStringExtra("VACCINE_SHOT");
            vaccineShot1 = intent.getStringExtra("VACCINE_SHOT1");
            vaccineDate = intent.getStringExtra("VACCINE_DATE");
            vaccineDate1 = intent.getStringExtra("VACCINE_DATE1");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            addedTime = intent.getStringExtra("ADDED_TIME");
            updatedTime = intent.getStringExtra("UPDATED_TIME");

            nameEt.setText(name);
            phoneEt.setText(phone);
            addressEt.setText(address);
            dobEt.setText(dob);
            ageEt.setText(age);
            guardianEt.setText(guardian);
            categoryEt.setText("");
            vaccineManEt.setText("");
            vaccineManEt1.setText("");
            vaccineShotEt.setText("");
            vaccineShotEt1.setText("");
            vaccineDateEt.setText(vaccineDate);
            vaccineDateEt1.setText(vaccineDate1);

            if (imageUri.toString().equals("null")){
                profileIv.setImageResource(R.drawable.ic_person_black);
            }
            else{
                profileIv.setImageURI(imageUri);
            }
        }
        else {

            actionBar.setTitle("Add Record");
        }

        dbHelper = new MyDbHelper(this);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddUpdateRecordActivity.this, MainActivity.class);
                startActivity(intent);

                inputData();
            }
        });
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateBirthDate(int year, int month, int day) {
        // Update the EditText field with the selected date.
        String selectedDate = year + "-" + (month + 1) + "-" + day; // Month is 0-based.
        dobEt.setText(selectedDate);

        // Calculate age and display it.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date birthDate = sdf.parse(selectedDate);
            int age = calculateAge(birthDate);
            ageEt.setText(" " + age);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculateAge(Date birthDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

        int birthYear = birthCalendar.get(Calendar.YEAR);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        int birthDay = birthCalendar.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - birthYear;

        // Check if the birthdate hasn't occurred yet this year.
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        return age;
    }

    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new VaccineDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker2");
    }

    public void showDatePickerDialog3(View v) {
        DialogFragment newFragment = new VaccineDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker3");
    }

    private void updateVaccineDate(int year, int month, int day) {
        // Update the EditText field with the selected date for vaccineDateEt.
        String selectedDate = year + "-" + (month + 1) + "-" + day; // Month is 0-based.
        vaccineDateEt.setText(selectedDate);
    }

    private void updateVaccineDate1(int year, int month, int day) {
        // Update the EditText field with the selected date for vaccineDateEt1.
        String selectedDate = year + "-" + (month + 1) + "-" + day; // Month is 0-based.
        vaccineDateEt1.setText(selectedDate);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it.
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            ((AddUpdateRecordActivity) getActivity()).updateBirthDate(year, month, day);
        }
    }

    public static class VaccineDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it.
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Determine which EditText field called this method and update accordingly.
            String callerTag = getTag();
            if ("datePicker2".equals(callerTag)) {
                ((AddUpdateRecordActivity) getActivity()).updateVaccineDate(year, month, day);
            } else if ("datePicker3".equals(callerTag)) {
                ((AddUpdateRecordActivity) getActivity()).updateVaccineDate1(year, month, day);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void circularRevealActivity() {

        int cx = background.getRight() - getDips(44);
        int cy = background.getBottom() - getDips(44);

        float finalRaduis = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRaduis);

        circularReveal.setDuration(3000);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();
    }
    private int getDips(int dps) {

        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    private void inputData() {

        if (nameEt == null || phoneEt == null || addressEt == null || dobEt == null ||
                ageEt == null || guardianEt == null || categoryEt == null || vaccineManEt == null ||
                vaccineManEt1 == null || vaccineShotEt == null || vaccineShotEt1 == null ||
                vaccineDateEt == null || vaccineDateEt1 == null) {
            // Handle the case where one or more EditText fields are not initialized
            Toast.makeText(this, "One or more EditText fields are null.", Toast.LENGTH_SHORT).show();
            return;
        }

        name = ""+nameEt.getText().toString().trim();
        phone = ""+phoneEt.getText().toString().trim();
        address = ""+addressEt.getText().toString().trim();
        dob = ""+dobEt.getText().toString().trim();
        age = ""+ageEt.getText().toString().trim();
        guardian = ""+guardianEt.getText().toString().trim();
        category = ""+categoryEt.getText().toString().trim();
        vaccineManufacturer = ""+vaccineManEt.getText().toString().trim();
        vaccineManufacturer1 = ""+vaccineManEt1.getText().toString().trim();
        vaccineShot = ""+vaccineShotEt.getText().toString().trim();
        vaccineShot1 = ""+vaccineShotEt1.getText().toString().trim();
        vaccineDate = ""+vaccineDateEt.getText().toString().trim();
        vaccineDate1 = ""+vaccineDateEt1.getText().toString().trim();

        if (isEditMode){

            String timestamp = ""+System.currentTimeMillis();
            dbHelper.updateRecord(
                    ""+id,
                    ""+name,
                    ""+imageUri,
                    ""+phone,
                    ""+address,
                    ""+dob,
                    ""+age,
                    ""+guardian,
                    ""+category,
                    ""+vaccineManufacturer,
                    ""+vaccineManufacturer1,
                    ""+vaccineShot,
                    ""+vaccineShot1,
                    ""+vaccineDate,
                    ""+vaccineDate1,
                    ""+addedTime,
                    ""+timestamp
            );
            Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show();
        }
        else {

            String timestamp = ""+System.currentTimeMillis();
            long id = dbHelper.insertRecord(
                    ""+name,
                    ""+imageUri,
                    ""+phone,
                    ""+address,
                    ""+dob,
                    ""+age,
                    ""+guardian,
                    ""+category,
                    ""+vaccineManufacturer,
                    ""+vaccineManufacturer1,
                    ""+vaccineShot,
                    ""+vaccineShot1,
                    ""+vaccineDate,
                    ""+vaccineDate1,
                    ""+timestamp,
                    ""+timestamp
            );
            Toast.makeText(this,"Record Added against ID: "+id, Toast.LENGTH_SHORT).show();

        }


    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which==0){
                    if (!checkCameraPermissions()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private void copyFileOrDirectory(String srcDir, String descDir){
        try{
            File src = new File(srcDir);
            File des = new File(descDir, src.getName());
            if (src.isDirectory()){
                String[] files = src.list();
                int filesLength = files.length;
                for (String file : files){
                    String src1 = new File(src, file).getPath();
                    String dst1 = des.getPath();

                    copyFileOrDirectory(src1, dst1);
                }
            }
            else {
                copyFile(src, des);
            }
        }
        catch (Exception e){
        Toast.makeText( this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFile(File srcDir, File desDir) throws IOException {
        if (!desDir.getParentFile().exists()){
            desDir.mkdirs();
        }
        if (!desDir.exists()){
            desDir.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(srcDir).getChannel();
            destination = new FileInputStream(desDir).getChannel();
            destination.transferFrom(source, 0, source.size());

            imageUri = Uri.parse(desDir.getPath());
            Log.d("ImagePath", "copyFile: "+imageUri);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            if (source!=null){
                source.close();
            }
            if (destination!=null){
                destination.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRaduis = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRaduis, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(3000);
            circularReveal.start();
        }
        else {

        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this,"Camera & Storage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Storage Permission is required...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){

            if (requestCode == IMAGE_PICK_GALLERY_CODE){

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(this);
                }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    profileIv.setImageURI(resultUri);

                    copyFileOrDirectory(""+imageUri.getPath(),""+getDir("SQLiteRecordImages",MODE_PRIVATE));
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}