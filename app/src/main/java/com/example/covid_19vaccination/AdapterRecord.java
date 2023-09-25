package com.example.covid_19vaccination;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord> {

    private Context context;
    private ArrayList<ModelRecord> recordsList;

    MyDbHelper dbHelper;

    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList) {
        this.context = context;
        this.recordsList = recordsList;

        dbHelper = new MyDbHelper(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {

        ModelRecord model = recordsList.get(position);
        String id = model.getId();
        String name = model.getName();
        String image = model.getImage();
        String phone = model.getPhone();
        String address = model.getAddress();
        String dob = model.getDob();
        String age = model.getAge();
        String guardian = model.getGuardian();
        String category = model.getCategory();
        String vaccineManufacturer = model.getVaccineManufacturer();
        String vaccineManufacturer1 = model.getVaccineManufacturer1();
        String vaccineShot = model.getVaccineShot();
        String vaccineShot1 = model.getVaccineShot1();
        String vaccineDate = model.getVaccineDate();
        String vaccineDate1 = model.getVaccineDate1();
        String addedTime = model.getAddedTime();
        String updatedTime = model.getUpdatedTime();

        holder.nameTv.setText(name);
        holder.phoneTv.setText(phone);
        holder.addressTv.setText(address);
        holder.categoryTv.setText(category);
        if (image.equals("null")){
            holder.profileIv.setImageResource(R.drawable.ic_person_black);
        }
        else {
            holder.profileIv.setImageURI(Uri.parse(image));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecordDatailActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMoreDialog(
                        ""+position,
                        ""+id,
                        ""+name,
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
                        ""+image,
                        ""+addedTime,
                        ""+updatedTime
                );
            }
        });

        Log.d("ImagePath", "onBindViewHolder: "+image);
    }

    private void showMoreDialog(String position, final String id, final String name, final String phone, final String address,
                                final String dob, final String age, final String guardian, final String category,
                                final String vaccineManufacturer, final String vaccineManufacturer1, final String vaccineShot, final String vaccineShot1,
                                final String vaccineDate, final String vaccineDate1, final String image, final String addedTime, final String updatedTime) {

        String[] options = {"Edit"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(which==0){
                    Intent intent = new Intent(context, AddUpdateRecordActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME", name);
                    intent.putExtra("PHONE", phone);
                    intent.putExtra("IMAGE", image);
                    intent.putExtra("ADDRESS", address);
                    intent.putExtra("DOB", dob);
                    intent.putExtra("CATEGORY", category);
                    intent.putExtra("VACCINE_MANUFACTURER", vaccineManufacturer);
                    intent.putExtra("VACCINE_MANUFACTURER1", vaccineManufacturer1);
                    intent.putExtra("VACCINE_SHOT", vaccineShot);
                    intent.putExtra("VACCINE_SHOT1", vaccineShot1);
                    intent.putExtra("VACCINE_DATE", vaccineDate);
                    intent.putExtra("VACCINE_DATE1", vaccineDate1);
                    intent.putExtra("AGE", age);
                    intent.putExtra("GUARDIAN", guardian);
                    intent.putExtra("ADDED_TIME", addedTime);
                    intent.putExtra("UPDATED_TIME", updatedTime);
                    intent.putExtra("isEditMode", true);
                    context.startActivity(intent);
                }
            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder {

        ImageView profileIv;
        TextView nameTv, phoneTv, addressTv, categoryTv;
        ImageButton moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);

            profileIv = itemView.findViewById(R.id.profileIv);
            nameTv= itemView.findViewById(R.id.nameTv);
            phoneTv= itemView.findViewById(R.id.contactNoTv);
            addressTv= itemView.findViewById(R.id.addressTv);
            categoryTv= itemView.findViewById(R.id.categoryTv);
            moreBtn= itemView.findViewById(R.id.moreBtn);
        }
    }
}