package com.example.pw_task_1.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pw_task_1.MainViewModel;
import com.example.pw_task_1.R;
import com.example.pw_task_1.pojos.Exclusions;
import com.example.pw_task_1.pojos.Facilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.internal.cache.InternalCache;

public class FacilityAdpater extends RecyclerView.Adapter<FacilityAdpater.ViewHolder> {

    private final Context context;
    private MainViewModel.ApiResponseBundle dataset;
    private HashMap<Integer,RadioButton> radioButtonHashmap = new HashMap<>();
    private final HashMap<Integer, List<RadioButton>> disabledButtonMap = new HashMap<>();

    public FacilityAdpater(Context context) {
        this.context = context;
    }

    public void setDataset(MainViewModel.ApiResponseBundle dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.facilityNameText.setText(dataset.facilitiesList.get(position).getName());
        int options = dataset.facilitiesList.get(position).getOptions().size();
        for (int i = 0; i < options; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(dataset.facilitiesList.get(position).getOptions().get(i).getName());
            radioButtonHashmap.put(Integer.parseInt(dataset.facilitiesList.get(position).getOptions().get(i).getId()),radioButton);
            radioButton.setId(Integer.parseInt(dataset.facilitiesList.get(position).getOptions().get(i).getId()));

            holder.facilityOptionGroup.addView(radioButton);
        }

        holder.facilityOptionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int facilityId = Integer.parseInt(dataset.facilitiesList.get(position).getFacilityId());
                if (disabledButtonMap.containsKey(facilityId)) {
                    disabledButtonMap.get(facilityId).forEach(radioButton -> {
                        if(!disabledButtonMap.containsValue(radioButton)) {
                            radioButton.setEnabled(true);
                        }
                    });
                    disabledButtonMap.put(facilityId,new LinkedList<>());
                }
                //Toast.makeText(context,facilitiesList.get(position).getFacilityId()+"\n"+String.valueOf(radioGroup.getCheckedRadioButtonId()),Toast.LENGTH_LONG).show();

                    if(dataset.exclusionsMap.containsKey(i)){
                        RadioButton radioButton = radioButtonHashmap.get(dataset.exclusionsMap.get(i));
                        if(radioButton!=null){
                            radioButton.setChecked(false);
                            radioButton.setEnabled(false);

                            if(!disabledButtonMap.containsKey(facilityId)){
                                disabledButtonMap.put(facilityId,new LinkedList<>());
                            }
                            disabledButtonMap.get(facilityId).add(radioButton);
                        }
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataset == null) return 0;
        return dataset.facilitiesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView facilityNameText;
        RadioGroup facilityOptionGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            facilityNameText = itemView.findViewById(R.id.facility_name_text_view);
            facilityOptionGroup = itemView.findViewById(R.id.facility_option_radio_group);
        }
    }
}
