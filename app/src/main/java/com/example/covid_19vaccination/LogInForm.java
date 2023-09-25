package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class LogInForm extends AppCompatActivity {
EditText username, password;
Button Login;
private ActionBar actionBar;

Timer timer;

LogInDBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_form);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Log-In");

        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);

        mydb = new LogInDBHelper(this);
        Login=findViewById(R.id.log_in);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animation = AnimationUtils.loadAnimation(LogInForm. this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                animation.setInterpolator(interpolator);
                Login.startAnimation(animation);
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(LogInForm.this, "Please enter Credentials.", Toast.LENGTH_SHORT).show();
                }else
                {
                   Boolean result =  mydb.checkusernamePassword(user,pass);
                   if(result==true){
                       timer = new Timer();
                       timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Intent intent = new Intent(LogInForm.this,MainActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       },2000);
                   }else{
                       Toast.makeText(LogInForm.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });
    }

}