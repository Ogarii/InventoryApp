package com.marksapplication.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileReader;

public class itemview8 extends Fragment {

    public itemview8() {

    }
    ImageView imageSettingsSlider;
    LinearLayout linearRowitems;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

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

        return view;
    }
}