package com.marksapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignIN1 extends AppCompatActivity {

     EditText email;
     EditText password;
     TextView txtEmployeelogin;

     ImageView imageFingerprint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_small_one);

        email = findViewById(R.id.etGroupSixtyOne);
        password = findViewById(R.id.etGroupSixty);
        txtEmployeelogin = findViewById(R.id.txtEmployeelogin);
        imageFingerprint = findViewById(R.id.imageFingerprint);

        imageFingerprint.setOnClickListener(v -> {
            Intent intent = new Intent(SignIN1.this, MainnavActivity.class);
            startActivity(intent);
        });
    }
}
