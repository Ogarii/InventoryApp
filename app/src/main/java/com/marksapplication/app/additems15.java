package com.marksapplication.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class additems15 extends AppCompatActivity {


    LinearLayout linearColumnone, openScanner;
    EditText editTextTextPersonName,editTextTextPersonName2,editTextTextPersonName3,editTextTextPersonName4,editTextTextPersonName5;
    Button btnSave;
    ImageView imageView6;
    private FirebaseFirestore db;

    String ProductCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_small_fifteen);

        btnSave = findViewById(R.id.btnSave);

        linearColumnone = findViewById(R.id.linearColumnone);

        imageView6 = findViewById(R.id.imageView6);

        openScanner = findViewById(R.id.openScanner);

        // Check if the activity was launched from SenderActivity
        boolean fromSenderActivity = getIntent().getBooleanExtra("fromSenderActivity", false);
        if (fromSenderActivity) {
            // Perform the specific action
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            // Convert the byte array back to a bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // Display the bitmap in an ImageView
            imageView6.setImageBitmap(bitmap);
        }

        openScanner.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(additems15.this, scannerActivity.class);
                startActivity(intent);
            }
        });
        linearColumnone.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(additems15.this,addItemImage.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                store();
            }
        });

    }



    public void store(){

         db = FirebaseFirestore.getInstance();


        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);

        imageView6 = findViewById(R.id.imageView6);

        String ProductName = editTextTextPersonName.getText().toString().trim();
        String ProductCategory = editTextTextPersonName2.getText().toString().trim();
        ProductCode = editTextTextPersonName3.getText().toString().trim();
        String ProductBuyingPrice = editTextTextPersonName4.getText().toString().trim();
        String ProductSellingPrice = editTextTextPersonName5.getText().toString().trim();

        Map<String, Object> Product = new HashMap<>();
        Product.put("ProductName", ProductName);
        Product.put("ProductCategory", ProductCategory);
        Product.put("ProductCode", ProductCode);
        Product.put("ProductBuyingPrice", ProductBuyingPrice);
        Product.put("ProductSellingPrice", ProductSellingPrice);

        db.collection("Product").document(ProductCode).set(Product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(additems15.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully written!"+ ProductCode);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(additems15.this, "Product Not Added", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

}
