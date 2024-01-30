package com.marksapplication.app;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class addemployee14 extends AppCompatActivity {


    EditText etGroupSixtyOne,etGroupSixtyOne2, etGroupSixtyOne3, etGroupSixtyOne4;
    ImageView imageFingerprint;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_android_small_fourteen);

        FirebaseApp.initializeApp(this);

         etGroupSixtyOne = findViewById(R.id.etGroupSixtyOne);
         etGroupSixtyOne2 = findViewById(R.id.etGroupSixtyOne2);
         etGroupSixtyOne3 = findViewById(R.id.etGroupSixtyOne3);
         etGroupSixtyOne4 = findViewById(R.id.etGroupSixtyOne4);
         imageFingerprint = findViewById(R.id.imageFingerprint);
         btnSave = findViewById(R.id.btnSave);

         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 storeProductDetails();
                 Intent Next = new Intent(addemployee14.this,employersettings11.class);
                 startActivity(Next) ;
             }
         });
    }

    public void storeProductDetails() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        String name = etGroupSixtyOne.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
                return;
        }
        String phonenumber = etGroupSixtyOne2.getText().toString();
        if (phonenumber.isEmpty()) {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = etGroupSixtyOne3.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = etGroupSixtyOne4.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }



        DocumentReference documentReference = db.collection("Employee").document();
        Map<String, Object> Employee = new HashMap<>();
        Employee.put("name", name);
        Employee.put("email", email);
        Employee.put("phonenumber", phonenumber);
        Employee.put("password", password);

         documentReference.set(Employee).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 Toast.makeText(addemployee14.this, "Employee Profile is created for " + name, Toast.LENGTH_SHORT).show();
                 Log.d(TAG, "onSuccess: Employee Profile is created for " + name);
             }
         });


    }
}
