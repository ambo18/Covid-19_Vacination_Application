package com.example.covid_19vaccination;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LogInActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signUp,logIn;
    LogInDBHelper mydb;

    Timer timer;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.rep_password);

        signUp=findViewById(R.id.sign_up);
        logIn=findViewById(R.id.log_in);

        mydb=new LogInDBHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle("GET-Vaccinated");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animation = AnimationUtils.loadAnimation(LogInActivity. this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                signUp.startAnimation(animation);

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(LogInActivity.this,"Fill all the fields.", Toast.LENGTH_SHORT).show();
                }else{
                    if (pass.equals(repass)){
                        Boolean usercheckResult = mydb.checkusername(user);
                        if (usercheckResult == false){
                            Boolean regResult = mydb.insertDataLogIn(user,pass);
                            if (regResult == true){

                                Toast.makeText(LogInActivity.this,"Registration Successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this,LogInForm.class);
                                startActivity(intent);


                            }else {
                                Toast.makeText(LogInActivity.this,"Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LogInActivity.this,"User already Exists. \n Please Sign In", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LogInActivity.this,"Password Not Matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animation = AnimationUtils.loadAnimation(LogInActivity. this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                logIn.startAnimation(animation);

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                Intent intent = new Intent(getApplicationContext(),LogInForm.class);
                startActivity(intent);

                    }
                },1000);
            }
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit ? ").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                finish();

            }
        }) .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}