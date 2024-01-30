package com.marksapplication.app;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class employeelist12 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<employees> employeeList;

    private FirebaseFirestore db;

    public employeelist12() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(this, employeeList);
        recyclerView.setAdapter(employeeAdapter);

        db = FirebaseFirestore.getInstance();
        getEmployees();
    }

    private void getEmployees() {
        db.collection("Employee").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            employees employee = documentSnapshot.toObject(employees.class);
                            employeeList.add(employee);
                        }
                        employeeAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Employees retrieved successfully");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error fetching employees: " + e.getMessage());
                    }
                });
    }

    // Define your EmployeeAdapter class here
    private static class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
        private List<employees> employeeList;
        private Context context;

        public EmployeeAdapter(employeelist12 context, List<employees> employeeList) {
            this.context = context;
            this.employeeList = employeeList;
        }

        @NonNull
        @Override
        public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the main layout activity_android_small_twelve
            View mainLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_android_small_twelve, parent, false);

            // Find the layout inside activity_android_small_twelve where you want to attach linearRowrectanglefortysix
            ViewGroup layoutToAttachTo = mainLayout.findViewById(R.id.linearRowrectanglefortysix);

            // Inflate the linearRowrectanglefortysix layout using layoutToAttachTo as the parent
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_android_small_twelve, layoutToAttachTo, false);

            return new EmployeeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
            employees employee = employeeList.get(position);
            holder.bind(employee);
        }

        @Override
        public int getItemCount() {
            return employeeList.size();
        }

        static class EmployeeViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout linearRowrectanglefortysix;
            private TextView textView, textView1;

            EmployeeViewHolder(@NonNull View itemView) {
                super(itemView);

                linearRowrectanglefortysix = itemView.findViewById(R.id.linearRowrectanglefortysix);
                textView = itemView.findViewById(R.id.textView);
                textView1 = itemView.findViewById(R.id.textView2);
            }

            public void bind(employees employee) {
                // Bind data to your item views here
                textView.setText(employee.getName());
                textView1.setText(employee.getPhonenumber());
            }
        }
    }
}