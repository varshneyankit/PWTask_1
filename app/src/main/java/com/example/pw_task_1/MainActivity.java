package com.example.pw_task_1;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pw_task_1.adapter.FacilityAdpater;

public class MainActivity extends AppCompatActivity {
    private FacilityAdpater facilityAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView facilityRecyclerView = findViewById(R.id.facility_recycler_view);
        facilityAdpater = new FacilityAdpater(this);
        facilityRecyclerView.setAdapter(facilityAdpater);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getLiveApiResponse().observe((LifecycleOwner) this, bundle -> {
            facilityAdpater.setDataset(bundle);
            facilityRecyclerView.scrollToPosition(bundle.facilitiesList.size()-1);
            facilityAdpater.notifyDataSetChanged();
        });
    }
}