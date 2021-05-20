package com.example.pw_task_1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pw_task_1.adapter.FacilityAdapter;

public class MainActivity extends AppCompatActivity {
    private FacilityAdapter facilityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView facilityRecyclerView = findViewById(R.id.facility_recycler_view);
        facilityAdapter = new FacilityAdapter(this);
        facilityRecyclerView.setAdapter(facilityAdapter);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getLiveApiResponse().observe((LifecycleOwner) this, bundle -> {
            facilityAdapter.setDataset(bundle);
            facilityRecyclerView.scrollToPosition(bundle.facilitiesList.size()-1);
            facilityAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_button:
                showDiaglogBox();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    private void showDiaglogBox() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.about_me_layout,null);
        TextView call,email,github,resume;
        call = mView.findViewById(R.id.about_me_call);
        email = mView.findViewById(R.id.about_me_email);
        github = mView.findViewById(R.id.about_me_github);
        resume = mView.findViewById(R.id.about_me_resume);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        call.setOnClickListener(view -> {
            String uri = "tel:8375983710" ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        });
        email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:ankitv397@gmail.com"));
            startActivity(Intent.createChooser(intent, "Send Email"));
        });
        github.setOnClickListener(view -> {
            String url = "https://github.com/varshneyankit/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        resume.setOnClickListener(view -> {
            String url = "https://github.com/varshneyankit/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        alertDialog.show();
    }
}