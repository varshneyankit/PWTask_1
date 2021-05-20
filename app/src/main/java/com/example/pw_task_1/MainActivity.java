package com.example.pw_task_1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pw_task_1.adapter.FacilityAdapter;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
    private FacilityAdapter facilityAdapter;
    private RelativeLayout progressBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Facilities Details");
        progressBarLayout = findViewById(R.id.fullscreen_progress_layout);
        progressBarLayout.setVisibility(View.VISIBLE);

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
        setDataFromViewHolder();

    }

    private void setDataFromViewHolder() {
        RecyclerView facilityRecyclerView = findViewById(R.id.facility_recycler_view);
        facilityAdapter = new FacilityAdapter(this);
        facilityRecyclerView.setAdapter(facilityAdapter);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getLiveApiResponse().observe((LifecycleOwner) this, bundle -> {
            facilityAdapter.setDataset(bundle);
            facilityRecyclerView.scrollToPosition(bundle.facilitiesList.size() - 1);
            facilityAdapter.notifyDataSetChanged();
            progressBarLayout.setVisibility(View.GONE);
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about_button) {
            showDiaglogBox();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            String url = "https://drive.google.com/file/d/1Bw39pvqG-ModIvUYLCqPyZZe-lMA6eVm/view?usp=sharing";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        alertDialog.show();
    }
}