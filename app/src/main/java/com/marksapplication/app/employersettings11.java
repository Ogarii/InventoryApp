package com.marksapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class employersettings11 extends AppCompatActivity {

    Button btnAddEmployees, btnSignOut, btnEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_small_eleven);

        btnAddEmployees = findViewById(R.id.btnAddEmployees);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnEmployees = findViewById(R.id.btnEmployees);


        btnAddEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employersettings11.this, addemployee14.class);
                startActivity(intent);


            }
        });
        btnEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent employees = new Intent(employersettings11.this, employeelist12.class);
                startActivity(employees);
            }
        });



    }
}