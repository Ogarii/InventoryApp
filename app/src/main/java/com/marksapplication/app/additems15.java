package com.marksapplication.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class additems15 extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    private static final int SELECT_PICTURE = 200;
    int REQUEST_CODE_ADD_ITEM_IMAGE = 1; // Define your request code
    int YOUR_REQUEST_CODE = 1; // This code can be anything value you want, its just a identification number.

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int STORAGE_PERMISSION_REQUEST = 102;

    LinearLayout linearColumnone, openScanner;
    EditText editTextTextPersonName,editTextTextPersonName2,editTextTextPersonName3,editTextTextPersonName4,editTextTextPersonName5,editTextTextPersonName6;
    Button btnSave;
    ImageView imageView6;
    private FirebaseFirestore db;

    String ProductCode;

    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_small_fifteen);

        btnSave = findViewById(R.id.btnSave);

        linearColumnone = findViewById(R.id.linearColumnone);

        imageView6 = findViewById(R.id.imageView6);

        openScanner = findViewById(R.id.openScanner);

        openScanner.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startBarcodeScanner();
            }
        });
        linearColumnone.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(additems15.this,addItemImage.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Storage();
                store();

            }
        });
        //Receive barcode from scanner
        Intent intent = getIntent();
        String barCode = intent.getStringExtra("code");
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName3.setText(barCode);

        onResume();
    }
    protected void onResume() {
        super.onResume();

        // Check if the activity was launched from SenderActivity
        boolean fromSenderActivity = getIntent().getBooleanExtra("fromSenderActivity", false);
        if (fromSenderActivity) {
            imageUri = getIntent().getStringExtra("imageUri");
            if (imageUri != null) {
                Uri imageUri1 = Uri.parse(imageUri);
                if (imageUri1!= null) {
                    // Clear the Glide cache for the target ImageView
                    Glide.with(this).clear(imageView6);

                    // Load and display the new image using the Uri
                    Glide.with(this).load(imageUri1).into(imageView6);
                    Toast.makeText(this, "Image received", Toast.LENGTH_SHORT).show();
                }
                else {
                    // If imageUri is null, show a message indicating that no image is received
                    Toast.makeText(this, "Image not received", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            // If the activity was not launched from the sender activity, show a message indicating no image is received
            Toast.makeText(this, "No image received", Toast.LENGTH_SHORT).show();
        }
    }
    public void store(){

         db = FirebaseFirestore.getInstance();

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);

        String ProductName = editTextTextPersonName.getText().toString().trim();
        String ProductCategory = editTextTextPersonName2.getText().toString().trim();
        ProductCode = editTextTextPersonName3.getText().toString().trim();
        String ProductBuyingPrice = editTextTextPersonName4.getText().toString().trim();
        String ProductSellingPrice = editTextTextPersonName5.getText().toString().trim();
        String ProductQuantity = editTextTextPersonName6.getText().toString().trim();

        Map<String, Object> Product = new HashMap<>();
        Product.put("ProductName", ProductName);
        Product.put("ProductCategory", ProductCategory);
        Product.put("ProductCode", ProductCode);
        Product.put("ProductBuyingPrice", ProductBuyingPrice);
        Product.put("ProductSellingPrice", ProductSellingPrice);
        Product.put("ProductQuantity", ProductQuantity);
        Product.put("ProductImage", imageUri.toString());


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
    public void Storage() {
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Get a reference to the Firebase Storage location
            StorageReference storageRef = storage.getReference().child("images").child(Uri.parse(imageUri).getLastPathSegment());

            // Upload the image file to Firebase Storage
            storageRef.putFile(Uri.parse(imageUri))
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        Toast.makeText(additems15.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                        Log.e("additems15", "Failed to upload image: " + exception.getMessage());
                        Toast.makeText(additems15.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public void ifProdExists(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                        if(Objects.equals(document.getString("ProductCode"), ProductCode)){
                            Toast.makeText(additems15.this, "Product Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    //Geg item code
    private void startBarcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0); // Use the rear camera
        integrator.setOrientationLocked(false); // Allow both portrait and landscape
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startBarcodeScanner();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String barcodeData = result.getContents();
                Log.d("BarcodeScanner", "Scanned data: " + barcodeData);
                // Handle the scanned data as needed

                ProductCode = barcodeData;
                editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);
                editTextTextPersonName3.setText(barcodeData);


            }
        } else {


            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}



