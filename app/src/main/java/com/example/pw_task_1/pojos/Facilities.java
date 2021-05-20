package com.example.pw_task_1.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facilities {
	@SerializedName("facility_id")
	private String facilityId;

	@SerializedName("name")
	private String name;

	@SerializedName("options")
	private List<Options> options;

	public Facilities(String facilityId, String name, List<Options> options) {
		this.facilityId = facilityId;
		this.name = name;
		this.options = options;
	}

	public String getName(){
		return name;
	}

	public List<Options> getOptions(){
		return options;
	}

	public String getFacilityId(){
		return facilityId;
	}
}