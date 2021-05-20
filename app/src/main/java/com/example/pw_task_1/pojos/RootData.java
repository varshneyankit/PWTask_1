package com.example.pw_task_1.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootData {
    @SerializedName("facilities")
    private List<Facilities> facilities;

    @SerializedName("exclusions")
    private List<List<Exclusions>> exclusions;

    public RootData(List<Facilities> facilities, List<List<Exclusions>> exclusions) {
        this.facilities = facilities;
        this.exclusions = exclusions;
    }

    public List<List<Exclusions>> getExclusions() {
        return exclusions;
    }

    public List<Facilities> getFacilities() {
        return facilities;
    }
}