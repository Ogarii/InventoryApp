    package com.marksapplication.app;

    import static android.content.ContentValues.TAG;

    import androidx.annotation.NonNull;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.Manifest;
    import android.app.Activity;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.google.zxing.BarcodeFormat;
    import com.google.zxing.Result;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.UUID;

    import me.dm7.barcodescanner.zxing.ZXingScannerView;


    public class makesale5 extends Fragment implements ZXingScannerView.ResultHandler {
        public makesale5() {

        }
        private RecyclerView recyclerView;
        private makesale5.ItemAdapter itemAdapter;
        private List<Items> itemsList;

        private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

        private ZXingScannerView scannerView;
        static String newQuantity;

        LinearLayout viewRectangleEight;
        String barcodeData;

        Map<String, String> productCodeToIdMap = new HashMap<>();



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

            recyclerView = view.findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Ensure itemsList is populated with data (e.g., call getItems())

            itemsList = new ArrayList<>();
            itemAdapter = new makesale5.ItemAdapter(this, itemsList);

            // Attach adapter after initialization
            recyclerView.setAdapter(itemAdapter);

            viewRectangleEight = view.findViewById(R.id.viewRectangleEight);

            // Start the barcode scanner if camera permission is granted
            if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                startBarcodeScanner();
            }

            return view;

        }

        private void startBarcodeScanner() {
            // Create a new instance of ZXingScannerView
            scannerView = new ZXingScannerView(getContext());


            // Set desired barcode formats and other configurations (if needed)
            // Example configurations:
            scannerView.setFormats(Arrays.asList(BarcodeFormat.values()));
            scannerView.setAutoFocus(true);

            // Add the scanner view to the secondHalfLayout
            viewRectangleEight.addView(scannerView);

            // Set the result handler to this activity
            scannerView.setResultHandler(this::handleResult);

            // Start the camera
            scannerView.startCamera();
        }
        // Implement the ZXingScannerView.ResultHandler interface

        @Override
        public void handleResult(Result rawResult) {
            barcodeData = rawResult.getText();
            Log.d("BarcodeScanner", "Scanned data: " + barcodeData);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Product").whereEqualTo("ProductCode", barcodeData)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Items item = documentSnapshot.toObject(Items.class);

                                // Check if this is the first occurrence of the product code
                                if (!productCodeToIdMap.containsKey(barcodeData)) {
                                    // Generate a UUID for the product code
                                    String itemId = UUID.randomUUID().toString();
                                    // Store the UUID in the map
                                    productCodeToIdMap.put(barcodeData, itemId);

                                    // Assign the UUID to the scanned item
                                    item.setId(itemId);
                                    itemsList.add(item); // Add only the scanned product
                                    itemAdapter.notifyDataSetChanged();
                                } else {
                                    // Retrieve the UUID associated with the product code
                                    String itemId = productCodeToIdMap.get(barcodeData);
                                    // Assign the UUID to the scanned item
                                    item.setId(itemId);

                                    // Check if the scanned item's UUID matches any previously scanned item
                                    boolean duplicate = false;
                                    for (Items existingItem : itemsList) {
                                        if (existingItem.getId().equals(itemId)) {
                                            duplicate = true;

                                            break;
                                        }
                                    }

                                    if (!duplicate) {
                                        // Add the scanned item only if it's not a duplicate
                                        itemsList.add(item);
                                        itemAdapter.notifyDataSetChanged();
                                    } else {
                                        // Handle the case where the item is a duplicate
                                        Log.d(TAG, "Duplicate item scanned.");

                                        // Find the existing item with the same UUID and update its quantity
                                        for (Items existingItem : itemsList) {
                                            if (existingItem.getId().equals(itemId)) {
                                                // Get the current quantity
                                                int currentQuantity = Integer.parseInt(existingItem.getQuantity());
                                                // Increment the quantity by 1
                                                currentQuantity++;
                                                // Set the new quantity
                                                existingItem.setQuantity(String.valueOf(currentQuantity));
                                                // Notify the adapter that the data has changed
                                                itemAdapter.notifyDataSetChanged();
                                                break; // Exit the loop since we found the matching item
                                            }
                                        }
                                        Toast.makeText(getContext(), "Duplicate item scanned", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error fetching items: " + e.getMessage());
                        }
                    });
            onResume();
        }
        // compare with scanned code
        private void handleMatchingCode() {


            // Resume scanning after handling the result
            scannerView.resumeCameraPreview(this);
        }




        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startBarcodeScanner();
                } else {
                    Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onPause() {
            super.onPause();
            // Stop the camera when the fragment is paused to release resources
            if (scannerView != null) {
                scannerView.stopCamera();
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            // Resume the camera when the fragment is resumed
            if (scannerView != null) {
                // Use the result handler set in startBarcodeScanner
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            // Release resources when the fragment is destroyed
            if (scannerView != null) {
                scannerView.stopCamera();
                scannerView = null;
            }
        }


        private void getItems() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Product").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Items items = documentSnapshot.toObject(Items.class);
                                itemsList.add(items);


                            }
                            itemAdapter.notifyDataSetChanged();
                            Log.d(TAG, "items retrieved successfully");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error fetching items: " + e.getMessage());
                        }
                    });

        }
        private static class ItemAdapter extends RecyclerView.Adapter<makesale5.ItemAdapter.ItemViewHolder> {
            private List<Items> itemsList;
            private static makesale5 context;

            public ItemAdapter(makesale5 context, List<Items> itemsList) {
                this.context = context;
                this.itemsList = itemsList;
            }

            @NonNull
            @Override
            public makesale5.ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermakesale, parent, false); // Inflate item layout directly
                return new makesale5.ItemAdapter.ItemViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull makesale5.ItemAdapter.ItemViewHolder holder, int position) {
                Items items = itemsList.get(position);
                holder.bind(items);

            }
            @Override
            public int getItemCount() {
                return itemsList.size();
            }

            static class ItemViewHolder extends RecyclerView.ViewHolder {

                TextView txtProd,txtqnty,txtPrice,txtCode,Total;


                ItemViewHolder(@NonNull View itemView) {
                    super(itemView);
                    txtProd = itemView.findViewById(R.id.txtProd);
                    txtqnty = itemView.findViewById(R.id.txtqnty);
                    txtPrice = itemView.findViewById(R.id.txtPrice);
                    txtCode = itemView.findViewById(R.id.textCode);
                    Total = itemView.findViewById(R.id.Total);



                }

                public void bind(Items items) {

                    txtProd.setText(items.getProductName());
                    txtPrice.setText(items.getProductSellingPrice());
                    txtCode.setText(items.getProductCode());
                    txtqnty.setText(items.getQuantity());

                    String price = txtPrice.getText().toString();
                    String quantity = txtqnty.getText().toString();

                    int total = Integer.parseInt(price) * Integer.parseInt(quantity);
                    Total.setText(String.valueOf(total));


                }
            }
        }


    }