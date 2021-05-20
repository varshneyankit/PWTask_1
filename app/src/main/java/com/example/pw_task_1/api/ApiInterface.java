package com.example.pw_task_1.api;

import com.example.pw_task_1.pojos.RootData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("ricky1550/pariksha/db")
    Call<RootData> getAllData();
}
