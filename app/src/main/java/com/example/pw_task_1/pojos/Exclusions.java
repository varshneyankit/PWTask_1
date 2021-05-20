package com.example.pw_task_1.pojos;

import com.google.gson.annotations.SerializedName;

public class Exclusions {
	@SerializedName("facility_id")
	private String facilityId;

	@SerializedName("options_id")
	private String optionsId;

	public Exclusions(String facilityId, String optionsId) {
		this.facilityId = facilityId;
		this.optionsId = optionsId;
	}

	public String getOptionsId(){
		return optionsId;
	}

	public String getFacilityId(){
		return facilityId;
	}
}
