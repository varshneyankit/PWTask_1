package com.example.pw_task_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pw_task_1.MainViewModel;
import com.example.pw_task_1.R;
import com.example.pw_task_1.pojos.Facilities;
import com.example.pw_task_1.pojos.Options;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

    private final Context context;
    private final HashMap<Integer, List<RadioButton>> disabledButtonMap = new HashMap<>();
    private MainViewModel.ApiResponseBundle dataset;
    private final HashMap<Integer, RadioButton> radioButtonHashmap = new HashMap<>();

    public FacilityAdapter(Context context) {
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
        final Facilities facility = dataset.facilitiesList.get(position);
        holder.facilityNameText.setText(facility.getName());
        List<Options> optionsList = facility.getOptions();
        int options = optionsList.size();
        for (int i = 0; i < options; i++) {
            Options option = optionsList.get(i);
            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(Integer.parseInt(option.getId()));
            radioButton.setText(option.getName());
            radioButtonHashmap.put(Integer.parseInt(option.getId()), radioButton);
            holder.facilityOptionGroup.addView(radioButton);
        }

        holder.facilityOptionGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            int facilityId = Integer.parseInt(facility.getFacilityId());
            // See if current facility is responsible for disabling some button
            if (disabledButtonMap.containsKey(facilityId)) {
                disabledButtonMap.put(facilityId, new LinkedList<>());
            }

            if (dataset.exclusionsMap.containsKey(i)) {
                RadioButton radioButton = radioButtonHashmap.get(dataset.exclusionsMap.get(i));
                if (radioButton != null) {
                    if (!disabledButtonMap.containsKey(facilityId)) {
                        disabledButtonMap.put(facilityId, new LinkedList<>());
                    }
                    Objects.requireNonNull(disabledButtonMap.get(facilityId)).add(radioButton);
                }
            }
            renderButtons();
        });
    }

    private void renderButtons() {
        HashSet<RadioButton> disabled = new HashSet<>();
        disabledButtonMap.forEach((items, buttonList) -> {
            disabled.addAll(buttonList);
        });
        radioButtonHashmap.forEach((optionId, radioButton) -> {
            boolean enabled = !disabled.contains(radioButton);
            if (!enabled) radioButton.setChecked(false);
            radioButton.setEnabled(enabled);
        });
    }

    @Override
    public int getItemCount() {
        if (dataset == null) return 0;
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