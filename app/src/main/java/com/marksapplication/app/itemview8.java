    package com.marksapplication.app;

    import static android.content.ContentValues.TAG;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.List;

    public class itemview8 extends Fragment {

        public itemview8() {

        }
        private RecyclerView recyclerView;
        private ItemAdapter itemAdapter;
        private List<Items> itemsList;

        //private FirebaseFirestore db;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.recycler_itemview, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Ensure itemsList is populated with data (e.g., call getItems())

            itemsList = new ArrayList<>();
            itemAdapter = new ItemAdapter(this, itemsList);

            // Attach adapter after initialization
            recyclerView.setAdapter(itemAdapter);



            ImageView imageSettingsSlider;
            LinearLayout linearRowitems;

            imageSettingsSlider = view.findViewById(R.id.imageSettingsSlider);
            linearRowitems = view.findViewById(R.id.linearRowitems);


            linearRowitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(getActivity(), ItemSettings18.class);
                   startActivity(intent);
                }
            });

            imageSettingsSlider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(getActivity(), ItemSettings18.class);
                   startActivity(intent);
                }
            });

            getItems();

            return view;
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

        // Define your ItemAdapter class here
        private static class ItemAdapter extends RecyclerView.Adapter<itemview8.ItemAdapter.ItemViewHolder> {
            private List<Items> itemsList;
            private itemview8 context;

            public ItemAdapter(itemview8 context, List<Items> itemsList) {
                this.context = context;
                this.itemsList = itemsList;
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notifications, parent, false); // Inflate item layout directly
                return new ItemViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull itemview8.ItemAdapter.ItemViewHolder holder, int position) {
                Items items = itemsList.get(position);
                holder.bind(items);
            }

            @Override
            public int getItemCount() {
                return itemsList.size();
            }

            static class ItemViewHolder extends RecyclerView.ViewHolder {

                ImageView imageView4;
                TextView prodName,prodQuantity,prodPrice,prodCategory;

                ItemViewHolder(@NonNull View itemView) {
                    super(itemView);

                    imageView4 = itemView.findViewById(R.id.imageView4);
                    prodName = itemView.findViewById(R.id.prodName);
                    prodQuantity = itemView.findViewById(R.id.prodQunatity);
                    prodPrice = itemView.findViewById(R.id.prodPrice);
                    prodCategory = itemView.findViewById(R.id.prodCategoryy);
                }

                public void bind(Items items) {

                    prodName.setText(items.getProductName());
                    prodQuantity.setText(items.getProductQuantity());
                    prodPrice.setText(items.getProductSellingPrice());
                    prodCategory.setText(items.getProductCategory());


                    // Bind data to your item views here

                }
            }
        }
    }