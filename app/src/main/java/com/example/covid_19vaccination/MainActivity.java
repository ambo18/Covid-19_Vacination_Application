package com.example.covid_19vaccination;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;

    private MyDbHelper dbHelper;

    ActionBar actionBar;

    String orderByNewest = Constants.C_ADDED_TIMESTAMP + " DESC";
    String orderByOldest = Constants.C_ADDED_TIMESTAMP + " ASC";
    String orderByTitleAsc = Constants.C_NAME + " ASC";
    String orderByTitleDesc = Constants.C_NAME + " DESC";

    String currentOrderByStatus = orderByNewest;

    private static final int STORAGE_REQUEST_CODE_EXPORT = 1;
    private static final int STORAGE_REQUEST_CODE_IMPORT = 2;
    private String [] storagePermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar=getSupportActionBar();
        actionBar.setTitle("All Records");

        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addRecordBtn = findViewById(R.id.addRecordBtn);
        recordsRv = findViewById(R.id.recordsRv);

        dbHelper = new MyDbHelper(this);

        loadRecords(orderByNewest);

        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddUpdateRecordActivity.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);

            }
        });

    }

    @Override
     public void onBackPressed(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log-Out ? ").setCancelable(false).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
                finish();
            }
        }) .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermissionImport(){

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE_IMPORT);
    }

    private void requestStoragePermissionExport(){

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE_EXPORT);
    }

    private void importCSV() {
        String filePathAndName = Environment.getExternalStorageDirectory()+"/SQLiteBackup" + "SQLite_Backup.csv";

        File csvFile = new File(filePathAndName);

        if (csvFile.exists()){
            try {
                CSVReader csvReader = new CSVReader(new FileReader(csvFile.getAbsolutePath()));

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null){
                    String ids = nextLine[0];
                    String name = nextLine[1];
                    String image = nextLine[2];
                    String phone = nextLine[3];
                    String address = nextLine[4];
                    String dob = nextLine[5];
                    String age = nextLine[6];
                    String guardian = nextLine[7];
                    String category = nextLine[8];
                    String vaccineManufacturer = nextLine[9];
                    String vaccineManufacturer1 = nextLine[10];
                    String vaccineShot = nextLine[11];
                    String vaccineShot1 = nextLine[12];
                    String vaccineDate = nextLine[13];
                    String vaccineDate1 = nextLine[14];
                    String addedTime = nextLine[15];
                    String updatedTime = nextLine[16];

                    long timestamp = System.currentTimeMillis();
                    long id = dbHelper.insertRecord(
                            ""+name,
                            ""+image,
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
                            ""+updatedTime
                    );
                }
                Toast.makeText(this, "Backup Restored..", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "No backup found...", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportCSV() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/"+ "SQLiteBackup");

        boolean isFolderCreated = false;
        if (!folder.exists()){
            isFolderCreated = folder.mkdir();
        }
        Log.d("CAC_TAG", "exportCSV: "+isFolderCreated);
        String csvFileName = "SQLite_Backup.csv";
        String filePathAndName = folder.toString() + "/" + csvFileName;

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        recordsList.clear();
        recordsList = dbHelper.getAllRecords(orderByOldest);

        try {
            FileWriter fw = new FileWriter(filePathAndName);
            for (int i=0; i<recordsList.size(); i++){
                fw.append(""+recordsList.get(i).getId());
                fw.append(",");
                fw.append(""+recordsList.get(i).getName());
                fw.append(",");
                fw.append(""+recordsList.get(i).getImage());
                fw.append(",");
                fw.append(""+recordsList.get(i).getPhone());
                fw.append(",");
                fw.append(""+recordsList.get(i).getAddress());
                fw.append(",");
                fw.append(""+recordsList.get(i).getDob());
                fw.append(",");
                fw.append(""+recordsList.get(i).getAge());
                fw.append(",");
                fw.append(""+recordsList.get(i).getGuardian());
                fw.append(",");
                fw.append(""+recordsList.get(i).getCategory());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineManufacturer());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineManufacturer1());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineShot());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineShot1());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineDate());
                fw.append(",");
                fw.append(""+recordsList.get(i).getVaccineDate1());
                fw.append(",");
                fw.append(""+recordsList.get(i).getAddedTime());
                fw.append(",");
                fw.append(""+recordsList.get(i).getUpdatedTime());
                fw.append("\n");
            }
            fw.flush();
            fw.close();

            Toast.makeText(this, "Backup Exported to: "+filePathAndName, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecords(String orderBy) {
        currentOrderByStatus = orderBy;

        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this,
                dbHelper.getAllRecords(orderBy));

        recordsRv.setAdapter(adapterRecord);

        actionBar.setSubtitle("Total: "+dbHelper.getRecordsCount());
    }

    private void searchRecords (String query){
        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this,
                dbHelper.searchRecords(query));

        recordsRv.setAdapter(adapterRecord);
    }
    private void sortOptionDialog() {
        String[] options = {"Title Ascending", "Title Descending", "Newest", "Oldest"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which==0) {
                            loadRecords(orderByTitleAsc);
                        }
                        else if (which==1) {
                            loadRecords(orderByTitleDesc);
                        }
                        else if (which==2) {
                            loadRecords(orderByNewest);
                        }
                        else if (which==3) {
                            loadRecords(orderByOldest);
                        }
                    }
                })
                .create().show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadRecords(currentOrderByStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
         if (id==R.id.action_sort){
             sortOptionDialog();
         }
        /* else if (id==R.id.action_delete_all){
             dbHelper.deleteAllData();
             onResume();
         }*/
         else if (id==R.id.action_backup){
            if (checkStoragePermission()){
                exportCSV();
            }
            else {
                requestStoragePermissionExport();
            }
         }
         /*else if (id==R.id.action_restore){
             if (checkStoragePermission()){
                 onResume();
                 importCSV();
             }
             else {
                 requestStoragePermissionImport();
             }
         }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case STORAGE_REQUEST_CODE_EXPORT:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    exportCSV();
                }
                else {
                    Toast.makeText(this, "Storage Permission required...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE_IMPORT:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    importCSV();
                }
                else {
                    Toast.makeText(this, "Storage Permission required...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}