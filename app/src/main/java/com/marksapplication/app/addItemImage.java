package com.marksapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class addItemImage extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    private static final int SELECT_PICTURE = 200;

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int STORAGE_PERMISSION_REQUEST = 102;


    ImageView imageView3;
    Button btnOpenCamera, btnOpenGallery, btnSave;
    File photoFile1;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_image);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .93));

        btnOpenGallery = findViewById(R.id.btnOpenGallery);
        btnOpenCamera = findViewById(R.id.btnOpenCamera);
        btnSave = findViewById(R.id.btnSignOut);
        imageView3 = findViewById(R.id.imageView3);

        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCaptureImage(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView3.getDrawable() == null) {
                    // No image selected
                    return;
                }

                // Save the image to a file in storage
                Uri imageUri = saveImageToFile();

                // Pass the Uri to the next activity using an intent
                Intent intent = new Intent(addItemImage.this, additems15.class);
                assert imageUri != null;
                intent.putExtra("imageUri", imageUri.toString());
                intent.putExtra("fromSenderActivity", true);
                Log.d("ImageUri", "Image Uri: " + imageUri);
                startActivity(intent);
                finish();
            }
        });

    }
    private Uri saveImageToFile() {
        // Get the bitmap from the ImageView
        imageView3.setDrawingCacheEnabled(true);
        imageView3.buildDrawingCache();
        bitmap = ((BitmapDrawable) imageView3.getDrawable()).getBitmap();

        // Create a unique filename using timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        // Save the bitmap to a file in the device's external storage directory
        File imagePath = new File(Environment.getExternalStorageDirectory(), imageFileName);
        try {
            FileOutputStream fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Return the Uri of the saved image file
        return Uri.fromFile(imagePath);
    }

    public void onClickCaptureImage(View view) {
        if (checkCameraPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                photoFile1 = createImageFile();
                if (photoFile1 != null) {
                    Uri photoUri = FileProvider.getUriForFile(this,
                            "com.example.marksapplication.fileprovider",
                            photoFile1);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_1);
                }
            }
        } else {
            requestCameraPermission();
        }
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted
                    // You can proceed with camera operations here
                } else {
                    // Camera permission denied
                    // Handle this as needed (e.g., show a message to the user)
                }
                break;

            case STORAGE_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Storage permission granted
                    // You can proceed with storage operations here
                } else {
                    // Storage permission denied
                    // Handle this as needed (e.g., show a message to the user)
                }
                break;
        }
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Append a random number to the file name to ensure uniqueness
        imageFileName += new Random().nextInt(1000);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE_1) {
                if (photoFile1 != null && photoFile1.exists()) {
                    Bitmap bitmap1 = BitmapFactory.decodeFile(photoFile1.getAbsolutePath());
                    imageView3.setImageBitmap(bitmap1);
                }
            } else if (requestCode == SELECT_PICTURE) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    imageView3.setImageURI(selectedImageUri);
                }
            }
        }
    }

}