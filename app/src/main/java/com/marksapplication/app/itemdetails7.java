package com.marksapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class itemdetails7 extends AppCompatActivity {

    ImageView imageView7,imageArrowLeftOne;
    TextView prodname,prodPrice,prodCategorry,prodquantitty,txtIDescription;

    String purchasedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_small_seven);

        imageView7 = findViewById(R.id.imageView7);
        prodname = findViewById(R.id.prodname);
        prodPrice = findViewById(R.id.prodPrice);
        prodCategorry = findViewById(R.id.prodCategoryy);
        prodquantitty = findViewById(R.id.prodquantityy);
        txtIDescription = findViewById(R.id.txtIDescription);
        imageArrowLeftOne = findViewById(R.id.imageArrowLeftOne);

        imageArrowLeftOne.setOnClickListener(v -> {
            finish();
        });

        // Get the intent that started this activity and extract the string

        Intent intent = getIntent();

        String name = intent.getStringExtra("productName");
        String qty = intent.getStringExtra("productQuantity");
        String sellingprice = intent.getStringExtra("productSellingPrice");
        String category = intent.getStringExtra("productCategory");
        String image = intent.getStringExtra("productImage");
        purchasedate = intent.getStringExtra("purchaseDate");

        String formattedDate = null;
        if (purchasedate != null) {
            try {
                long purchaseTimeInMillis = Long.parseLong(purchasedate);
                Date date = new Date(purchaseTimeInMillis); // Assuming you have date in milliseconds
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Customize format as needed
                formattedDate = formatter.format(date);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log the exception for debugging
            }
        }


        String buyingprice = intent.getStringExtra("productBuyingPrice");
        String code = intent.getStringExtra("productCode");

        prodname.setText("Product: " +name);
        prodquantitty.setText("Quantity: " +qty);
        prodPrice.setText("SellingPrice: " + sellingprice);
        prodCategorry.setText("Category: " +category);
        txtIDescription.setText(String.format("Product Code: %s\nPurchased Date: %s\nBuying Price: %s", code, formattedDate , buyingprice));

        if (image != null) {
            imageView7.setImageURI(Uri.parse(image));
        } else {
            Toast.makeText(this, "No image found", Toast.LENGTH_SHORT).show();
            // Set a placeholder image or handle the absence of an image URI accordingly
        }



    }
}