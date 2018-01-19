package com.example.jasycdell3.rentalpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

import com.example.jasycdell3.rentalpal.adapter.DemoAdapter;
import com.example.jasycdell3.rentalpal.adapter.NormalAdapter;

public class Demo extends AppCompatActivity {
    RecyclerView recycler_view;

    DemoAdapter adapter;
    Button save_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_demo);
        GridView gridview = (GridView) findViewById(R.id.gridView12);
        save_images = (Button) findViewById(R.id.save_images);

        adapter = new DemoAdapter(this);
        gridview.setAdapter(adapter);

    }
}